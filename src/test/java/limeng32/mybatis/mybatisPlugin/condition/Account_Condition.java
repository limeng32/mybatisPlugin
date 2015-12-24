package limeng32.mybatis.mybatisPlugin.condition;

import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Conditionable;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Limitable;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Queryable;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Sortable;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.ConditionType;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.QueryMapperAnnotation;

@QueryMapperAnnotation(tableName = "account_")
public class Account_Condition extends Account_ implements Conditionable {

	private static final long serialVersionUID = 1L;

	public static final String field_tableName = "account_";

	public static final String field_id = "id";

	public static final String field_name = "name";

	public static final String field_email = "email";

	public static final String field_password = "password";

	public static final String field_activated = "activated";

	public static final String field_activateValue = "activateValue";

	public enum Field implements Queryable {
		tableName(field_tableName), id(field_id), name(field_name), email(
				field_email), password(field_password), activated(
				field_activated), activateValue(field_activateValue);

		private final String value;

		private Field(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}

		@Override
		public String getTableName() {
			return tableName.value;
		}
	}

	private Limitable limiter;

	private Sortable sorter;

	@ConditionMapperAnnotation(dbFieldName = field_email, conditionType = ConditionType.Like)
	private String emailLike;

	@ConditionMapperAnnotation(dbFieldName = field_email, conditionType = ConditionType.HeadLike)
	private String emailHeadLike;

	@ConditionMapperAnnotation(dbFieldName = field_email, conditionType = ConditionType.TailLike)
	private String emailTailLike;

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

	public String getEmailLike() {
		return emailLike;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}

	public String getEmailHeadLike() {
		return emailHeadLike;
	}

	public void setEmailHeadLike(String emailHeadLike) {
		this.emailHeadLike = emailHeadLike;
	}

	public String getEmailTailLike() {
		return emailTailLike;
	}

	public void setEmailTailLike(String emailTailLike) {
		this.emailTailLike = emailTailLike;
	}

}
