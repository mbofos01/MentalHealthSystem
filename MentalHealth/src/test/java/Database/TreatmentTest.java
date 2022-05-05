package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.PatientRecord;
import Objects.Treatment;

class TreatmentTest {
	static JDBC database = new JDBC();
	static ArrayList<PatientRecord> d = database.getPatientRecords(5, 1);
	static Treatment treat = d.get(0).getTreatment();

	@Test
	@DisplayName("Ensure treatment is fetched correct - Dosage")
	void testDosage() {
		assertEquals(treat.getDose(), 7);
	}

	@Test
	@DisplayName("Ensure treatment is fetched correct - Treatment ID")
	void testID() {
		assertEquals(treat.getTreatment_id(), 80);
	}

	@Test
	@DisplayName("Ensure treatment is fetched correct - Comment")
	void testComment() {
		assertEquals(treat.getComments(), "every day");
	}

	@Test
	@DisplayName("Ensure treatment is fetched correct - Warning")
	void testWarning() {
		assertTrue(treat.isWarning());
	}

	@Test
	@DisplayName("Ensure treatment is fetched correct - Drug ID")
	void testDrug() {
		assertEquals(treat.getDrug_id(), 1);
	}

	@Test
	@DisplayName("Ensure treatment is fetched correct - Date")
	void TestDate() {
		assertEquals(treat.getDate(), "2022-05-01");
	}

	@Test
	@DisplayName("Ensure treatment is fetched correct - Last Update")
	void TestUpdate() {
		assertEquals(treat.getLast_updated(), "2022-05-05");
	}
}
