package limeng32.mybatis.mybatisPlugin;

import java.io.Serializable;

import limeng32.mirage.util.pojo.PojoSupport;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.FieldMapperAnnotation;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

@TableMapperAnnotation(tableName = "loginLog_")
public class LoginLog_ extends PojoSupport<LoginLog_> implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;

	@FieldMapperAnnotation(dbFieldName = "loginTime", jdbcType = JdbcType.TIMESTAMP)
	private java.util.Date loginTime;

	@FieldMapperAnnotation(dbFieldName = "loginIP", jdbcType = JdbcType.VARCHAR)
	private java.lang.String loginIP;

	@FieldMapperAnnotation(dbFieldName = "accountid", jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private Account_ account;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.util.Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(java.util.Date loginTime) {
		this.loginTime = loginTime;
	}

	public java.lang.String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(java.lang.String loginIP) {
		this.loginIP = loginIP;
	}

	public Account_ getAccount() {
		return account;
	}

	public void setAccount(Account_ newAccount) {
		if (this.account == null || !this.account.equals(newAccount)) {
			if (this.account != null) {
				Account_ oldAccount = this.account;
				this.account = null;
				oldAccount.removeLoginLog(this);
			}
			if (newAccount != null) {
				this.account = newAccount;
				this.account.addLoginLog(this);
			}
		}
	}

	@Override
	public boolean equalsExactly(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginLog_ other = (LoginLog_) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loginIP == null) {
			if (other.loginIP != null)
				return false;
		} else if (!loginIP.equals(other.loginIP))
			return false;
		if (loginTime == null) {
			if (other.loginTime != null)
				return false;
		} else if (!loginTime.equals(other.loginTime))
			return false;
		return true;
	}

}