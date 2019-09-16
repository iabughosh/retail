package retail.domain;

import java.util.Date;

public class Affiliate extends User {

	public Affiliate(String name, Date subscriptionDate) {
		super(name, subscriptionDate);
	}

	@Override
	public int getDiscountRate() {
		return 10;
	}
}
