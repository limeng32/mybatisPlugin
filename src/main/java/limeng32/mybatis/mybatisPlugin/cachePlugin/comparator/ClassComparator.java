package limeng32.mybatis.mybatisPlugin.cachePlugin.comparator;

import java.util.Comparator;

public class ClassComparator implements Comparator<Class<?>> {

	@Override
	public int compare(Class<?> o1, Class<?> o2) {
		return o1.getSimpleName().compareTo(o2.getSimpleName());
	}

}
