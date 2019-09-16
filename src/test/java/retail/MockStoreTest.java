package retail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import retail.domain.Grocery;
import retail.domain.NonGrocery;
import retail.domain.NormalUser;
import retail.domain.Store;
import retail.domain.User;
import retail.service.BillCalculatorService;
import retail.service.StoreService;

public class MockStoreTest {
	
	@Test
	public void mockUserTypeCheck(
			final @Tested(fullyInitialized = true) BillCalculatorService billCalculatorService,
			final @Mocked StoreService storeService) {
		
		final Store store = new NonGrocery();
		final NormalUser normalUser = new NormalUser("ibrahim", new Date());
		Double totalBill = 550.0;
		
		//Arrange/Record
		new Expectations() {{
			storeService.checkUserType((NormalUser) any, (Store) any); times = 1;
		}};
		//Act/Replay
		billCalculatorService.calculateBill(normalUser, store, totalBill);
		//Assert/Verify
		new Verifications() {{
			//Verify billCalculatorService behavior that it has successfully called
			//storeService.checkUserType method.
			storeService.checkUserType(withAny((NormalUser)any), withAny((Store) any)); times = 1;
		}};
	}
	
	@Test
	public void checkUserTypeCoverage() {
		
		StoreService storeService = StoreService.get();
		User user = storeService.checkUserType(new NormalUser("ibrahim", new Date()), new Grocery());
		
		Assert.assertNotNull(user);
	}
}