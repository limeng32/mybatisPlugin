package limeng32.mybatis.mybatisPlugin.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import limeng32.mybatis.mybatisPlugin.AccountService;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.LoginLogService;
import limeng32.mybatis.mybatisPlugin.LoginLog_;
import limeng32.mybatis.mybatisPlugin.condition.Account_Condition;

import org.apache.commons.dbcp.BasicDataSource;
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
public class ConditionTest {

	@Autowired
	private BasicDataSource dataSource;

	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginLogService loginLogService;

	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	public void testDataSource() {
		Assert.assertNotNull(dataSource);
		Assert.assertNotNull(dataSource.getUsername());
	}

	/** 测试condition:like功能 */
	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionLike.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionLike.xml")
	public void testConditionLike() {
		Account_Condition ac = new Account_Condition();
		ac.setEmailLike("%%");
		Collection<Account_> c = accountService.selectAll(ac);
		Account_[] accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals(1, accounts.length);
		Assert.assertEquals("an%%n@live.cn", accounts[0].getEmail());
	}

	/** 测试condition:like功能2：在parameter为null和为空字符串时的情况 */
	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionLike2.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionLike2.xml")
	public void testConditionLike2() {
		Account_Condition ac = new Account_Condition(), ac2 = new Account_Condition();
		ac.setEmailLike(null);
		Collection<Account_> c = accountService.selectAll(ac);
		Account_[] accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals(2, accounts.length);
		ac2.setEmailLike("");
		Collection<Account_> c2 = accountService.selectAll(ac);
		Assert.assertEquals(2, c2.size());
	}

	/** 测试condition:headLike功能 */
	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionHeadLike.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionHeadLike.xml")
	public void testConditionHeadLike() {
		Account_Condition ac = new Account_Condition();
		ac.setEmailHeadLike("ann");
		Collection<Account_> c = accountService.selectAll(ac);
		Account_[] accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals(1, accounts.length);
		Assert.assertEquals("ann@live.cn", accounts[0].getEmail());
	}

	/** 测试condition:tailLike功能 */
	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionTailLike.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testConditionTailLike.xml")
	public void testConditionTailLike() {
		Account_Condition ac = new Account_Condition();
		ac.setEmailTailLike("live.cn");
		Collection<Account_> c = accountService.selectAll(ac);
		Account_[] accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals(1, accounts.length);
		Assert.assertEquals("ann@live.cn", accounts[0].getEmail());
	}

	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	public void testSpliter() {
		String spliter = "\\s+";
		String source = " 1 2  34 ";
		String[] array = source.trim().split(spliter);
		// for (String s : array) {
		// System.out.println("1:" + s);
		// }
		Assert.assertEquals(3, array.length);
		Assert.assertEquals("34", array[2]);
		String[] array2 = source.trim().split(spliter, 2);
		// for (String s : array2) {
		// System.out.println("2:" + s);
		// }
		Assert.assertEquals(2, array2.length);
		Assert.assertEquals("2  34", array2[1]);
	}

	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/ConditionTest.testMultiLikeAND.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/ConditionTest.testMultiLikeAND.xml")
	public void testMultiLikeAND() {
		Account_Condition ac = new Account_Condition();
		ac.setName("ann");
		ac.setEmailLike("as");
		List<String> multi = new ArrayList<>();
		multi.add("a");
		multi.add("s");
		multi.add("d");
		ac.setMultiLike(multi);
		Collection<Account_> c = accountService.selectAll(ac);
		Assert.assertEquals(1, c.size());
		int conut = accountService.count(ac);
		Assert.assertEquals(1, conut);
		Account_Condition ac2 = new Account_Condition();
		ac2.setName("ann");
		ac2.setEmailLike("as");
		List<String> multi2 = new LinkedList<>();
		ac2.setMultiLike(multi2);
		Collection<Account_> c2 = accountService.selectAll(ac2);
		Assert.assertEquals(1, c2.size());
		Account_Condition ac3 = new Account_Condition();
		List<String> multi3 = new ArrayList<>();
		multi3.add(null);
		multi3.add("a");
		multi3.add(null);
		ac3.setMultiLike(multi3);
		Collection<Account_> c3 = accountService.selectAll(ac3);
		Assert.assertEquals(1, c3.size());
		LoginLog_ lc = new LoginLog_();
		Account_Condition ac4 = new Account_Condition();
		List<String> multi4 = new ArrayList<>();
		multi4.add("a");
		ac4.setMultiLike(multi4);
		lc.setAccount(ac4);
		Collection<LoginLog_> c4 = loginLogService.selectAll(lc);
		Assert.assertEquals(1, c4.size());
	}

	@Test
	@IfProfileValue(name = "VOLATILE", value = "true")
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/ConditionTest.testMultiLikeOR.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/ConditionTest.testMultiLikeOR.xml")
	public void testMultiLikeOR() {
		Account_Condition ac = new Account_Condition();
		ac.setName("ann");
		ac.setEmailLike("as");
		List<String> multi = new ArrayList<>();
		multi.add(null);
		multi.add(null);
		multi.add(null);
		multi.add(null);
		ac.setMultiLikeOR(multi);
		Collection<Account_> c = accountService.selectAll(ac);
		Assert.assertEquals(1, c.size());
		int count = accountService.count(ac);
		Assert.assertEquals(1, count);
		Account_Condition ac2 = new Account_Condition();
		ac2.setName("ann");
		ac2.setEmailLike("as");
		List<String> multi2 = new ArrayList<>();
		multi2.add(null);
		multi2.add("a");
		multi2.add("sd");
		multi2.add("z");
		ac2.setMultiLikeOR(multi2);
		Collection<Account_> c2 = accountService.selectAll(ac2);
		Assert.assertEquals(1, c2.size());
		LoginLog_ lc = new LoginLog_();
		Account_Condition ac3 = new Account_Condition();
		List<String> multi3 = new ArrayList<>();
		multi3.add(null);
		multi3.add("a");
		multi3.add("sd");
		multi3.add("z");
		ac3.setMultiLikeOR(multi3);
		lc.setAccount(ac3);
		Collection<LoginLog_> c3 = loginLogService.selectAll(lc);
		Assert.assertEquals(1, c3.size());
	}
}
