package limeng32.mybatis.mybatisPlugin.test;

import java.util.Collection;

import limeng32.mybatis.mybatisPlugin.AccountService;
import limeng32.mybatis.mybatisPlugin.Account_;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Conditionable;
import limeng32.mybatis.mybatisPlugin.cachePlugin.Order;
import limeng32.mybatis.mybatisPlugin.cachePlugin.PageParam;
import limeng32.mybatis.mybatisPlugin.cachePlugin.SortParam;
import limeng32.mybatis.mybatisPlugin.condition.Account_Condition;

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
public class AccountTest {

	@Autowired
	private BasicDataSource dataSource;

	@Autowired
	private AccountService accountService;

	@Test
	public void testDataSource() {
		Assert.assertNotNull(dataSource);
		Assert.assertNotNull(dataSource.getUsername());
	}

	/** 测试insert功能（有乐观锁） */
	@Test
	@DatabaseSetup(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testInsert.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testInsert.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testInsert.xml")
	public void testInsert() {
		Account_ a = new Account_();
		a.setId(1);
		a.setName("ann");
		a.setEmail("ann@live.cn");
		a.setPassword("5a690d842935c51f26f473e025c1b97a");
		a.setActivated(true);
		a.setActivateValue("");
		accountService.insert(a);
	}

	/** 测试update功能（有乐观锁） */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testUpdate.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testUpdate.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testUpdate.xml")
	public void testUpdate() {
		Account_ a = accountService.select(1);
		a.setEmail("ann@tom.com");
		a.setActivated(false);
		accountService.update(a);
	}

	/** 测试delete功能 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testDelete.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testDelete.result.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testDelete.xml")
	public void testDelete() {
		Account_ a = accountService.select(1);
		accountService.delete(a);
	}

	/** 测试condition:like功能 */
	@Test
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

	/** 测试condition:headLike功能 */
	@Test
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

	/** 测试sorter功能 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testSorter.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testSorter.xml")
	public void testSorter() {
		Account_Condition ac = new Account_Condition();
		ac.setSorter(new SortParam(new Order(Account_Condition.field_name,
				Conditionable.Sequence.desc)));
		Collection<Account_> c = accountService.selectAll(ac);
		Account_[] accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals("ann", accounts[3].getName());
		Assert.assertEquals("5a690d842935c51f26f473e025c1b97a",
				accounts[0].getPassword());
		ac.setSorter(new SortParam(new Order(Account_Condition.field_name,
				Conditionable.Sequence.desc), new Order(
				Account_Condition.field_password, Conditionable.Sequence.desc)));
		c = accountService.selectAll(ac);
		accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals("6a690d842935c51f26f473e025c1b97a",
				accounts[0].getPassword());
		ac.setSorter(new SortParam(new Order(Account_Condition.field_name,
				Conditionable.Sequence.desc), new Order(
				Account_Condition.field_name, Conditionable.Sequence.asc)));
		c = accountService.selectAll(ac);
		accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals("ann", accounts[3].getName());
	}

	/** 测试limiter功能 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testLimiter.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "/limeng32/mybatis/mybatisPlugin/test/AccountTest.testLimiter.xml")
	public void testLimiter() {
		Account_Condition ac = new Account_Condition();
		ac.setLimiter(new PageParam(1, 2));
		Collection<Account_> c = accountService.selectAll(ac);
		Account_[] accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals(2, accounts.length);
		Assert.assertEquals(1, accounts[0].getId().intValue());
		Assert.assertEquals(2, accounts[1].getId().intValue());
		Assert.assertEquals(2, ac.getLimiter().getMaxPageNum());
		ac.setSorter(new SortParam(new Order(Account_Condition.field_id,
				Conditionable.Sequence.desc)));
		c = accountService.selectAll(ac);
		accounts = c.toArray(new Account_[c.size()]);
		Assert.assertEquals(2, accounts.length);
		Assert.assertEquals(4, accounts[0].getId().intValue());
		Assert.assertEquals(3, accounts[1].getId().intValue());
	}
}
