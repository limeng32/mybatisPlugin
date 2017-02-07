package limeng32.mybatis.mybatisPlugin;

import java.util.Collection;

import limeng32.mirage.util.service.ServiceSupport;
import limeng32.mybatis.mybatisPlugin.mapper.LoginLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService extends ServiceSupport<LoginLog_> implements
		LoginLogMapper {

	@Autowired
	private LoginLogMapper mapper;

	@Autowired
	private DetailService detailService;

	@Override
	public LoginLog_ select(Object id) {
		return supportSelect(mapper, id);
	}

	@Override
	public LoginLog_ selectOne(LoginLog_ t) {
		return supportSelectOne(mapper, t);
	}

	@Override
	public void insert(LoginLog_ t) {
		supportInsert(mapper, t);
	}

	@Override
	public int update(LoginLog_ t) {
		return supportUpdate(mapper, t);
	}

	@Override
	public Collection<LoginLog_> selectAll(LoginLog_ t) {
		return supportSelectAll(mapper, t);
	}

	@Override
	public int updatePersistent(LoginLog_ t) {
		return supportUpdatePersistent(mapper, t);
	}

	@Override
	public int delete(LoginLog_ t) {
		return supportDelete(mapper, t);
	}

	@Override
	public int count(LoginLog_ t) {
		return supportCount(mapper, t);
	}

	@Override
	public void loadDetail(LoginLog_ loginlog, Detail_ detail) {
		detail.setLoginLog(loginlog);
		loginlog.setDetail(detailService.selectAll(detail));
	}
}