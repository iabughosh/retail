package retail.domain;

import java.util.Date;

public class Employee extends User {

	public Employee(String name, Date subscriptionDate) {
		super(name, subscriptionDate);
	}

	@Override
	public int getDiscountRate() {
		return 30;
	}

}
