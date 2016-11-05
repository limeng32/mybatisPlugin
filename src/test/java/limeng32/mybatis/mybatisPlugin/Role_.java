package limeng32.mybatis.mybatisPlugin;

import java.io.Serializable;

import limeng32.mirage.util.pojo.PojoSupport;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.FieldMapperAnnotation;
import limeng32.mybatis.mybatisPlugin.mapperPlugin.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

@TableMapperAnnotation(tableName = "role_")
public class Role_ extends PojoSupport<Role_> implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;

	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
	private java.lang.String name;

	private java.util.Collection<Account_> account;

	@Override
	public boolean equalsExactly(Object arg0) {
		return false;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.util.Collection<Account_> getAccount() {
		if (account == null)
			account = new java.util.LinkedHashSet<Account_>();
		return account;
	}

	public java.util.Iterator<Account_> getIteratorAccount() {
		if (account == null)
			account = new java.util.LinkedHashSet<Account_>();
		return account.iterator();
	}

	public void setAccount(java.util.Collection<Account_> newAccount) {
		removeAllAccount();
		for (java.util.Iterator<Account_> iter = newAccount.iterator(); iter
				.hasNext();)
			addAccount((Account_) iter.next());
	}

	public void addAccount(Account_ newAccount) {
		if (newAccount == null)
			return;
		if (this.account == null)
			this.account = new java.util.LinkedHashSet<Account_>();
		if (!this.account.contains(newAccount)) {
			this.account.add(newAccount);
			newAccount.setRole(this);
		}
	}

	public void removeAccount(Account_ oldAccount) {
		if (oldAccount == null)
			return;
		if (this.account != null)
			if (this.account.contains(oldAccount)) {
				this.account.remove(oldAccount);
				oldAccount.setRole((Role_) null);
			}
	}

	public void removeAllAccount() {
		if (account != null) {
			Account_ oldAccount;
			for (java.util.Iterator<Account_> iter = getIteratorAccount(); iter
					.hasNext();) {
				oldAccount = (Account_) iter.next();
				iter.remove();
				oldAccount.setRole((Role_) null);
			}
		}
	}
}
