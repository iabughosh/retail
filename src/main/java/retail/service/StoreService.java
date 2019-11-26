package retail.service;

import java.util.Random;

import retail.domain.Affiliate;
import retail.domain.Employee;
import retail.domain.NormalUser;
import retail.domain.Store;
import retail.domain.User;

/**
 * Singleton instance of store service which should evaluate user type if he is Affiliate, Employee 
 * or normal user. in real application, this service should connect to RDBMS and do required logic.
 * but, we are using this one just for demo and mocking.
 * 
 * @author ibrahim
 */
public class StoreService {
	
	/* Singleton instance */
	private final static StoreService INSTANCE = new StoreService();
	
	private StoreService() {
		//Avoid direct instantiation.
	}
	
	/**
	 * Factory method to get singleton instance.
	 * 
	 * @return current instance.
	 */
	public static StoreService get() {
		return INSTANCE;
	}
	
	/**
	 * This method is used for mock and test only.
	 */
	public User checkUserType(NormalUser user, Store store) {
		
		//If it is not mocked, it will return random user type.
		Random random = new Random();
		int number = random.nextInt(3);
		
		switch(number) {
		case 1: return new Affiliate(user.getName(), user.getSubscriptionDate());
		case 2: return new Employee(user.getName(), user.getSubscriptionDate());
		default:return new NormalUser(user.getName(), user.getSubscriptionDate());
		}
	}
}
