package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.Role_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotationNew;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleType;

@CacheRoleAnnotation(ObserverClass = {}, TriggerClass = { Role_.class })
public interface RoleMapper extends MapperFace<Role_> {

	@Override
	@CacheAnnotation(MappedClass = {}, role = CacheRoleType.Observer)
	@CacheAnnotationNew(role = CacheRoleType.Observer)
	public Role_ select(Object id);

	@Override
	@CacheAnnotation(MappedClass = {}, role = CacheRoleType.Observer)
	@CacheAnnotationNew(role = CacheRoleType.Observer)
	public Collection<Role_> selectAll(Role_ t);

	@Override
	@CacheAnnotation(MappedClass = {}, role = CacheRoleType.Observer)
	@CacheAnnotationNew(role = CacheRoleType.Observer)
	public Role_ selectOne(Role_ t);

	@Override
	public void insert(Role_ t);

	@Override
	@CacheAnnotation(MappedClass = { Role_.class }, role = CacheRoleType.Trigger)
	@CacheAnnotationNew(role = CacheRoleType.Trigger)
	public int update(Role_ t);

	@Override
	@CacheAnnotation(MappedClass = { Role_.class }, role = CacheRoleType.Trigger)
	@CacheAnnotationNew(role = CacheRoleType.Trigger)
	public int updatePersistent(Role_ t);

	@Override
	@CacheAnnotation(MappedClass = { Role_.class }, role = CacheRoleType.Trigger)
	@CacheAnnotationNew(role = CacheRoleType.Trigger)
	public int delete(Role_ t);

	@Override
	@CacheAnnotation(MappedClass = {}, role = CacheRoleType.Observer)
	@CacheAnnotationNew(role = CacheRoleType.Observer)
	public int count(Role_ t);

	public void loadAccount(Role_ role, Account_ account);

	public void loadAccountDeputy(Role_ roleDeputy, Account_ accountDeputy);
}
