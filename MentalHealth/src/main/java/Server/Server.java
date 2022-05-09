package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import Database.JDBC;
import Objects.Allergy;
import Objects.Appointment;
import Objects.Clinic;
import Objects.Comment;
import Objects.Condition;
import Objects.Doctor;
import Objects.Drug;
import Objects.HealthServ;
import Objects.Patient;
import Objects.PatientRecord;
import Objects.ReceptionistObj;
import Objects.RecordsStaff;
import Objects.ReportData;
import Objects.Request;
import Objects.Treatment;
import Tools.Clock;
import Tools.FileResourcesUtils;
import Tools.Query;
import Tools.Viewpoint;

/**
 * This is the server class. Our server communicates with all the clients and
 * then passes each request to the JDBC object whom then communicates with the
 * MSSQL Database to actually execute the stored procedures.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Server {

	private static class TCPWorker implements Runnable {

		/**
		 * The socket
		 */
		private Socket client;
		/**
		 * The client buffer
		 */
		private String clientbuffer;
		/**
		 * The database
		 */
		private static JDBC database = new JDBC();

		public TCPWorker(Socket client) {
			this.client = client;
			this.clientbuffer = "127.0.0.1";
		}

		public void run() {

			try {
				System.out.println("Client connected with: " + this.client.getInetAddress());

				DataOutputStream output = new DataOutputStream(client.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

				this.clientbuffer = reader.readLine();
				Query incoming = new Gson().fromJson(clientbuffer, Query.class);

				if (incoming.getClient() == Viewpoint.MedicalRecords)
					HandleMedicalRecords(incoming, output);
				else if (incoming.getClient() == Viewpoint.Clinical)
					HandleClinical(incoming, output);
				else if (incoming.getClient() == Viewpoint.Receptionist)
					HandleReceptionist(incoming, output);
				else if (incoming.getClient() == Viewpoint.HealthService)
					HandleHealthService(incoming, output);
				else {
					System.out.println("UNKNOWN CLIENT ISSUED REQUEST");
				}

				System.out.println("[" + new Date() + "] Received: " + this.clientbuffer);

			} catch (IOException e) {
				System.out.println("Connection with client ended unexpectedly");
			}

		}

		/**
		 * This method is used to handle requests from the Health Service Viewpoint.
		 * 
		 * @param incoming Actual Query object
		 * @param output   Output Stream
		 * @throws IOException
		 */
		private void HandleHealthService(Query incoming, DataOutputStream output) throws IOException {
			/**
			 * login - Health Service viewpoint - request a Health Service Staff member
			 * login
			 * 
			 * Parameters: username and password
			 */
			if (incoming.getFunction().equals("login")) {
				HealthServ hs = database.loginHealthService(incoming.getArguments().get(0),
						incoming.getArguments().get(1));
				if (hs == null) {
					hs = new HealthServ();
					hs.emptyValue();
				}
				output.writeBytes(new Gson().toJson(hs) + System.lineSeparator());
			}
			/**
			 * generateWeeklyReport - Health Service viewpoint may request the creation of a
			 * weekly report on a specific clinic.
			 * 
			 * Parameters: Clinic ID
			 */
			else if (incoming.getFunction().equals("generateWeeklyReport")) {
				int clinic_id = Integer.parseInt(incoming.getArguments().get(0));
				ReportData rp = new ReportData();
				rp.setVisitors(database.getWeeksAppointments(clinic_id));
				rp.setClinic(database.getClinic(clinic_id));
				rp.setDates(Clock.getLastWeek());
				rp.setConditionCounter(database.getPatientsOfEachCondition(clinic_id));
				rp.setTreatmentCounter(database.getPatientsOfEachTreatment(clinic_id));
				try {
					output.writeBytes(new Gson().toJson(rp) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getConditions - Health Service Viewpoint may request a list with all the
			 * conditions in the database.
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getConditions")) {
				ArrayList<Condition> cond = database.getConditions();
				try {
					output.writeBytes(new Gson().toJson(cond.size()) + System.lineSeparator());
					for (int i = 0; i < cond.size(); i++)
						output.writeBytes(new Gson().toJson(cond.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getDrugs - Health Service Viewpoint may request a list with all the drugs in
			 * the database.
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getDrugs")) {
				ArrayList<Drug> rec = database.getDrugList();
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getClinics - Health Service may request a list with all the clinics in the
			 * database.
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getClinics")) {
				ArrayList<Clinic> rec = database.getClinics();
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatientsTreatmentCond - Health Service may request a list with all the
			 * patients that have got a specific treatment with a specific condition
			 * 
			 * Parameters: treatment and condition
			 */
			else if (incoming.getFunction().equals("getPatientsTreatmentCond")) {
				int treatment, cond;
				cond = Integer.parseInt(incoming.getArguments().get(0));
				treatment = Integer.parseInt(incoming.getArguments().get(1));
				ArrayList<Patient> rec = database.getReport2(cond, treatment);
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * This method is used to handle requests from the Receptionist Viewpoint.
		 * 
		 * @param incoming Actual Query object
		 * @param output   Output Stream
		 * @throws IOException
		 */
		private void HandleReceptionist(Query incoming, DataOutputStream output) throws IOException {
			/**
			 * login - Receptionist viewpoint may request a receptionist login
			 * 
			 * Parameters: username and password
			 */
			if (incoming.getFunction().equals("login")) {
				ReceptionistObj hs = database.loginReceptionist(incoming.getArguments().get(0),
						incoming.getArguments().get(1));
				if (hs == null) {
					hs = new ReceptionistObj();
					hs.emptyValue();
				}
				output.writeBytes(new Gson().toJson(hs) + System.lineSeparator());
			}
			/**
			 * getPatients - Receptionist viewpoint may request a list with all the patients
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getPatients")) {
				ArrayList<Patient> rec = database.getPatients();
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getAppointment - Receptionist viewpoint may request a specific appointment's
			 * details
			 * 
			 * Parameters: appointment ID
			 */
			else if (incoming.getFunction().equals("getAppointment")) {
				int appid = Integer.parseInt(incoming.getArguments().get(0));
				Appointment rec = database.getAppointment(appid);
				try {
					output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatientsSearch - Receptionist viewpoint may search for a patient given a
			 * specific name
			 * 
			 * Parameters: name
			 */
			else if (incoming.getFunction().equals("getPatientsSearch")) {
				String name;
				name = incoming.getArguments().get(0);
				ArrayList<Patient> rec = database.search(name);
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatients - Receptionist viewpoint may request to check if a patient is
			 * alive
			 * 
			 * Parameters: patient ID
			 */
			else if (incoming.getFunction().equals("CheckIfAlive")) {
				int appid = Integer.parseInt(incoming.getArguments().get(0));
				boolean rec = database.CheckIfAlive(appid);
				try {
					output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatients - Receptionist viewpoint may request to make a new appointment
			 * 
			 * Parameters: doctor ID, patient ID, clinic ID, date, time, drop in?,
			 * receptionist ID, attended?
			 */
			else if (incoming.getFunction().equals("newAppointment")) {
				int doc_id = Integer.parseInt(incoming.getArguments().get(0));
				int pat_id = Integer.parseInt(incoming.getArguments().get(1));
				int clinic_id = Integer.parseInt(incoming.getArguments().get(2));
				String date = incoming.getArguments().get(3);
				String time = incoming.getArguments().get(4);
				int drop_in = Integer.parseInt(incoming.getArguments().get(5));
				int receptionist_id = Integer.parseInt(incoming.getArguments().get(6));
				int att = Integer.parseInt(incoming.getArguments().get(7));
				boolean flag = database.insertAppointment(doc_id, pat_id, clinic_id, date, time, drop_in,
						receptionist_id, att);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			}
			/**
			 * getPatients - Receptionist viewpoint may request to get the ID of the last
			 * appointment in the table of Appointments
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getlastID")) {
				int rec = database.getLastAppointmentID();
				try {
					output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatients - Receptionist viewpoint may request a list with the doctors of a
			 * specific clinic
			 * 
			 * Parameters: clinic ID
			 */
			else if (incoming.getFunction().equals("getDoctors")) {
				int c_id = Integer.parseInt(incoming.getArguments().get(0));
				ArrayList<Doctor> rec = database.getDoctorsOfAClinic(c_id);
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatients - Receptionist viewpoint may request a list with the appointments
			 * of a specific clinic
			 * 
			 * Parameters: clinic ID
			 */
			else if (incoming.getFunction().equals("getAppointments")) {
				int clinic;
				clinic = Integer.parseInt(incoming.getArguments().get(0));
				ArrayList<Appointment> rec = database.getAppointments(clinic);
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatients - Receptionist viewpoint may request to update the attendance of
			 * a specific appointment
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("updateApp")) {
				int att = Integer.parseInt(incoming.getArguments().get(1));
				int id = Integer.parseInt(incoming.getArguments().get(0));
				boolean flag = database.updateAppointment(id, att);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			}
			/**
			 * getPatients - Receptionist viewpoint may request to generate a new treatment
			 * 
			 * Parameters: Treatment object instance
			 */
			else if (incoming.getFunction().equals("addTreatment")) {
				Treatment tr = new Gson().fromJson(incoming.getArguments().get(0), Treatment.class);
				int row = database.insertTreatment(tr);
				output.writeBytes(new Gson().toJson(row) + System.lineSeparator());
			}
			/**
			 * getPatients - Receptionist viewpoint may request a list with the last
			 * treatment given to a specific patient
			 * 
			 * Parameters: Patient ID
			 */
			else if (incoming.getFunction().equals("showAllTreatments")) {
				int att = Integer.parseInt(incoming.getArguments().get(0));
				Treatment row = database.getgenTreat(att);
				try {
					output.writeBytes(new Gson().toJson(row) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (incoming.getFunction().equals("getPatientByID")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				Patient rec = database.getPatientByID(id);
				output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
			} else if (incoming.getFunction().equals("insertNewPatient")) {
				Patient tr = new Gson().fromJson(incoming.getArguments().get(0), Patient.class);
				boolean suc = database.insertNewPatient(tr);
				output.writeBytes(new Gson().toJson(suc) + System.lineSeparator());

			} else if (incoming.getFunction().equals("getPatientsLatestAppointment")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				Appointment app = database.getPatientsLatestAppointment(id);
				output.writeBytes(new Gson().toJson(app) + System.lineSeparator());

			}

		}

		/**
		 * This method is used to handle requests from the Clinical Viewpoint.
		 * 
		 * @param incoming Actual Query object
		 * @param output   Output Stream
		 */
		private void HandleClinical(Query incoming, DataOutputStream output) throws IOException {
			/**
			 * login - Clinical viewpoint may request a doctors login
			 * 
			 * Parameters: username and password
			 */
			if (incoming.getFunction().equals("login")) {
				Doctor doc = database.loginClinical(incoming.getArguments().get(0), incoming.getArguments().get(1));
				if (doc == null) {
					doc = new Doctor();
					doc.emptyValue();
				}
				output.writeBytes(new Gson().toJson(doc) + System.lineSeparator());
			}
			/**
			 * getDrugs - Clinical Viewpoint may request a list with all the drugs in the
			 * database.
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getDrugs")) {
				ArrayList<Drug> rec = database.getDrugList();
				try {
					output.writeBytes(new Gson().toJson(rec.size()) + System.lineSeparator());
					for (int i = 0; i < rec.size(); i++)
						output.writeBytes(new Gson().toJson(rec.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getConditions - Clinical Viewpoint may request a list with all the conditions
			 * in the database.
			 * 
			 * Parameters: no parameters
			 */
			else if (incoming.getFunction().equals("getConditions")) {
				ArrayList<Condition> cond = database.getConditions();
				try {
					output.writeBytes(new Gson().toJson(cond.size()) + System.lineSeparator());
					for (int i = 0; i < cond.size(); i++)
						output.writeBytes(new Gson().toJson(cond.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getDoctorsPatient - Clinical Viewpoint may request a list with all the
			 * patients of a doctor.
			 * 
			 * Parameters: Doctor's ID
			 */
			else if (incoming.getFunction().equals("getDoctorsPatient")) {
				int doc_id = Integer.parseInt(incoming.getArguments().get(0));
				ArrayList<Patient> patient_list = database.getDoctorsPatient(doc_id);
				try {
					output.writeBytes(new Gson().toJson(patient_list.size()) + System.lineSeparator());
					for (int i = 0; i < patient_list.size(); i++)
						output.writeBytes(new Gson().toJson(patient_list.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatientComments - Clinical Viewpoint may request a list with all the
			 * comments made for a patient.
			 * 
			 * Parameters: Patient's ID
			 */
			else if (incoming.getFunction().equals("getPatientComments")) {
				int pat_id = Integer.parseInt(incoming.getArguments().get(0));
				ArrayList<Comment> comment_list = database.getComments(pat_id);
				try {
					output.writeBytes(new Gson().toJson(comment_list.size()) + System.lineSeparator());
					for (int i = 0; i < comment_list.size(); i++)
						output.writeBytes(new Gson().toJson(comment_list.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatientRecords - Clinical Viewpoint may request a list with all the
			 * records made for a patient by a specific doctor.
			 * 
			 * Parameters: Patient's ID and Doctor's ID
			 */
			else if (incoming.getFunction().equals("getPatientRecords")) {
				int pat_id = Integer.parseInt(incoming.getArguments().get(0));
				int doc_id = Integer.parseInt(incoming.getArguments().get(1));
				ArrayList<PatientRecord> record_list = database.getPatientRecords(pat_id, doc_id);
				try {
					output.writeBytes(new Gson().toJson(record_list.size()) + System.lineSeparator());
					for (int i = 0; i < record_list.size(); i++)
						output.writeBytes(new Gson().toJson(record_list.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getPatientAllergies - Clinical Viewpoint may request a list with all the
			 * allergies of a patient.
			 * 
			 * Parameters: Patient's ID
			 */
			else if (incoming.getFunction().equals("getPatientAllergies")) {
				int pat_id = Integer.parseInt(incoming.getArguments().get(0));
				ArrayList<Allergy> allergy_list = database.getPatientAllergies(pat_id);
				try {
					output.writeBytes(new Gson().toJson(allergy_list.size()) + System.lineSeparator());
					for (int i = 0; i < allergy_list.size(); i++)
						output.writeBytes(new Gson().toJson(allergy_list.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * addComment - Clinical Viewpoint may request the addition of a comment for a
			 * patient.
			 * 
			 * Parameters: Doctor's ID, Patient's ID and comment body
			 */
			else if (incoming.getFunction().equals("addComment")) {
				int doc_id = Integer.parseInt(incoming.getArguments().get(0));
				int pat_id = Integer.parseInt(incoming.getArguments().get(1));
				String com = incoming.getArguments().get(2);
				boolean flag = database.insertComment(doc_id, pat_id, com);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			}
			/**
			 * addTreatment - Clinical Viewpoint may request the addition of a treatment for
			 * a patient.
			 * 
			 * Parameters: Treatment
			 */
			else if (incoming.getFunction().equals("addTreatment")) {
				Treatment tr = new Gson().fromJson(incoming.getArguments().get(0), Treatment.class);
				int row = database.insertTreatment(tr);
				output.writeBytes(new Gson().toJson(row) + System.lineSeparator());
			}
			/**
			 * addRecord - Clinical Viewpoint may request the addition of a record for a
			 * patient.
			 * 
			 * Parameters: Record
			 */
			else if (incoming.getFunction().equals("addRecord")) {
				PatientRecord re = new Gson().fromJson(incoming.getArguments().get(0), PatientRecord.class);
				int appointment_id = Integer.parseInt(incoming.getArguments().get(1));
				boolean flag = database.insertRecord(re);
				int id = database.getIDofLastRecord();
				database.insertRecordAppointmentRelationship(id, appointment_id);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());

			}
			/**
			 * 
			 */
			else if (incoming.getFunction().equals("getRecordOfAnAppointment")) {
				int appointment_id = Integer.parseInt(incoming.getArguments().get(0));
				int reid = database.getRecordOfAnAppointment(appointment_id);
				output.writeBytes(reid + System.lineSeparator());
			}
			/**
			 * requestDeath - Clinical Viewpoint may request the addition of a death request
			 * of a patient.
			 * 
			 * Parameters: Patient's ID and Doctor's ID
			 */
			else if (incoming.getFunction().equals("requestDeath")) {
				int pat = new Gson().fromJson(incoming.getArguments().get(0), Integer.class);
				int doc = new Gson().fromJson(incoming.getArguments().get(1), Integer.class);
				String date = new Gson().fromJson(incoming.getArguments().get(2), String.class);
				boolean flag = database.insertPendingDeath(pat, doc, date);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			}
			/**
			 * insertAllergy - Clinical Viewpoint may request the addition of an allergy
			 * request for a patient.
			 * 
			 * Parameters: Allergy
			 */
			else if (incoming.getFunction().equals("insertAllergy")) {
				Allergy all = new Gson().fromJson(incoming.getArguments().get(0), Allergy.class);
				boolean flag = database.insertAllergy(all);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			}
			/**
			 * getAppointmentsOfDoctorAndPatient - Clinical Viewpoint may request the
			 * appointment of a patient with a doctor.
			 * 
			 * Parameters: Doctor's ID and patient's ID
			 */
			else if (incoming.getFunction().equals("getAppointmentsOfDoctorAndPatient")) {
				int doc = new Gson().fromJson(incoming.getArguments().get(0), Integer.class);
				int pat = new Gson().fromJson(incoming.getArguments().get(1), Integer.class);
				ArrayList<Appointment> app_list = database.getPatientAppointmentsWithDoctor(doc, pat);
				try {
					output.writeBytes(new Gson().toJson(app_list.size()) + System.lineSeparator());
					for (int i = 0; i < app_list.size(); i++)
						output.writeBytes(new Gson().toJson(app_list.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * getAppointmentsOfADoctorsDay - Clinical Viewpoint may request the list of
			 * appointments of a doctor, for a day.
			 * 
			 * Parameters: Doctor's ID and patient's ID
			 */
			else if (incoming.getFunction().equals("getAppointmentsOfADoctorsDay")) {
				int doc = new Gson().fromJson(incoming.getArguments().get(0), Integer.class);
				String day = new Gson().fromJson(incoming.getArguments().get(1), String.class);
				ArrayList<Appointment> app_list = database.getDoctorsAppointments(doc, day);
				try {
					output.writeBytes(new Gson().toJson(app_list.size()) + System.lineSeparator());
					for (int i = 0; i < app_list.size(); i++)
						output.writeBytes(new Gson().toJson(app_list.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * updateTreatment - Clinical viewpoint may request the update of an existing
			 * treatment.
			 * 
			 * Parameters: Treatment object
			 */
			else if (incoming.getFunction().equals("updateTreatment")) {
				Treatment tr = new Gson().fromJson(incoming.getArguments().get(0), Treatment.class);
				database.updateTreatment(tr);
			}
			/**
			 * updateRecord - Clinical viewpoint may request the update of an existing
			 * record.
			 * 
			 * Parameters: PatientRecord object
			 */
			else if (incoming.getFunction().equals("updateRecord")) {
				PatientRecord re = new Gson().fromJson(incoming.getArguments().get(0), PatientRecord.class);
				database.updateRecord(re);
			}
			/**
			 * 
			 */
			else if (incoming.getFunction().equals("getPatientByID")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				Patient rec = database.getPatientByID(id);
				output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
			}
			/**
			 * If no correct function is called a message should be printed in server side.
			 */
			else {
				System.out.println(
						"---------------- Wrong function call for Clinical Viewpoint Client ---------------- ");
			}
		}

		/**
		 * This method is used to implement all the function issued by Medical Records
		 * Staff client.
		 * 
		 * @param incoming Query issued
		 * @param output   the data stream we send out
		 * @throws IOException
		 */
		public void HandleMedicalRecords(Query incoming, DataOutputStream output) throws IOException {
			if (incoming.getFunction().equals("login")) {
				RecordsStaff rec = database.loginMedicalRecords(incoming.getArguments().get(0),
						incoming.getArguments().get(1));
				if (rec == null) {
					rec = new RecordsStaff();
					rec.emptyValue();
				}
				output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
			} else if (incoming.getFunction().equals("getClinics")) {
				ArrayList<Clinic> clinics = database.getClinics();
				try {
					output.writeBytes(new Gson().toJson(clinics.size()) + System.lineSeparator());
					for (int i = 0; i < clinics.size(); i++)
						output.writeBytes(new Gson().toJson(clinics.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (incoming.getFunction().equals("getRequests")) {
				ArrayList<Request> reqs = database.getRequests();
				try {
					output.writeBytes(new Gson().toJson(reqs.size()) + System.lineSeparator());
					for (int i = 0; i < reqs.size(); i++)
						output.writeBytes(new Gson().toJson(reqs.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (incoming.getFunction().equals("getAllergy")) {
				int al_id = Integer.parseInt(incoming.getArguments().get(0));
				Allergy allergy = database.getAllergy(al_id);
				try {
					output.writeBytes(new Gson().toJson(allergy) + System.lineSeparator());

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (incoming.getFunction().equals("changeTreatAccept")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				boolean flag = database.changeTreatAccept(id);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			} else if (incoming.getFunction().equals("changeAllAccept")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				boolean flag = database.changeAllAccept(id);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			} else if (incoming.getFunction().equals("changeRecAccept")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				boolean flag = database.changeRecAccept(id);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			} else if (incoming.getFunction().equals("changeDeath")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				boolean flag = database.changeDeath(id);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			} else if (incoming.getFunction().equals("getPatients")) {
				ArrayList<Patient> reqs = database.getPatients();
				try {
					output.writeBytes(new Gson().toJson(reqs.size()) + System.lineSeparator());
					for (int i = 0; i < reqs.size(); i++)
						output.writeBytes(new Gson().toJson(reqs.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (incoming.getFunction().equals("getDoctors")) {
				ArrayList<Doctor> reqs = database.getDoctors();
				try {
					output.writeBytes(new Gson().toJson(reqs.size()) + System.lineSeparator());
					for (int i = 0; i < reqs.size(); i++)
						output.writeBytes(new Gson().toJson(reqs.get(i)) + System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (incoming.getFunction().equals("insertDoctorPatientRelationship")) {
				int doc_id = Integer.parseInt(incoming.getArguments().get(1));
				int p_id = Integer.parseInt(incoming.getArguments().get(0));
				boolean flag = database.insertDoctorPatientRelationship(p_id, doc_id);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
			} else if (incoming.getFunction().equals("getPatientByID")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				Patient rec = database.getPatientByID(id);
				output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
			} else if (incoming.getFunction().equals("getRecordByID")) {
				int id = Integer.parseInt(incoming.getArguments().get(0));
				PatientRecord rec = database.getRecordByID(id);
				output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
			}

		}
	}

	/**
	 * Here we define the thread capacity of our server.
	 */
	public static ExecutorService TCP_WORKER_SERVICE = Executors.newFixedThreadPool(10);

	/**
	 * This is the main start point of the server program.
	 * 
	 * @param args No arguments needed
	 */
	public static void main(String args[]) {
		try {
			FileResourcesUtils app = new FileResourcesUtils();

			@SuppressWarnings("resource")
			ServerSocket socket = new ServerSocket(app.getConfig("serverConf.json").getPort());

			System.out.println("Server listening to: " + socket.getInetAddress() + ":" + socket.getLocalPort());

			while (true) {
				Socket client = socket.accept();

				TCP_WORKER_SERVICE.submit(new TCPWorker(client));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
