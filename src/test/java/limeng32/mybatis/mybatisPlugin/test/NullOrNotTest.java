package limeng32.mybatis.mybatisPlugin.test;

import java.util.Collection;

import junit.framework.Assert;
import limeng32.mybatis.mybatisPlugin.AccountService;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.condition.Account_Condition;

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
public class NullOrNotTest {

	@Autowired
	private AccountService accountService;

	/** 测试NullOrNot关键字 */
	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/nullOrNotTest/testNullOrNot.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/nullOrNotTest/testNullOrNot.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/nullOrNotTest/testNullOrNot.xml")
	public void testNullOrNot() {
		Account_Condition ac = new Account_Condition();
		ac.setEmailIsNull(true);
		Collection<Account_> accountC = accountService.selectAll(ac);
		Assert.assertEquals(1, accountC.size());
		int count = accountService.count(ac);
		Assert.assertEquals(1, count);

		Account_Condition ac2 = new Account_Condition();
		ac2.setEmailIsNull(false);
		Collection<Account_> accountC2 = accountService.selectAll(ac2);
		Assert.assertEquals(1, accountC2.size());
		int count2 = accountService.count(ac2);
		Assert.assertEquals(1, count2);

		Account_Condition ac3 = new Account_Condition();
		Collection<Account_> accountC3 = accountService.selectAll(ac3);
		Assert.assertEquals(2, accountC3.size());
		int count3 = accountService.count(ac3);
		Assert.assertEquals(2, count3);

		Account_Condition ac4 = new Account_Condition();
		ac4.setRoleIsNull(true);
		Collection<Account_> accountC4 = accountService.selectAll(ac4);
		for (Account_ a : accountC4) {
			Assert.assertEquals("bob", a.getName());
		}
		int count4 = accountService.count(ac4);
		Assert.assertEquals(1, count4);

		Account_Condition ac5 = new Account_Condition();
		ac5.setRoleIsNull(false);
		Collection<Account_> accountC5 = accountService.selectAll(ac5);
		for (Account_ a : accountC5) {
			Assert.assertEquals("ann", a.getName());
		}
		int count5 = accountService.count(ac5);
		Assert.assertEquals(1, count5);
	}
}
