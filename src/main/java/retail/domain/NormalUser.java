package retail.domain;

import java.util.Date;

public class NormalUser extends User {

	public NormalUser(String name, Date subscriptionDate) {
		super(name, subscriptionDate);
	}

	@Override
	public int getDiscountRate() {
		return 0;
	}

}
