package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Detail_;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheAnnotation;
import limeng32.mybatis.mybatisPlugin.cachePlugin.annotation.CacheRoleType;

public interface DetailMapper extends MapperFace<Detail_> {

	@Override
	@CacheAnnotation(MappedClass = { LoginLog_.class }, role = CacheRoleType.Observer)
	public Detail_ select(int id);

	@Override
	@CacheAnnotation(MappedClass = { LoginLog_.class }, role = CacheRoleType.Observer)
	public Collection<Detail_> selectAll(Detail_ t);

	@Override
	public void insert(Detail_ t);

	@Override
	@CacheAnnotation(MappedClass = { Detail_.class }, role = CacheRoleType.Trigger)
	public int update(Detail_ t);

	@Override
	@CacheAnnotation(MappedClass = { Detail_.class }, role = CacheRoleType.Trigger)
	public int updatePersistent(Detail_ t);

	@Override
	public void retrieve(Detail_ t);

	@Override
	public void retrieveOnlyNull(Detail_ t);

	@Override
	@CacheAnnotation(MappedClass = { Detail_.class }, role = CacheRoleType.Trigger)
	public int delete(Detail_ t);

	@Override
	@CacheAnnotation(MappedClass = { LoginLog_.class }, role = CacheRoleType.Observer)
	public int count(Detail_ t);
}
