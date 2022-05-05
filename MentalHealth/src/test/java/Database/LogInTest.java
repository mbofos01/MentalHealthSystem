package Database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.HealthServ;
import Objects.ReceptionistObj;
import Objects.RecordsStaff;

public class LogInTest {
	/**
	 * This test checks to see if login for medical records staff works right we
	 * first create a JDBC handler and the check for a fake and a real user.
	 */
	@Test
	@DisplayName("Ensure that a fake doctor is not logged in")
	public void testFakeMedical() {
		JDBC database = new JDBC();
		RecordsStaff r = database.loginMedicalRecords("totallyNotARealUser", "wrongpass");
		assertEquals(r.getId(), -1);

	}

	/**
	 * This test checks to see if login for medical records staff works right we
	 * first create a JDBC handler and the check for a fake and a real user.
	 */
	@Test
	@DisplayName("Ensure that a medical records person is correctly logged in")
	public void testRealMedical() {
		JDBC database = new JDBC();
		RecordsStaff r = database.loginMedicalRecords("jwill01", "1234");
		assertNotEquals(r.getId(), -1);
	}

	@Test
	@DisplayName("Ensure that a receptionist is correctly logged in")
	public void testRealReceptionist() {
		JDBC database = new JDBC();
		ReceptionistObj r = database.loginReceptionist("test", "1234");
		assertNotEquals(r.getId(), -1);
	}

	@Test
	@DisplayName("Ensure that a health service person is correctly logged in")
	public void testRealHealthService() {
		JDBC database = new JDBC();
		HealthServ r = database.loginHealthService("dhadji02", "1234");
		assertNotEquals(r.getId(), -1);
	}

}
