package Database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Doctor;

class DoctorTest {
	static JDBC database = new JDBC();
	static Doctor d = database.loginClinical("test01", "1234");

	@Test
	@DisplayName("Ensure doctor id is fetched correctly")
	void testDocID() {
		assertEquals(d.getId(), 2);
	}

	@Test
	@DisplayName("Ensure doctor clinic id is fetched correctly")
	void testClinicID() {
		assertEquals(d.getClinic_id(), 3);
	}

	@Test
	@DisplayName("Ensure doctor name is fetched correctly")
	void testDocName() {
		assertEquals(d.getName(), "Tester");
	}

	@Test
	@DisplayName("Ensure doctor surname is fetched correctly")
	void testDocSurname() {
		assertEquals(d.getSurname(), "Function");
	}
}
