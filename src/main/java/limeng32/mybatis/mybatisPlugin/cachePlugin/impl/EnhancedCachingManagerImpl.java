package limeng32.mybatis.mybatisPlugin.cachePlugin.impl;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import limeng32.mybatis.mybatisPlugin.cachePlugin.CacheKeysPool;
import limeng32.mybatis.mybatisPlugin.cachePlugin.EnhancedCachingManager;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.util.ReflectHelper;

import org.apache.ibatis.cache.Cache;

public class EnhancedCachingManagerImpl implements EnhancedCachingManager {

	// 每一个statementId 更新依赖的statementId集合
	private Map<String, Set<String>> observers = new ConcurrentHashMap<>();
	private Map<Class<?>, Set<Method>> triggerMethods = new ConcurrentHashMap<>();
	private Map<Class<?>, Set<Method>> observerMethods = new ConcurrentHashMap<>();

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
					Set<Object> cacheKeys = sharedCacheKeysPool
							.get(statementId);
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

		String annotationPackageName = properties
				.getProperty("annotationPackage");
		Package annotationPackage = Package.getPackage(annotationPackageName);
		if (annotationPackage != null) {
			Set<Class<?>> classes = ReflectHelper
					.getClasses(annotationPackageName);
			for (Class<?> clazz : classes) {
				for (Method method : clazz.getDeclaredMethods()) {
					CacheAnnotation cacheAnnotation = method
							.getAnnotation(CacheAnnotation.class);
					if (cacheAnnotation != null) {
						switch (cacheAnnotation.role()) {
						case Observer:
							for (Class<?> clazz1 : cacheAnnotation
									.MappedClass()) {
								if (!observerMethods.containsKey(clazz1)) {
									observerMethods.put(clazz1,
											new HashSet<Method>());
								}
								/* 这里采用迭代的方式实现追加观察者的观察者 */
								observerMethods.get(clazz1).add(method);
							}
							break;

						case Trigger:
							for (Class<?> clazz1 : cacheAnnotation
									.MappedClass()) {
								if (!triggerMethods.containsKey(clazz1)) {
									triggerMethods.put(clazz1,
											new HashSet<Method>());
								}
								triggerMethods.get(clazz1).add(method);
							}
							break;
						}
					}
				}
			}
			observerMethodsIteration(observerMethods);
			buildObservers(triggerMethods, observerMethods);
		}
	}

	private void observerMethodsIteration(
			Map<Class<?>, Set<Method>> observerMethodMap) {
		Map<Class<?>, Set<Method>> temp = new ConcurrentHashMap<>();
		for (Class<?> clazz : observerMethodMap.keySet()) {
			Set<Method> observerMethodSet = observerMethodMap.get(clazz);
			for (Method method : observerMethodSet) {
				if ("select".equals(method.getName())) {
					Class<?> clazz1 = method.getReturnType();
					if (observerMethodMap.containsKey(clazz1)) {
						System.out.println("1====================:"
								+ method.getName());
						System.out.println("2====================:" + clazz1);
						temp.put(clazz, observerMethodMap.get(clazz1));
						System.out.println("3====================:"
								+ observerMethodMap.get(clazz1));
					}
				}
			}
		}
		System.out.println("4====================:" + temp);
		for (Class<?> clazz : observerMethodMap.keySet()) {
			if (temp.containsKey(clazz)) {
				Set<Method> observerMethodSet = observerMethodMap.get(clazz);
				observerMethodSet.addAll(temp.get(clazz));
			}
		}
		System.out.println("5====================:" + observerMethodMap);
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
					String triggerFullName = triggerMethod.getDeclaringClass()
							.getName() + "." + triggerMethod.getName();
					if (!observers.containsKey(triggerFullName)) {
						observers.put(triggerFullName, new HashSet<String>());
					}
					for (Method observerMethod : observerMethods) {
						String observerFullName = observerMethod
								.getDeclaringClass().getName()
								+ "."
								+ observerMethod.getName();
						observers.get(triggerFullName).add(observerFullName);
					}
				}
			}
		}
	}
}
