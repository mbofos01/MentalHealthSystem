package Database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Allergy;

class AllergyTest {
	static JDBC database = new JDBC();
	static ArrayList<Allergy> a = database.getPatientAllergies(3);

	@Test
	@DisplayName("Ensure patient allergies are correct DrugID")
	void testAllergyDrug() {
		assertEquals(a.get(0).getDrug_id(), 1);
	}

}
