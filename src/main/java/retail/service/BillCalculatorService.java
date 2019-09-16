package retail.service;

import retail.domain.NormalUser;
import retail.domain.Store;
import retail.domain.User;
import retail.policy.BillCalculatorPolicy;

/**
 * Bill calculation service.
 * 
 * @author ibrahim
 *
 */
public class BillCalculatorService {
	
	private StoreService storeService = StoreService.get();
	
	/**
	 * This method is used with mock tests.
	 */
	public Double calculateBill(NormalUser user, Store store, Double totalBill) {
		
		User checkedUser = storeService.checkUserType(user, store);
		BillCalculatorPolicy billPolicy = BillCalculatorPolicy.get();
		return billPolicy.calculateBill(checkedUser, store, totalBill);
	}
	
	/**
	 * This method is used with normal tests.
	 */
	public Double calculateBill(User user, Store store, Double totalBill) {
		
		BillCalculatorPolicy billPolicy = BillCalculatorPolicy.get();
		return billPolicy.calculateBill(user, store, totalBill);
	}
}
