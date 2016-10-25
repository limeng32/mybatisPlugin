package limeng32.mybatis.mybatisPlugin.condition;

import java.util.Date;

import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Conditionable;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Limitable;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Sortable;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.ConditionType;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.QueryMapperAnnotation;

@QueryMapperAnnotation(tableName = "loginLog_")
public class LoginLog_Condition extends LoginLog_ implements Conditionable {

	private static final long serialVersionUID = 1L;

	private Limitable limiter;

	private Sortable sorter;

	@ConditionMapperAnnotation(dbFieldName = "loginTime", conditionType = ConditionType.GreaterThan)
	private Date loginTimeGreaterThan;

	@ConditionMapperAnnotation(dbFieldName = "id", conditionType = ConditionType.GreaterThan)
	private Integer idGreaterThan;

	@Override
	public Limitable getLimiter() {
		return limiter;
	}

	@Override
	public void setLimiter(Limitable limiter) {
		this.limiter = limiter;
	}

	@Override
	public Sortable getSorter() {
		return sorter;
	}

	@Override
	public void setSorter(Sortable sorter) {
		this.sorter = sorter;
	}

	public Date getLoginTimeGreaterThan() {
		return loginTimeGreaterThan;
	}

	public void setLoginTimeGreaterThan(Date loginTimeGreaterThan) {
		this.loginTimeGreaterThan = loginTimeGreaterThan;
	}

	public Integer getIdGreaterThan() {
		return idGreaterThan;
	}

	public void setIdGreaterThan(Integer idGreaterThan) {
		this.idGreaterThan = idGreaterThan;
	}

}
