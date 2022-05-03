package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Appointment;

class AppointmentTest {
	static JDBC database = new JDBC();
	static ArrayList<Appointment> a = database.getDoctorsAppointments(2, "2022-04-26");
	static ArrayList<Appointment> d = database.getPatientAppointmentsWithDoctor(2, 1);

	@Test
	@DisplayName("Ensure that an appointment is fetced correctly - Appointment ID")
	void testAppointmentID() {
		assertEquals(a.get(0).getAppoint_id(), 3);
	}

	@Test
	@DisplayName("Ensure that an appointment is fetced correctly - Appointment Time")
	void testAppointmentTime() {
		assertEquals(a.get(0).getTime(), "10:00");
	}

	@Test
	@DisplayName("Ensure that an appointment is fetced correctly - Appointment Type")
	void testAppointmentType() {
		assertEquals(a.get(0).getType(), "Scheduled");
	}

	@Test
	@DisplayName("Ensure that an appointment is fetced correctly - Appointment Patient")
	void testAppointmentPatient() {
		assertEquals(a.get(0).getPatient_id(), 1);
	}

	@Test
	@DisplayName("Ensure that an appointment is fetced correctly - Record ID")
	void testAppointmentDoctor() {
		assertEquals(d.get(0).getRecord_id(), 61);
	}

}
