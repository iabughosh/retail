package retail.domain;

/**
 * Common interface for stores.
 * 
 * @author ibrahim
 *
 */
public interface Store {

	/**
	 * Boolean flag if store support discounts or not.
	 * 
	 * @return true if discounts are applicable, false otherwise.
	 */
	boolean isDiscountAvailable();
}
