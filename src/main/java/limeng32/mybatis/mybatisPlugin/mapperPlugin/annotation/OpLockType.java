package limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation;

public enum OpLockType {
	/** version型乐观锁，适合整数型字段 */
	Version, /** stamp型乐观锁，适合时间型字段 */
	Stamp, /** 非乐观锁字段 */
	Null
}
