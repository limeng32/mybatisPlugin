package limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation;

public enum ConditionType {
	/* 相等 */Equal,
	/* like匹配 */Like,
	/* 开头like匹配 */HeadLike,
	/* 结尾like匹配 */TailLike,
	/*
	 * 多like条件匹配 ， 与关系
	 */MultiLikeAND,
	/*
	 * 多like条件匹配 ， 或关系
	 */MultiLikeOR,
	/*
	 * 大于 ， 未完成
	 */GreaterThan,
	/*
	 * 大于或等于 ， 未完成
	 */GreaterOrEqual,
	/*
	 * 小于 ， 未完成
	 */LessThan,
	/*
	 * 小于或等于 ， 未完成
	 */LessOrEqual,
	/*
	 * 不等于 ， 未完成
	 */NotEqual
}
