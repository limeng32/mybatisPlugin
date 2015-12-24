package limeng32.mybatis.mybatisPlugin.mapper;

import java.util.Collection;

import limeng32.mirage.util.mapper.MapperFace;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.LoginLog_;

public interface AccountMapper extends MapperFace<Account_> {

	@Override
	public Account_ select(int id);

	@Override
	public Collection<Account_> selectAll(Account_ t);

	@Override
	public void insert(Account_ t);

	@Override
	public int update(Account_ t);

	@Override
	public int updatePersistent(Account_ t);

	@Override
	public void retrieve(Account_ t);

	@Override
	public void retrieveOnlyNull(Account_ t);

	@Override
	public int delete(Account_ t);

	@Override
	public int count(Account_ t);

	public void loadLoginLog(Account_ account, LoginLog_ loginLog);
}
