package limeng32.mybatis.mybatisPlugin;

import java.io.Serializable;

import limeng32.mirage.util.pojo.PojoSupport;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.FieldMapperAnnotation;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.OpLockType;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = "account_")
public class Account_ extends PojoSupport<Account_> implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;

	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
	private java.lang.String name;

	@FieldMapperAnnotation(dbFieldName = "email", jdbcType = JdbcType.VARCHAR)
	private java.lang.String email;

	@FieldMapperAnnotation(dbFieldName = "password", jdbcType = JdbcType.VARCHAR, ignoredSelect = true)
	private java.lang.String password;

	@FieldMapperAnnotation(dbFieldName = "opLock", jdbcType = JdbcType.INTEGER, opLockType = OpLockType.Version)
	private Integer opLock;

	@FieldMapperAnnotation(dbFieldName = "status", jdbcType = JdbcType.CHAR)
	private StoryStatus_ status;

	/**
	 * 是否已激活
	 * */
	@FieldMapperAnnotation(dbFieldName = "activated", jdbcType = JdbcType.BOOLEAN)
	private Boolean activated;

	/**
	 * 激活码
	 * 
	 */
	@JSONField(serialize = false)
	@FieldMapperAnnotation(dbFieldName = "activateValue", jdbcType = JdbcType.VARCHAR)
	private java.lang.String activateValue;

	@FieldMapperAnnotation(dbFieldName = "role_id", jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private Role_ role;

	@FieldMapperAnnotation(dbFieldName = "deputy_id", jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private Role_ roleDeputy;

	private java.util.Collection<LoginLog_> loginLog;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public java.lang.String getActivateValue() {
		return activateValue;
	}

	public void setActivateValue(java.lang.String activateValue) {
		this.activateValue = activateValue;
	}

	public Integer getOpLock() {
		return opLock;
	}

	public StoryStatus_ getStatus() {
		return status;
	}

	public void setStatus(StoryStatus_ status) {
		this.status = status;
	}

	public java.util.Collection<LoginLog_> getLoginLog() {
		if (loginLog == null)
			loginLog = new java.util.LinkedHashSet<LoginLog_>();
		return loginLog;
	}

	public java.util.Iterator<LoginLog_> getIteratorLoginLog() {
		if (loginLog == null)
			loginLog = new java.util.LinkedHashSet<LoginLog_>();
		return loginLog.iterator();
	}

	public void setLoginLog(java.util.Collection<LoginLog_> newLoginLog) {
		removeAllLoginLog();
		for (java.util.Iterator<LoginLog_> iter = newLoginLog.iterator(); iter
				.hasNext();)
			addLoginLog((LoginLog_) iter.next());
	}

	public void addLoginLog(LoginLog_ newLoginLog) {
		if (newLoginLog == null)
			return;
		if (this.loginLog == null)
			this.loginLog = new java.util.LinkedHashSet<LoginLog_>();
		if (!this.loginLog.contains(newLoginLog)) {
			this.loginLog.add(newLoginLog);
			newLoginLog.setAccount(this);
		}
	}

	public void removeLoginLog(LoginLog_ oldLoginLog) {
		if (oldLoginLog == null)
			return;
		if (this.loginLog != null)
			if (this.loginLog.contains(oldLoginLog)) {
				this.loginLog.remove(oldLoginLog);
				oldLoginLog.setAccount((Account_) null);
			}
	}

	public void removeAllLoginLog() {
		if (loginLog != null) {
			LoginLog_ oldLoginLog;
			for (java.util.Iterator<LoginLog_> iter = getIteratorLoginLog(); iter
					.hasNext();) {
				oldLoginLog = (LoginLog_) iter.next();
				iter.remove();
				oldLoginLog.setAccount((Account_) null);
			}
		}
	}

	public Role_ getRole() {
		return role;
	}

	public void setRole(Role_ newRole) {
		if (this.role == null || !this.role.equals(newRole)) {
			if (this.role != null) {
				Role_ oldRole = this.role;
				this.role = null;
				oldRole.removeAccount(this);
			}
			if (newRole != null) {
				this.role = newRole;
				this.role.addAccount(this);
			}
		}
	}

	public Role_ getRoleDeputy() {
		return roleDeputy;
	}

	public void setRoleDeputy(Role_ newRoleDeputy) {
		if (this.roleDeputy == null || !this.roleDeputy.equals(newRoleDeputy)) {
			if (this.roleDeputy != null) {
				Role_ oldRoleDeputy = this.roleDeputy;
				this.roleDeputy = null;
				oldRoleDeputy.removeAccountDeputy(this);
			}
			if (newRoleDeputy != null) {
				this.roleDeputy = newRoleDeputy;
				this.roleDeputy.addAccountDeputy(this);
			}
		}
	}

	@Override
	public boolean equalsExactly(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account_ other = (Account_) obj;
		if (activated != other.activated)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loginLog == null) {
			if (other.loginLog != null)
				return false;
		} else if (!loginLog.equals(other.loginLog))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
