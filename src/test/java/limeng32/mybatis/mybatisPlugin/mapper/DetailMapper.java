package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Detail_;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleType;

@CacheRoleAnnotation(ObserverClass = { LoginLog_.class }, TriggerClass = { Detail_.class })
public interface DetailMapper extends MapperFace<Detail_> {

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Detail_ select(Object id);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Collection<Detail_> selectAll(Detail_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public Detail_ selectOne(Detail_ t);

	@Override
	public void insert(Detail_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int update(Detail_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int updatePersistent(Detail_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Trigger)
	public int delete(Detail_ t);

	@Override
	@CacheAnnotation(role = CacheRoleType.Observer)
	public int count(Detail_ t);
}
