package Database;

import static org.junit.Assert.*;

import org.junit.Test;

import Objects.RecordsStaff;

public class LogInTest {
	/**
	 * This test checks to see if login for medical records staff works right we
	 * first create a JDBC handler and the check for a fake and a real user.
	 */
	@Test
	public void testFakeMedical() {
		JDBC database = new JDBC();
		RecordsStaff r = database.loginMedicalRecords("totallyNotARealUser", "wrongpass");
		assertEquals("Testing Medical Records Log In", r.getId(), -1);

	}

	/**
	 * This test checks to see if login for medical records staff works right we
	 * first create a JDBC handler and the check for a fake and a real user.
	 */
	@Test
	public void testRealMedical() {
		JDBC database = new JDBC();
		RecordsStaff r = database.loginMedicalRecords("jwill01", "1234");
		assertNotEquals("Testing Medical Records Log In", r.getId(), -1);
	}

}
