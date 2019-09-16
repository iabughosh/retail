package retail.domain;

import java.util.Date;
import java.util.Objects;

/**
 * Immutable class holds customer's main information.
 * 
 * @author ibrahim
 *
 */
public abstract class User {

	private final String name;
	private final Date subscriptionDate;
	
	/**
	 * Override super constructor.
	 * 
	 * @param name Customer name
	 * @param subscriptionDate subscription date of the customer.
	 */
	public User(String name, Date subscriptionDate) {
		Objects.requireNonNull(name, "Name cannot be null");
		Objects.requireNonNull(subscriptionDate, "subscriptionDate cannot be null");
		
		this.name = name;
		this.subscriptionDate = (Date)subscriptionDate.clone();
	}
	
	/**
	 * Get customer's name.
	 * 
	 * @return customer's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get customer's subscription date.
	 * 
	 * @return subscription date.
	 */
	public Date getSubscriptionDate() {
		return (Date)subscriptionDate.clone();
	}
	
	/**
	 * Get customer discount rate.
	 * 
	 * @return customer discount rate
	 */
	public abstract int getDiscountRate();
}
