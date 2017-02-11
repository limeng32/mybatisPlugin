package limeng32.mybatis.mybatisPlugin.test;

import limeng32.mybatis.mybatisPlugin.AccountService;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.DetailService;
import limeng32.mybatis.mybatisPlugin.Detail_;
import limeng32.mybatis.mybatisPlugin.LoginLogService;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.RoleService;
import limeng32.mybatis.mybatisPlugin.Role_;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = FlatXmlDataSetLoader.class)
@ContextConfiguration("classpath:spring-test.xml")
public class CacheTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private DetailService detailService;

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private RoleService roleService;

	@Test
	@IfProfileValue(name = "CACHE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest.test.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest.test.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest.test.result.xml")
	public void test() {
		String name = "ann";
		String newName = "bob";
		String loginIP = "0.0.0.1";

		Account_ a = new Account_();
		LoginLog_ l = new LoginLog_();

		a.setName(name);
		accountService.insert(a);

		l.setLoginIP(loginIP);
		l.setAccount(a);
		loginLogService.insert(l);

		LoginLog_ loginLog = loginLogService.select(l.getId());
		Assert.assertEquals(name, loginLog.getAccount().getName());
		Account_ account = accountService.select(a.getId());
		account.setName(newName);
		accountService.update(account);
		LoginLog_ loginLog2 = loginLogService.select(l.getId());
		Assert.assertEquals(newName, loginLog2.getAccount().getName());
	}

	@Test
	@IfProfileValue(name = "CACHE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest/test2.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest/test2.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest/test2.result.xml")
	public void test2() {
		String name = "新权限", newName = "新角色", accountName = "ann", ip = "0.0.0.1";
		Role_ r = new Role_();
		r.setName(name);
		roleService.insert(r);

		Account_ a = new Account_();
		a.setName(accountName);
		a.setRole(r);
		accountService.insert(a);

		LoginLog_ l = new LoginLog_();
		l.setLoginIP(ip);
		l.setAccount(a);
		loginLogService.insert(l);

		Account_ account = accountService.select(a.getId());
		Assert.assertEquals(name, account.getRole().getName());

		LoginLog_ loginLog = loginLogService.select(l.getId());
		Assert.assertEquals(name, loginLog.getAccount().getRole().getName());

		Role_ role = roleService.select(r.getId());
		role.setName(newName);
		roleService.update(role);

		Account_ account2 = accountService.select(a.getId());
		Assert.assertEquals(newName, account2.getRole().getName());

		LoginLog_ loginLog2 = loginLogService.select(l.getId());
		Assert.assertEquals(newName, loginLog2.getAccount().getRole().getName());
	}

	@Test
	@IfProfileValue(name = "CACHE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest/test3.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest/test3.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/CacheTest/test3.result.xml")
	public void test3() {
		String name = "新权限", newName = "新角色", newName2 = "新新角色", accountName = "ann", newAccountName = "bob", ip = "0.0.0.1", detailName = "细节";
		Role_ r = new Role_();
		r.setName(name);
		roleService.insert(r);

		Account_ a = new Account_();
		a.setName(accountName);
		a.setRole(r);
		accountService.insert(a);

		LoginLog_ l = new LoginLog_();
		l.setLoginIP(ip);
		l.setAccount(a);
		loginLogService.insert(l);

		Detail_ d = new Detail_();
		d.setName(detailName);
		d.setLoginLog(l);
		detailService.insert(d);

		Account_ account = accountService.select(a.getId());
		Assert.assertEquals(name, account.getRole().getName());

		LoginLog_ loginLog = loginLogService.select(l.getId());
		Assert.assertEquals(name, loginLog.getAccount().getRole().getName());

		Role_ role = roleService.select(r.getId());
		role.setName(newName);
		roleService.update(role);

		Account_ account2 = accountService.select(a.getId());
		Assert.assertEquals(newName, account2.getRole().getName());

		LoginLog_ loginLog2 = loginLogService.select(l.getId());
		Assert.assertEquals(newName, loginLog2.getAccount().getRole().getName());

		Detail_ detail = detailService.select(d.getId());
		Assert.assertEquals(accountName, detail.getLoginLog().getAccount()
				.getName());
		Assert.assertEquals(newName, detail.getLoginLog().getAccount()
				.getRole().getName());

		// Account_ account3 = accountService.select(a.getId());
		// account3.setName(newAccountName);
		// accountService.update(account3);

		Role_ role2 = roleService.select(r.getId());
		role2.setName(newName2);
		roleService.update(role2);

		Detail_ detail2 = detailService.select(d.getId());
		Assert.assertEquals(newName2, detail2.getLoginLog().getAccount()
				.getRole().getName());
	}
}
