package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.PatientRecord;

class RecordTest {
	static JDBC database = new JDBC();
	static ArrayList<PatientRecord> d = database.getPatientRecords(5, 1);

	@Test
	@DisplayName("Ensure patient records are correct - Record ID")
	void testRecordID1() {
		assertEquals(d.get(0).getRecord_id(), 87);
	}

	@Test
	@DisplayName("Ensure patient records are correct - Record ID")
	void testRecordID2() {
		assertEquals(d.get(1).getRecord_id(), 88);
	}

	@Test
	@DisplayName("Ensure patient records are correct - Date")
	void testRecordDate1() {
		assertEquals(d.get(0).getDate(), "2022-05-05");
	}

	@Test
	@DisplayName("Ensure patient records are correct - Date")
	void testRecordDate2() {
		assertEquals(d.get(1).getDate(), "2022-05-05");
	}

	@Test
	@DisplayName("Ensure patient records are correct - Self Harm")
	void testRecordSelfHarm1() {
		assertFalse(d.get(0).isSelf_harm());
	}

	@Test
	@DisplayName("Ensure patient records are correct - Self Harm")
	void testRecordSelfHarm2() {
		assertTrue(d.get(1).isSelf_harm());
	}

	@Test
	@DisplayName("Ensure patient records are correct - Threat")
	void testRecordThreat1() {
		assertTrue(d.get(0).isThreat());
	}

	@Test
	@DisplayName("Ensure patient records are correct - Threat")
	void testRecordThreat2() {
		assertTrue(d.get(1).isThreat());
	}

	@Test
	@DisplayName("Ensure patient records are correct - Treatment ID")
	void testRecordTreatment1() {
		assertEquals(d.get(0).getTreatment_id(), 80);
	}

	@Test
	@DisplayName("Ensure patient records are correct - Treatment ID")
	void testRecordTreatment2() {
		assertNotNull(d.get(0).getTreatment());
	}

	@Test
	@DisplayName("Ensure patient records are correct - Treatment ID")
	void testRecordTreatment3() {
		assertEquals(d.get(1).getTreatment_id(), -1);
	}

	@Test
	@DisplayName("Ensure patient records are correct - Treatment ID")
	void testRecordTreatment4() {
		assertNull(d.get(1).getTreatment());
	}
}
