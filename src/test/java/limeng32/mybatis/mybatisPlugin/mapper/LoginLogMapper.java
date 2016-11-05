package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.Role_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleType;

public interface LoginLogMapper extends MapperFace<LoginLog_> {

	@Override
	@CacheAnnotation(MappedClass = { Account_.class }, role = CacheRoleType.Observer)
	public LoginLog_ select(int id);

	@Override
	@CacheAnnotation(MappedClass = { Account_.class }, role = CacheRoleType.Observer)
	public Collection<LoginLog_> selectAll(LoginLog_ t);

	@Override
	public void insert(LoginLog_ t);

	@Override
	@CacheAnnotation(MappedClass = { LoginLog_.class }, role = CacheRoleType.Trigger)
	public int update(LoginLog_ t);

	@Override
	@CacheAnnotation(MappedClass = { LoginLog_.class }, role = CacheRoleType.Trigger)
	public int updatePersistent(LoginLog_ t);

	@Override
	public void retrieve(LoginLog_ t);

	@Override
	public void retrieveOnlyNull(LoginLog_ t);

	@Override
	@CacheAnnotation(MappedClass = { LoginLog_.class }, role = CacheRoleType.Trigger)
	public int delete(LoginLog_ t);

	@Override
	@CacheAnnotation(MappedClass = { Account_.class }, role = CacheRoleType.Observer)
	public int count(LoginLog_ t);
}
