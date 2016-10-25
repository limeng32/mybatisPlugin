package limeng32.mybatis.mybatisPlugin.test;

import java.util.Collection;
import java.util.Date;

import limeng32.mybatis.mybatisPlugin.AccountService;
import limeng32.mybatis.mybatisPlugin.LoginLogService;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.condition.LoginLog_Condition;

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
import com.github.springtestdbunit.dataset.FlatXmlDataSetLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = FlatXmlDataSetLoader.class)
@ContextConfiguration("classpath:spring-test.xml")
public class NotEqualConditionTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginLogService loginLogService;

	/** 测试condition:greaterThan功能 */
	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/NotEqualConditionTest.testConditionGreaterThan.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/NotEqualConditionTest.testConditionGreaterThan.xml")
	public void testConditionGreaterThan() {
		LoginLog_Condition lc = new LoginLog_Condition();
		lc.setLoginTimeGreaterThan(new Date(1));
		lc.setIdGreaterThan(0);
		Collection<LoginLog_> c = loginLogService.selectAll(lc);
		Assert.assertEquals(1, c.size());
		LoginLog_[] loginlogs = c.toArray(new LoginLog_[c.size()]);
		Assert.assertEquals("0.0.0.1", loginlogs[0].getLoginIP());
		LoginLog_Condition lc2 = new LoginLog_Condition();
		lc2.setLoginTimeGreaterThan(new Date());
		Collection<LoginLog_> c2 = loginLogService.selectAll(lc2);
		Assert.assertEquals(0, c2.size());
	}
}
