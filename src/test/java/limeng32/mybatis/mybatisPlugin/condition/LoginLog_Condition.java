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

	@ConditionMapperAnnotation(dbFieldName = "loginTime", conditionType = ConditionType.GreaterOrEqual)
	private Date loginTimeGreaterOrEqual;

	@ConditionMapperAnnotation(dbFieldName = "loginTime", conditionType = ConditionType.NotEqual)
	private Date loginTimeNotEqual;

	@ConditionMapperAnnotation(dbFieldName = "id", conditionType = ConditionType.GreaterThan)
	private Integer idGreaterThan;

	@ConditionMapperAnnotation(dbFieldName = "id", conditionType = ConditionType.GreaterOrEqual)
	private Integer idGreaterOrEqual;

	@ConditionMapperAnnotation(dbFieldName = "id", conditionType = ConditionType.LessThan)
	private Integer idLessThan;

	@ConditionMapperAnnotation(dbFieldName = "id", conditionType = ConditionType.LessOrEqual)
	private Integer idLessOrEqual;

	@ConditionMapperAnnotation(dbFieldName = "id", conditionType = ConditionType.NotEqual)
	private Integer idNotEqual;

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

	public Date getLoginTimeGreaterOrEqual() {
		return loginTimeGreaterOrEqual;
	}

	public void setLoginTimeGreaterOrEqual(Date loginTimeGreaterOrEqual) {
		this.loginTimeGreaterOrEqual = loginTimeGreaterOrEqual;
	}

	public Date getLoginTimeNotEqual() {
		return loginTimeNotEqual;
	}

	public void setLoginTimeNotEqual(Date loginTimeNotEqual) {
		this.loginTimeNotEqual = loginTimeNotEqual;
	}

	public Integer getIdGreaterOrEqual() {
		return idGreaterOrEqual;
	}

	public void setIdGreaterOrEqual(Integer idGreaterOrEqual) {
		this.idGreaterOrEqual = idGreaterOrEqual;
	}

	public Integer getIdLessThan() {
		return idLessThan;
	}

	public void setIdLessThan(Integer idLessThan) {
		this.idLessThan = idLessThan;
	}

	public Integer getIdLessOrEqual() {
		return idLessOrEqual;
	}

	public void setIdLessOrEqual(Integer idLessOrEqual) {
		this.idLessOrEqual = idLessOrEqual;
	}

	public Integer getIdNotEqual() {
		return idNotEqual;
	}

	public void setIdNotEqual(Integer idNotEqual) {
		this.idNotEqual = idNotEqual;
	}

}
