package limeng32.mybatis.mybatisPlugin;

import java.util.Collection;

import limeng32.mirage.util.service.ServiceSupport;
import limeng32.mybatis.mybatisPlugin.mapper.DetailMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailService extends ServiceSupport<Detail_> implements
		DetailMapper {

	@Autowired
	private DetailMapper mapper;

	@Override
	public Detail_ select(Object id) {
		return supportSelect(mapper, id);
	}

	@Override
	public Detail_ selectOne(Detail_ t) {
		return supportSelectOne(mapper, t);
	}

	@Override
	public void insert(Detail_ t) {
		supportInsert(mapper, t);
	}

	@Override
	public int update(Detail_ t) {
		return supportUpdate(mapper, t);
	}

	@Override
	public Collection<Detail_> selectAll(Detail_ t) {
		return supportSelectAll(mapper, t);
	}

	@Override
	public int updatePersistent(Detail_ t) {
		return supportUpdatePersistent(mapper, t);
	}

	@Override
	public int delete(Detail_ t) {
		return supportDelete(mapper, t);
	}

	@Override
	public int count(Detail_ t) {
		return supportCount(mapper, t);
	}
}