package retail.policy;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import retail.domain.Grocery;
import retail.domain.Store;
import retail.domain.User;

/**
 * Singleton Discount policy that holds up discount calculation logic
 * @author dsg268
 *
 */
public class DiscountPolicy {

	private static final int SUBSCRIPTION_DISCOUNT = 5;
	private static final DiscountPolicy INSTANCE = new DiscountPolicy();
	
	private DiscountPolicy() {
		//Avoid direct instantiation.
	}
	
	/**
	 * Factory method to get singleton instance.
	 * 
	 * @return current instance.
	 */
	public static DiscountPolicy get() {
		return INSTANCE;
	}
	
	/**
	 * Calculate discounts ratio for user
	 * @param user
	 * @param store
	 * @param totalBill
	 * @return
	 */
	public int calculateDiscount(User user, Store store) {
		
		if(store instanceof Grocery) {
			return 0;
		}
		
		int subscriptionDiscountRate = isDiffGreaterThanTwoYears(
				user.getSubscriptionDate(), new Date()) ? SUBSCRIPTION_DISCOUNT : 0;
		
		return user.getDiscountRate() > 0 
				? user.getDiscountRate()
				: subscriptionDiscountRate;
	}
	
	private boolean isDiffGreaterThanTwoYears(Date d1, Date d2) {
		
		if(d1.after(d2)) {
			throw new IllegalArgumentException("first date cannot be greater than second date");
		}
		
		long firstDateInMillis = d1.getTime();
		long seciondDateInMillis = d2.getTime();
		long diff = seciondDateInMillis - firstDateInMillis;
		
		//System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= (365 * 2);
	}
	
	public int getBillDiscount(double totalBill) {
		
		return totalBill >= 100 ? ((int) (totalBill / 100)) * 5 : 0;
	}
}
