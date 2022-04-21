package Database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Drug;

class DrugTest {
	static JDBC database = new JDBC();
	static Drug r = database.getDrug(0);

	@Test
	@DisplayName("Ensure a wrong id doesn't fetch a non-drug")
	public void testFakeDrug() {
		JDBC database = new JDBC();
		Drug r = database.getDrug(-1);
		assertNull(r.getCommercial_name(), "This id does not match with a trug so the name should be null");

	}

	@Test
	@DisplayName("Ensure a correct id fetches the correct drug - ID")
	public void testRealDrugID() {
		assertEquals(r.getId(), 0);
	}

	@Test
	@DisplayName("Ensure a correct id fetches the correct drug - Commercial Name")
	public void testRealDrugCommericalName() {
		assertEquals(r.getCommercial_name(), "Celexa");

	}

	@Test
	@DisplayName("Ensure a correct id fetches the correct drug -  Name")
	public void testRealDrugName() {
		assertEquals(r.getName(), "Citalopram");
	}

	@Test
	@DisplayName("Ensure a correct id fetches the correct drug - Side Effects")
	public void testRealDrugSideEffects() {

		assertEquals(r.getSide_effect(), "Headackes");
	}
}
