package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.LoginLog_;

public interface LoginLogMapper extends MapperFace<LoginLog_> {

	@Override
	public LoginLog_ select(int id);

	@Override
	public Collection<LoginLog_> selectAll(LoginLog_ t);

	@Override
	public void insert(LoginLog_ t);

	@Override
	public int update(LoginLog_ t);

	@Override
	public int updatePersistent(LoginLog_ t);

	@Override
	public void retrieve(LoginLog_ t);

	@Override
	public void retrieveOnlyNull(LoginLog_ t);

	@Override
	public int delete(LoginLog_ t);

	@Override
	public int count(LoginLog_ t);
}
