package retail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retail.domain.Affiliate;
import retail.domain.Employee;
import retail.domain.Grocery;
import retail.domain.NonGrocery;
import retail.domain.NormalUser;
import retail.domain.Store;
import retail.domain.User;
import retail.policy.DiscountPolicy;
import retail.service.BillCalculatorService;

public class BillCalculationTest {

	private Date subscriptionDateGt2;
	private Date subscriptionDateLt2;
	private Date futureDate;
	private Double amount;
	private static final Logger LOG = LoggerFactory.getLogger(BillCalculationTest.class);
	
	@Before
	public void setUp() {
		Calendar currentTime = Calendar.getInstance();
		currentTime.add(Calendar.YEAR, -2);
		subscriptionDateGt2 = currentTime.getTime();
		
		currentTime = Calendar.getInstance();
		currentTime.add(Calendar.MONTH, -10);
		subscriptionDateLt2 = currentTime.getTime();
		
		currentTime = Calendar.getInstance();
		currentTime.add(Calendar.YEAR, 2);
		futureDate = currentTime.getTime();
		
		amount = System.getProperty("amount") != null 
				? Double.parseDouble(System.getProperty("amount"))
				: 600;
	}
	
	@Test
	public void testGrocery() {
		
		Store grocery = new Grocery();
		User anyUser = new NormalUser("ibrahim", subscriptionDateGt2);
		
		Assert.assertFalse(grocery.isDiscountAvailable());
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(anyUser, grocery);
		
		Assert.assertTrue(totalDiscountRatio == 0);
		Assert.assertTrue(anyUser.getDiscountRate() == 0);
		Assert.assertEquals("ibrahim", anyUser.getName());
		
		BillCalculatorService billService = new BillCalculatorService();
		Double totalBill = billService.calculateBill(anyUser, grocery, amount);
		
		Assert.assertEquals(amount, totalBill);
	}
	
	@Test
	public void testNonGroceryEmployeeLt2() {
		
		Store nonGrocery = new NonGrocery();
		Assert.assertTrue(nonGrocery.isDiscountAvailable());
		User anyUser = new Employee("ibrahim", subscriptionDateLt2);
		
		BillCalculatorService billService = new BillCalculatorService();
		Double totalBill = billService.calculateBill(anyUser, nonGrocery, amount);
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(anyUser, nonGrocery);
		int extraDiscount = discountPolicy.getBillDiscount(amount);
		Assert.assertEquals(totalDiscountRatio > 0 ? 30 : extraDiscount, 
				totalDiscountRatio > 0 ? totalDiscountRatio : extraDiscount);
		
		Double expected = amount - (extraDiscount + (amount * (totalDiscountRatio / 100.0)));
		LOG.info("Employee test, Total bill is [{}] and after discount [{}].\n", amount, totalBill);
		Assert.assertEquals(expected, totalBill);
	}
	
	@Test
	public void testNonGroceryEmployeeGt2() {
		
		Store nonGrocery = new NonGrocery();
		Assert.assertTrue(nonGrocery.isDiscountAvailable());
		User anyUser = new Employee("ibrahim", subscriptionDateGt2);
		Assert.assertTrue(anyUser.getDiscountRate() > 0);
		
		BillCalculatorService billService = new BillCalculatorService();
		Double totalBill = billService.calculateBill(anyUser, nonGrocery, amount);
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(anyUser, nonGrocery);
		int extraDiscount = discountPolicy.getBillDiscount(amount);
		
		Assert.assertEquals(totalDiscountRatio > 0 ? 30 : extraDiscount, 
				totalDiscountRatio > 0 ? totalDiscountRatio : extraDiscount);
		Assert.assertTrue(totalDiscountRatio > 0);
		
		Double expected = amount - (extraDiscount + (amount * (totalDiscountRatio / 100.0)));
		LOG.info("Loyal employee !!!, Total bill is [{}] and after discount [{}].\n", amount, totalBill);
		Assert.assertEquals(expected, totalBill);
	}
	
	@Test
	public void testNonGroceryAffiliateGt2() {
		
		Store nonGrocery = new NonGrocery();
		Assert.assertTrue(nonGrocery.isDiscountAvailable());
		User anyUser = new Affiliate("ibrahim", subscriptionDateGt2);
		Assert.assertTrue(anyUser.getDiscountRate() > 0);
		
		BillCalculatorService billService = new BillCalculatorService();
		Double totalBill = billService.calculateBill(anyUser, nonGrocery, amount);
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(anyUser, nonGrocery);
		int extraDiscount = discountPolicy.getBillDiscount(amount);
		
		Assert.assertEquals(totalDiscountRatio > 0 ? 10 : extraDiscount, 
				totalDiscountRatio > 0 ? totalDiscountRatio : extraDiscount);
		
		Double expected = amount - (extraDiscount + (amount * (totalDiscountRatio / 100.0)));
		LOG.info("Loyal affiliate !!!, Total bill is [{}] and after discount [{}].\n", amount, totalBill);
		Assert.assertEquals(expected, totalBill);
	}
	
	@Test
	public void testNonGroceryAffiliateLt2() {
		
		Store nonGrocery = new NonGrocery();
		Assert.assertTrue(nonGrocery.isDiscountAvailable());
		User anyUser = new Affiliate("ibrahim", subscriptionDateGt2);
		Assert.assertTrue(anyUser.getDiscountRate() > 0);
		
		BillCalculatorService billService = new BillCalculatorService();
		Double totalBill = billService.calculateBill(anyUser, nonGrocery, amount);
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(anyUser, nonGrocery);
		int extraDiscount = discountPolicy.getBillDiscount(amount);
		
		Assert.assertEquals(totalDiscountRatio > 0 ? 10 : extraDiscount, 
				totalDiscountRatio > 0 ? totalDiscountRatio : extraDiscount);
		
		Double expected = amount - (extraDiscount + (amount * (totalDiscountRatio / 100.0)));
		LOG.info("Affiliate test, Total bill is [{}] and after discount [{}].\n", amount, totalBill);
		Assert.assertEquals(expected, totalBill);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSubscriptionDate() {
		
		Store nonGrocery = new NonGrocery();
		Assert.assertTrue(nonGrocery.isDiscountAvailable());
		User anyUser = new Affiliate("ibrahim", futureDate);
		Assert.assertTrue(anyUser.getDiscountRate() > 0);
		
		BillCalculatorService billService = new BillCalculatorService();
		Double totalBill = billService.calculateBill(anyUser, nonGrocery, amount);
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(anyUser, nonGrocery);
		int extraDiscount = discountPolicy.getBillDiscount(amount);
		
		Double expected = amount - (extraDiscount + (amount * (totalDiscountRatio / 100.0)));
		LOG.info("Affiliate test, Total bill is [{}] and after discount [{}].\n", amount, totalBill);
		Assert.assertEquals(expected, totalBill);
	}
	
	@Test
	public void testLessThanHundered() {
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int billDiscount = discountPolicy.getBillDiscount(90);
		
		Assert.assertTrue(billDiscount == 0);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullSubscriptionDate() {
		
		Store grocery = new Grocery();
		User anyUser = new NormalUser("ibrahim", null);
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		discountPolicy.calculateDiscount(anyUser, grocery);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidBillAmount() {
		
		Store grocery = new Grocery();
		User anyUser = new NormalUser("ibrahim", new Date());
		
		BillCalculatorService billCalculatorService = new BillCalculatorService();
		billCalculatorService.calculateBill(anyUser, grocery, 0.0);
	}
}
