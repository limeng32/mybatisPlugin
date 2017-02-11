package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.Detail_;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleType;

@CacheRoleAnnotation(ObserverClass = { Account_.class }, TriggerClass = { LoginLog_.class })
public interface LoginLogMapper extends MapperFace<LoginLog_> {

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public LoginLog_ select(Object id);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Collection<LoginLog_> selectAll(LoginLog_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public LoginLog_ selectOne(LoginLog_ t);

	@Override
	public void insert(LoginLog_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int update(LoginLog_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int updatePersistent(LoginLog_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int delete(LoginLog_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public int count(LoginLog_ t);

	public void loadDetail(LoginLog_ loginLog, Detail_ detail);
}
