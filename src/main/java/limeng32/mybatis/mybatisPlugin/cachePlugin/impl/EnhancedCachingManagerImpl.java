package limeng32.mybatis.mybatisPlugin.cachePlugin.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.ibatis.cache.Cache;

import limeng32.mybatis.mybatisPlugin.cachePlugin.CacheKeysPool;
import limeng32.mybatis.mybatisPlugin.cachePlugin.EnhancedCachingManager;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.comparator.ClassComparator;
import limeng32.mybatis.mybatisPlugin.util.ReflectHelper;

public class EnhancedCachingManagerImpl implements EnhancedCachingManager {

	// 每一个statementId 更新依赖的statementId集合
	private Map<String, Set<String>> observers = new ConcurrentHashMap<>();
	private Map<Class<?>, Set<Method>> triggerMethods = new ConcurrentHashMap<>();
	private ConcurrentSkipListMap<Class<?>, Set<Method>> observerMethods = new ConcurrentSkipListMap<>(
			new ClassComparator());
	private ConcurrentSkipListMap<Class<?>, Set<Method>> observerMethodsNew = new ConcurrentSkipListMap<>(
			new ClassComparator());
	private Map<Class<?>, Set<Class<?>>> observerClasses = new ConcurrentHashMap<>();
	private Map<Class<?>, Set<Class<?>>> triggerClasses = new ConcurrentHashMap<>();

	// 全局性的 statemntId与CacheKey集合
	private CacheKeysPool sharedCacheKeysPool = new CacheKeysPool();
	// 记录每一个statementId 对应的Cache 对象
	private Map<String, Cache> holds = new ConcurrentHashMap<String, Cache>();
	private boolean initialized = false;
	private boolean cacheEnabled = false;

	private static EnhancedCachingManagerImpl enhancedCacheManager;

	private EnhancedCachingManagerImpl() {
	}

	public static EnhancedCachingManagerImpl getInstance() {
		return enhancedCacheManager == null ? (enhancedCacheManager = new EnhancedCachingManagerImpl())
				: enhancedCacheManager;
	}

	@Override
	public void refreshCacheKey(CacheKeysPool keysPool) {
		sharedCacheKeysPool.putAll(keysPool);
	}

