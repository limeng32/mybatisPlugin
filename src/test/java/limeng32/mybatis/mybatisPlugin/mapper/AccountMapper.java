package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.Role_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleType;

@CacheRoleAnnotation(ObserverClass = { Role_.class }, TriggerClass = { Account_.class })
public interface AccountMapper extends MapperFace<Account_> {

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Account_ select(Object id);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Collection<Account_> selectAll(Account_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Account_ selectOne(Account_ t);

	@Override
	public void insert(Account_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int update(Account_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int updatePersistent(Account_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int delete(Account_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public int count(Account_ t);

	public void loadLoginLog(Account_ account, LoginLog_ loginLog);
}
