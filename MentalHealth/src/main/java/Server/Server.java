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
import Objects.RecordsStaff;
import Objects.ReportData;
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

		private Socket client;
		private String clientbuffer;
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
			 * login - Clinical viewpoint may request a doctors login
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
			 * FIX getDrugs - Clinical Viewpoint may request a list with all the drugs in
			 * the database.
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
			 * FIX getDrugs - Clinical Viewpoint may request a list with all the drugs in
			 * the database.
			 * 
			 * Parameters: no parameters
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
			// TODO:
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
				boolean flag = database.insertRecord(re);
				if (flag)
					output.writeBytes("SUCCESS" + System.lineSeparator());
				else
					output.writeBytes("FAILURE" + System.lineSeparator());
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
