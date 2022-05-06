package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Patient;

class PatientTest {
	static JDBC database = new JDBC();
	static ArrayList<Patient> d = database.getDoctorsPatient(2);
	static ArrayList<Patient> dead = database.getDoctorsPatient(1);

	@Test
	@DisplayName("Ensure that a doctor has a specific number of patients")
	void testDoctorsPatientList() {
		assertEquals(d.size(), 1);
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient ID")
	void testPatientID() {
		assertEquals(d.get(0).getPatient_id(), 3);
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient Name")
	void testPatientName() {
		assertEquals(d.get(0).getName(), "DoNot");
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient Surname")
	void testPatientSurname() {
		assertEquals(d.get(0).getSurname(), "ChangeMe");
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient Birthday")
	void testPatientBithday() {
		assertEquals(d.get(0).getDate(), "1999-04-20");
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient Telephone")
	void testPatientTelephone() {
		assertEquals(d.get(0).getTelephone(), "123456");
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient Email")
	void testPatientEmail() {
		assertEquals(d.get(0).getEmail(), "epl441.mentalhealth@gmail.com");
	}

	@Test
	@DisplayName("Ensure that a patient is not dead")
	void testPatientAlive() {
		assertTrue(dead.get(0).isAlive());
	}

	@Test
	@DisplayName("Ensure that a patient has a specific patient Email (null)")
	void testPatientEmail2() {
		assertNotEquals(dead.get(0).getEmail(), "otheremail@gmail.com");
	}

}
