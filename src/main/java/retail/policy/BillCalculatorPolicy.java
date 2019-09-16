package retail.policy;

import java.util.Objects;

import retail.domain.Grocery;
import retail.domain.Store;
import retail.domain.User;

/**
 * Singleton Bill calculator policy. it calculate bill after discounts if they are applicable.
 * 
 * @author ibrahim
 *
 */
public class BillCalculatorPolicy {
	
	/* Singleton instance */
	private static final BillCalculatorPolicy INSTANCE = new BillCalculatorPolicy();
	
	private BillCalculatorPolicy() {
		//Avoid direct instantiation.
	}
	
	/**
	 * Factory method to get singleton instance.
	 * 
	 * @return current instance.
	 */
	public static BillCalculatorPolicy get() {
		return INSTANCE;
	}
	
	/**
	 * Calculates bill and return total amount after discounts if it is applicable.
	 * 
	 * @param user customer who applied the bill.
	 * @param store store where bill is being issued.
	 * @param totalBill total bill before discount.
	 * 
	 * @return total amount after discounts if applicable.
	 */
	public Double calculateBill(User user, Store store, double totalBill) {
			
		Objects.requireNonNull(user, "user cannot be null");
		Objects.requireNonNull(store, "store cannot be null");
		if(totalBill <= 0) {
			throw new IllegalArgumentException(String.format("Ivalid bill value %d", (int)totalBill));
		}
		
		if(store instanceof Grocery) {
			return totalBill;
		}
		
		DiscountPolicy discountPolicy = DiscountPolicy.get();
		int totalDiscountRatio = discountPolicy.calculateDiscount(user, store);
		int extraDiscount = discountPolicy.getBillDiscount(totalBill);
		
		return totalBill - (extraDiscount + (totalBill * (totalDiscountRatio / 100.0)));
	}
}