	@Override
	public void clearRelatedCaches(final Set<String> set) {
		for (String observable : set) {
			Set<String> relatedStatements = observers.get(observable);
			if (relatedStatements != null) {
				for (String statementId : relatedStatements) {
					Cache cache = holds.get(statementId);
					Set<Object> cacheKeys = sharedCacheKeysPool.get(statementId);
					for (Object cacheKey : cacheKeys) {
						cache.removeObject(cacheKey);
					}
				}
			}
			// clear shared cacheKey Pool width specific key
			sharedCacheKeysPool.remove(observable);
		}
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void initialize(Properties properties) {
		initialized = true;
		// cacheEnabled
		String cacheEnabled = properties.getProperty("cacheEnabled", "true");
		if ("true".equals(cacheEnabled)) {
			this.cacheEnabled = true;
		}
		String annotationPackageName = properties.getProperty("annotationPackage");
		dealPackageInit(annotationPackageName);
		dealPackageInit2(annotationPackageName);
	}

	private void dealPackageInit(String annotationPackageName) {
		Package annotationPackage = Package.getPackage(annotationPackageName);
		if (annotationPackage != null) {
			Set<Class<?>> classes = ReflectHelper.getClasses(annotationPackageName);
			for (Class<?> clazz : classes) {
				// 将每个class中的cacheRole取出，放入一个集合
				Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
				for (Annotation an : classAnnotations) {
					if (an instanceof CacheRoleAnnotation) {
						if (!observerClasses.containsKey(clazz)) {
							observerClasses.put(clazz, new HashSet<Class<?>>());
						}
						if (!triggerClasses.containsKey(clazz)) {
							triggerClasses.put(clazz, new HashSet<Class<?>>());
						}
						CacheRoleAnnotation cacheRoleAnnotation = (CacheRoleAnnotation) an;
						for (Class<?> clazz1 : cacheRoleAnnotation.ObserverClass()) {
							observerClasses.get(clazz).add(clazz1);
						}
						for (Class<?> clazz1 : cacheRoleAnnotation.TriggerClass()) {
							triggerClasses.get(clazz).add(clazz1);
						}
					}
				}
			}
		}
	}

	private void dealPackageInit2(String annotationPackageName) {
		Package annotationPackage = Package.getPackage(annotationPackageName);
		if (annotationPackage != null) {
			Set<Class<?>> classes = ReflectHelper.getClasses(annotationPackageName);
			for (Class<?> clazz : classes) {
				for (Method method : clazz.getDeclaredMethods()) {
					CacheAnnotation cacheAnnotation = method.getAnnotation(CacheAnnotation.class);
					if (cacheAnnotation != null) {
						dealPackageInit21(clazz, method, cacheAnnotation);
					}
				}
			}
			observerMethodsFission(observerMethods, null, observerMethodsNew);
			buildObservers(triggerMethods, observerMethodsNew);
		}
	}

	private void dealPackageInit21(Class<?> clazz, Method method, CacheAnnotation cacheAnnotation) {
		switch (cacheAnnotation.role()) {
		case Observer:
			for (Class<?> clazz1 : observerClasses.get(clazz)) {
				if (!observerMethods.containsKey(clazz1)) {
					observerMethods.put(clazz1, new HashSet<Method>());
				}
				observerMethods.get(clazz1).add(method);
			}
			break;
		case Trigger:
			for (Class<?> clazz1 : triggerClasses.get(clazz)) {
				if (!triggerMethods.containsKey(clazz1)) {
					triggerMethods.put(clazz1, new HashSet<Method>());
				}
				triggerMethods.get(clazz1).add(method);
			}
			break;
		}
	}

	private void observerMethodsFission(ConcurrentSkipListMap<Class<?>, Set<Method>> observerMethodMap,
			Entry<Class<?>, Set<Method>> currentE, ConcurrentSkipListMap<Class<?>, Set<Method>> observerMethodMapNew) {
		if (observerMethodMap.size() != 0) {
			if (currentE == null) {
				currentE = observerMethodMap.firstEntry();
			}
			observerMethodMapNew.put(currentE.getKey(), currentE.getValue());
			observerMethodsFissionFission(observerMethodMap, observerMethodMapNew);
			Entry<Class<?>, Set<Method>> nextE = observerMethodMap.higherEntry(currentE.getKey());
			if (nextE != null) {
				observerMethodsFission(observerMethodMap, nextE, observerMethodMapNew);
			}
		}
	}

	private void observerMethodsFissionFission(ConcurrentSkipListMap<Class<?>, Set<Method>> observerMethodMap,
			ConcurrentSkipListMap<Class<?>, Set<Method>> observerMethodMapNew) {
		Set<Method> tempSet = new LinkedHashSet<>();
		Entry<Class<?>, Set<Method>> lastE = observerMethodMapNew.lastEntry();
		int size1 = lastE.getValue().size();
		for (Method methob : lastE.getValue()) {
			if ("select".equals(methob.getName())) {
				Class<?> clazz = methob.getReturnType();
				if (observerMethodMap.containsKey(clazz)) {
					tempSet.addAll(observerMethodMap.get(clazz));
				}
			}
		}
		lastE.getValue().addAll(tempSet);
		int size2 = lastE.getValue().size();
		if (size1 != size2) {
			observerMethodsFissionFission(observerMethodMap, observerMethodMapNew);
		}
	}

	@Override
	public void appendStatementCacheMap(String statementId, Cache cache) {
		if (holds.containsKey(statementId) && holds.get(statementId) != null) {
			return;
		}
		holds.put(statementId, cache);
	}

	@Override
	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	private void buildObservers(Map<Class<?>, Set<Method>> triggerMethodMap,
			Map<Class<?>, Set<Method>> observerMethodMap) {
		for (Class<?> clazz : triggerMethodMap.keySet()) {
			Set<Method> observerMethods = observerMethodMap.get(clazz);
			if (observerMethods != null) {
				for (Method triggerMethod : triggerMethodMap.get(clazz)) {
					String triggerFullName = triggerMethod.getDeclaringClass().getName() + "."
							+ triggerMethod.getName();
					if (!observers.containsKey(triggerFullName)) {
						observers.put(triggerFullName, new HashSet<String>());
					}
					for (Method observerMethod : observerMethods) {
						String observerFullName = observerMethod.getDeclaringClass().getName() + "."
								+ observerMethod.getName();
						observers.get(triggerFullName).add(observerFullName);
					}
				}
			}
		}
	}
}
