package limeng32.mybatis.mybatisPlugin.test;

import limeng32.mybatis.mybatisPlugin.LoginLogService;
import limeng32.mybatis.mybatisPlugin.LoginLog_;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginLogTest {

	@Autowired
	private BasicDataSource dataSource;

	@Autowired
	private LoginLogService loginLogService;

	@Test
	public void testDataSource() {
		Assert.assertNotNull(dataSource);
		Assert.assertNotNull(dataSource.getUsername());
	}

	/** 测试insert功能（无乐观锁） */
	@Test
	@DatabaseSetup(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/LoginLogTest.testInsert.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/LoginLogTest.testInsert.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/LoginLogTest.testInsert.xml")
	public void testInsert() {
		LoginLog_ a = new LoginLog_();
		a.setId(1);
		a.setLoginIP("0.0.0.1");
		loginLogService.insert(a);
	}
}
