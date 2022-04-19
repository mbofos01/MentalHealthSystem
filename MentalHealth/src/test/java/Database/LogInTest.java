package Database;

import org.junit.jupiter.api.Test;

import Objects.RecordsStaff;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LogInTest {
	/**
	 * This test checks to see if login for medical records staff works right we
	 * first create a JDBC handler and the check for a fake and a real user.
	 */
	@Test
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
	public void testRealMedical() {
		JDBC database = new JDBC();
		RecordsStaff r = database.loginMedicalRecords("jwill01", "1234");
		assertNotEquals(r.getId(), -1);
	}

}
