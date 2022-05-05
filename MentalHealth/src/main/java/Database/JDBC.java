package Database;

import java.sql.*;
import java.util.ArrayList;
import Objects.*;
import Tools.Clock;
import Tools.Counter;
import Tools.RequestType;
import Tools.Viewpoint;

/**
 * This object is the middleware between the server and the SQL Database. Each
 * method of this object should call a procedure which is defined in the actual
 * DB, and also return an object or a list of objects whom handle the returned
 * data.
 * 
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 *
 */
public class JDBC {
	/**
	 * Each JDBC connection must have a database driver loader.
	 */
	private boolean dbDriverLoaded = false;
	/**
	 * Each JDBC connection must have an actual connection object.
	 */
	private Connection conn = null;

	/**
	 * Empty constructor. Halt on failure.
	 */
	public JDBC() {
		getDBConnection();
		if (conn == null) {
			System.out.println("Cannot connect to Database!");
			System.exit(-1);
		}
	}

	/**
	 * A method that returns a connection to MS SQL server DB
	 *
	 * @return The connection object to be used. mbofos01 Jh2uvUbz
	 * 
	 */
	private Connection getDBConnection() {
		String dbConnString = "jdbc:sqlserver://mssql.cs.ucy.ac.cy:1433;databaseName=team4;user=team4;password=banmb65B;";

		if (!dbDriverLoaded)
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				dbDriverLoaded = true;
			} catch (ClassNotFoundException e) {
				System.out.println("Cannot load DB driver!");
				return null;
			}

		try {
			if (conn == null)
				conn = DriverManager.getConnection(dbConnString);
			else if (conn.isClosed())
				conn = DriverManager.getConnection(dbConnString);
		} catch (SQLException e) {
			System.out.print("Cannot connect to the DB!\nGot error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return conn;
	}

	/**
	 * This function is used to login a Medical Records Staff Person
	 * 
	 * @param username String the username we enter
	 * @param password String the password we enter
	 * @return RecordsStaff
	 */
	public Doctor loginClinical(String username, String password) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call loginClinicalStaff(?,?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			ResultSet rs = cs.executeQuery();
			Doctor rec = new Doctor();
			int right = 0;

			while (rs.next()) {

				rec.setId(rs.getInt("doctor_id"));
				rec.setName(rs.getString("name"));
				rec.setSurname(rs.getString("surname"));
				rec.setUsername(rs.getString("username"));
				rec.setClinic_id(rs.getInt("clinic_id"));
				right++;
			}
			if (right == 0) {
				rec.emptyValue();
				return rec;
			}
			cs = this.conn.prepareCall("{call getClinicName(?)}");
			cs.setInt(1, rec.getClinic_id());
			rs = cs.executeQuery();
			Clinic cli = new Clinic();
			while (rs.next()) {
				cli.setClinic_id(rs.getInt("clinic_id"));
				cli.setName(rs.getString("name"));
				cli.setTelephone(rs.getString("telephone"));
			}
			rec.setClinic(cli);
			return rec;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Doctor rec = new Doctor();
		rec.emptyValue();
		return rec;

	}

	/**
	 * This function is used to login a Health Service Staff Person
	 * 
	 * @param username String the username we enter
	 * @param password String the password we enter
	 * @return Health Service staff instance
	 */
	public HealthServ loginHealthService(String username, String password) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call loginHealthService(?,?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			ResultSet rs = cs.executeQuery();
			HealthServ rec = new HealthServ();
			int right = 0;

			while (rs.next()) {
				rec.setId(rs.getInt("hs_id"));
				rec.setName(rs.getString("name"));
				rec.setSurname(rs.getString("surname"));
				rec.setUsername(rs.getString("username"));
				rec.setEmail(rs.getString("email"));
				right++;

			}
			if (right == 0)
				rec.emptyValue();

			return rec;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		HealthServ rec = new HealthServ();
		rec.emptyValue();
		return rec;
	}

	/**
	 * This function is used to login a Receptionist
	 * 
	 * @param username String the username we enter
	 * @param password String the password we enter
	 * @return Receptionist instance
	 */
	public ReceptionistObj loginReceptionist(String username, String password) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call loginReceptionist(?,?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			ResultSet rs = cs.executeQuery();
			ReceptionistObj rec = new ReceptionistObj();
			int right = 0;

			while (rs.next()) {
				rec.setId(rs.getInt("id"));
				rec.setName(rs.getString("name"));
				rec.setSurname(rs.getString("surname"));
				rec.setUsername(rs.getString("username"));
				rec.setEmail(rs.getString("email"));
				rec.setClinic_id(rs.getInt("clinic_id"));
				right++;

			}
			if (right == 0)
				rec.emptyValue();

			return rec;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		ReceptionistObj rec = new ReceptionistObj();
		rec.emptyValue();
		return rec;
	}

	/**
	 * This method fetches the data of a clinic based on an id.
	 * 
	 * @param clinic_id Integer clinics id
	 * @return Clinic object
	 */
	public Clinic getClinic(int clinic_id) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getClinicName(?)}");
			cs.setInt(1, clinic_id);
			ResultSet rs = cs.executeQuery();

			Clinic cli = new Clinic();
			while (rs.next()) {
				cli.setClinic_id(rs.getInt("clinic_id"));
				cli.setName(rs.getString("name"));
				cli.setTelephone(rs.getString("telephone"));
			}

			return cli;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function is used to login a Medical Records Staff Person
	 * 
	 * @param username String the username we enter
	 * @param password String the password we enter
	 * @return RecordsStaff
	 */
	public RecordsStaff loginMedicalRecords(String username, String password) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call loginMedicalRecords(?,?)}");
			cs.setString(1, username);
			cs.setString(2, password);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				RecordsStaff rec = new RecordsStaff();
				rec.setId(rs.getInt("records_id"));
				rec.setName(rs.getString("name"));
				rec.setSurname(rs.getString("surname"));
				rec.setUsername(rs.getString("username"));
				rec.setEmail(rs.getString("email"));
				return rec;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RecordsStaff rec = new RecordsStaff();
		rec.emptyValue();
		return rec;

	}

	/**
	 * This method fetches all the drugs in an arraylist
	 * 
	 * @return ArrayList of drug objects
	 */
	public ArrayList<Drug> getDrugList() {
		ArrayList<Drug> all_drugs = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getAllDrugs()}");
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Drug rec = new Drug();
				rec.setId(rs.getInt("drug_id"));
				rec.setName(rs.getString("name"));
				rec.setCommercial_name(rs.getString("commercial_name"));
				rec.setSide_effect(rs.getString("side_effect"));
				all_drugs.add(rec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return all_drugs;

	}

	/**
	 * This method fetches a drug based on its id, this is going to be used after a
	 * specific drug has been selected on a list.
	 * 
	 * @param id Integer drug id
	 * @return Drug in object
	 */
	public Drug getDrug(int id) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getDrug(?)}");
			cs.setInt(1, id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Drug rec = new Drug();
				rec.setId(rs.getInt("drug_id"));
				rec.setName(rs.getString("name"));
				rec.setCommercial_name(rs.getString("commercial_name"));
				rec.setSide_effect(rs.getString("side_effect"));
				return rec;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Drug rec = new Drug();
		rec.setId(-1);
		return rec;
	}

	public Allergy getAllergy(int id) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getAllergy(?)}");
			cs.setInt(1, id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Allergy rec = new Allergy();
				rec.setAllergy_id(rs.getInt("allergy_id"));
				rec.setPatient_id(rs.getInt("patient_id"));
				rec.setDrug_id(rs.getInt("drug_id"));
				rec.setAccepted(rs.getBoolean("accepted"));
				rec.setDate(rs.getString("date"));
				rec.setLast_updated(rs.getString("last_updated"));
				return rec;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Allergy rec = new Allergy();
		rec.setAllergy_id(-1);
		System.out.println("rec");
		return rec;
	}

	/**
	 * This method fetched a patient based on their id.
	 * 
	 * @param patient_id Integer patients id
	 * @return Patient in object
	 */
	public Patient getPatient(int patient_id) {
		Patient patient = new Patient();
		int flag = 0;
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getPatient(?)}");
			cs.setInt(1, patient_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				patient.setPatient_id(rs.getInt("patient_id"));
				patient.setDate(rs.getString("birthday"));
				patient.setEmail(rs.getString("email"));
				patient.setTelephone(rs.getString("telephone"));
				patient.setName(rs.getString("name"));
				patient.setSurname(rs.getString("surname"));
				patient.setAlive(rs.getBoolean("alive"));
				flag++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == 0)
			patient.setPatient_id(-1);
		return patient;
	}

	/**
	 * This method fetches all the patients of a specific doctor.
	 * 
	 * @param doctor_id Integer id of a doctor
	 * @return An ArrayList of Patients
	 */
	public ArrayList<Patient> getDoctorsPatient(int doctor_id) {
		ArrayList<Patient> patient_list = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getDoctorsPatient(?)}");
			cs.setInt(1, doctor_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				patient_list.add(getPatient(rs.getInt("patient_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return patient_list;

	}

	/**
	 * This method inserts a comment in the database.
	 * 
	 * @param doctor_id  Intger id of a doctor
	 * @param patient_id Integer id of a patient
	 * @param comment    String comment body
	 * @return true if successful otherwise false
	 */
	public boolean insertComment(int doctor_id, int patient_id, String comment) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call insertComment(?,?,?,?)}");
			cs.setInt(1, doctor_id);
			cs.setInt(2, patient_id);
			cs.setString(3, comment);
			cs.setString(4, Tools.Clock.currentSQLTime());
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertDoctorPatientRelationship(int patient_id, int doctor_id) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call insertDoctorPatientRelationship(?,?)}");
			cs.setInt(1, patient_id);
			cs.setInt(2, doctor_id);
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeAllAccept(int recId) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call changeAllAccept(?)}");
			cs.setInt(1, recId);
			cs.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeRecAccept(int recId) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call changeRecAccept(?)}");
			cs.setInt(1, recId);
			cs.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeDeath(int recId) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call changeDeath(?)}");
			cs.setInt(1, recId);
			cs.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeTreatAccept(int recId) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call changeTreatAccept(?)}");
			cs.setInt(1, recId);
			cs.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method gets all comments created for a specific patient.
	 * 
	 * @param patient_id Integer id of a patient
	 * @return An ArrayList of comments
	 */
	public ArrayList<Comment> getComments(int patient_id) {
		ArrayList<Comment> comment_list = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getPatientComments(?)}");
			cs.setInt(1, patient_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Comment c = new Comment();
				c.setComment(rs.getString("comment"));
				c.setPatient_id(rs.getInt("patient_id"));
				c.setDoctor_id(rs.getInt("doctor_id"));
				c.setComment_id(rs.getInt("comment_id"));
				c.setDate(rs.getString("date"));
				PreparedStatement cs2 = this.conn.prepareCall("{call getDoctor(?)}");
				cs2.setInt(1, c.getDoctor_id());
				ResultSet rs2 = cs2.executeQuery();
				while (rs2.next()) {
					c.setDoctor_name(rs2.getString("name"));
					c.setDoctor_surname(rs2.getString("surname"));
				}

				comment_list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comment_list;
	}

	/**
	 * This method fetches all the conditions all the database.
	 * 
	 * @return An ArrayList of conditions
	 */
	public ArrayList<Condition> getConditions() {
		ArrayList<Condition> condition_list = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getAllConditions()}");
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Condition c = new Condition();
				c.setCondition_id(rs.getInt("condition_id"));
				c.setName(rs.getString("name"));

				condition_list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return condition_list;
	}

	/**
	 * This method fetches all the records of a specific patient.
	 * 
	 * @param patient_id Integer id of a patient
	 * @param doctor_id  Integer id of a doctor
	 * @return An ArrayList of Records
	 */
	public ArrayList<PatientRecord> getPatientRecords(int patient_id, int doctor_id) {
		ArrayList<PatientRecord> records = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getPatientRecords(?,?)}");
			cs.setInt(1, patient_id);
			cs.setInt(2, doctor_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				PatientRecord c = new PatientRecord();
				c.setRecord_id(rs.getInt("record_id"));
				c.setDoctor_id(rs.getInt("doctor_id"));
				c.setDate(rs.getString("date"));
				boolean flag;
				if (rs.getInt("selfharm") == 0)
					flag = false;
				else
					flag = true;
				c.setSelf_harm(flag);

				if (rs.getInt("overdose") == 0)
					c.setOverdose(false);
				else if (rs.getInt("overdose") == 1)
					c.setOverdose(true);

				if (rs.getInt("threat") == 0)
					c.setThreat(false);
				else if (rs.getInt("threat") == 1)
					c.setThreat(true);

				if (rs.getInt("underdose") == 0)
					c.setUnderdose(false);
				else if (rs.getInt("underdose") == 1)
					c.setUnderdose(true);

				c.setCondition_id(rs.getInt("condition_id"));
				c.setLast_update(rs.getString("last_update"));
				if (rs.getInt("treatment_id") == 0)
					c.setTreatment_id(-1);
				else
					c.setTreatment_id(rs.getInt("treatment_id"));
				c.setPatient_id(rs.getInt("patient_id"));

				if (rs.getInt("accepted") == 0)
					flag = false;
				else
					flag = true;
				c.setAccepted(flag);

				if (c.getTreatment_id() != -1) {
					PreparedStatement treat = this.conn.prepareCall("{call getTreatment(?)}");
					treat.setInt(1, c.getTreatment_id());
					ResultSet treat_rs = treat.executeQuery();
					Treatment t = new Treatment();
					while (treat_rs.next()) {
						if (treat_rs.getInt("accepted") == 0)
							flag = false;
						else
							flag = true;
						t.setAccepted(flag);
						t.setComments(treat_rs.getString("comments"));
						t.setDoctor_id(treat_rs.getInt("doctor_id"));
						t.setDose(treat_rs.getInt("dose"));
						t.setDrug_id(treat_rs.getInt("drug_id"));
						t.setPatient_id(treat_rs.getInt("patient_id"));
						t.setDate(treat_rs.getString("date"));
						t.setLast_updated(treat_rs.getString("last_update"));
						if (treat_rs.getInt("warning") == 0)
							flag = false;
						else
							flag = true;
						t.setWarning(flag);
						t.setTreatment_id(treat_rs.getInt("treatment_id"));

					}
					c.setTreatment(t);
				}
				records.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return records;

	}

	/**
	 * This method fetches all the allergies of a specific patient.
	 * 
	 * @param patient_id Integer id of a patient
	 * @return An ArrayList of allergies
	 */
	public ArrayList<Allergy> getPatientAllergies(int patient_id) {
		ArrayList<Allergy> allergies = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getPatientAllegies(?)}");
			cs.setInt(1, patient_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Allergy c = new Allergy();
				c.setAllergy_id(rs.getInt("allergy_id"));
				c.setDrug_id(rs.getInt("drug_id"));
				c.setPatient_id(rs.getInt("patient_id"));
				c.setAccepted(rs.getBoolean("accepted"));
				c.setDate(rs.getString("date"));
				c.setLast_updated(rs.getString("last_updated"));
				if (c.isAccepted())
					allergies.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allergies;
	}

	/**
	 * This method inserts a treatment to the database.
	 * 
	 * @param treat Treatment object
	 * @return the id of the new treatment
	 */
	public int insertTreatment(Treatment treat) {
		try {
			CallableStatement cs = this.conn.prepareCall("{ ? = call  insertTreatment(?,?,?,?,?,?,?,?)}");

			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setInt(2, treat.getPatient_id());
			cs.setInt(3, treat.getDoctor_id());
			cs.setInt(4, treat.getDose());
			cs.setString(5, treat.getComments());
			cs.setBoolean(6, treat.isWarning());
			cs.setInt(7, treat.getDrug_id());
			cs.setBoolean(8, treat.isAccepted());
			cs.setString(9, treat.getDate());
			cs.execute();
			System.out.println(cs.getInt(1));
			return cs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * This method inserts a record to the database.
	 * 
	 * @param re PatientRecord object
	 * @return true if successful otherwise false
	 */
	public boolean insertRecord(PatientRecord re) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call insertRecord(?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, re.getDoctor_id());
			cs.setString(2, re.getDate());
			cs.setBoolean(3, re.isSelf_harm());
			cs.setBoolean(4, re.isThreat());
			cs.setInt(5, re.getCondition_id());
			cs.setString(6, re.getDate());
			if (re.getTreatment_id() != -1)
				cs.setInt(7, re.getTreatment_id());
			else
				cs.setNull(7, 0);
			cs.setInt(8, re.getPatient_id());
			cs.setBoolean(9, re.isAccepted());
			cs.setBoolean(10, re.isOverdose());
			cs.setBoolean(11, re.isUnderdose());
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method inserts a death of a patient request.
	 * 
	 * @param patient     Patient object
	 * @param doctor      Integer doctor id
	 * @param dateOfDeath String SQL date
	 * @return true if successful otherwise false
	 */
	public boolean insertPendingDeath(int patient, int doctor, String dateOfDeath) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call requestDeathOfPatient(?,?,?)}");
			cs.setInt(1, patient);
			cs.setInt(2, doctor);
			cs.setString(3, dateOfDeath);

			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method inserts an allergy to the database.
	 * 
	 * @param allergy Allergy object
	 * @return true if successful otherwise false
	 */
	public boolean insertAllergy(Allergy allergy) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call insertAllergy(?,?,?,?,?)}");
			cs.setInt(1, allergy.getPatient_id());
			cs.setInt(2, allergy.getDrug_id());
			cs.setBoolean(3, allergy.isAccepted());
			cs.setString(4, allergy.getDate());
			cs.setString(5, allergy.getLast_updated());

			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * This method fetches all the doctors of a specific clinic.
	 * 
	 * @param clinic_id Integer id of a clinic
	 * @return An ArrayList of doctors
	 */
	public ArrayList<Doctor> getDoctorsOfAClinic(int clinic_id) {
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getDoctorsOfAClinic(?)}");
			cs.setInt(1, clinic_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Doctor rec = new Doctor();
				rec.setId(rs.getInt("doctor_id"));
				rec.setName(rs.getString("name"));
				rec.setSurname(rs.getString("surname"));
				rec.setUsername(rs.getString("username"));
				rec.setClinic_id(rs.getInt("clinic_id"));
				doctors.add(rec);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctors;

	}

	/**
	 * This method calculates how many patients have each condition in a specific
	 * clinic.
	 * 
	 * @param clinic_id Integer id of a clinic
	 * @return An ArrayList of counters
	 */
	public ArrayList<Counter> getPatientsOfEachCondition(int clinic_id) {
		ArrayList<Counter> counters = new ArrayList<Counter>();
		ArrayList<Counter> merged = new ArrayList<>();
		ArrayList<Doctor> doctors_of_a_clinic = this.getDoctorsOfAClinic(clinic_id);
		String week[] = Clock.getLastWeek();
		for (String week_day : week)
			System.out.println(week_day);
		try {
			for (String day : week) {
				ArrayList<Condition> conds = this.getConditions();
				for (int i = 0; i < conds.size(); i++) {
					Condition one = conds.get(i);
					PreparedStatement cs = this.conn.prepareCall("{call getNumberOfCondition(?,?,?)}");
					cs.setInt(1, one.getCondition_id());
					cs.setBoolean(2, true);
					cs.setString(3, day);
					ResultSet rs = cs.executeQuery();
					ArrayList<PatientRecord> recs = new ArrayList<>();
					while (rs.next()) {
						PatientRecord in = new PatientRecord();
						in.setPatient_id(rs.getInt("patient_id"));
						in.setDoctor_id(rs.getInt("doctor_id"));
						in.setDate(rs.getString("date"));
						if (!idDoesExist(recs, in) && clinicsDoctor(doctors_of_a_clinic, in.getDoctor_id()))
							recs.add(in);
					}
					counters.add(new Counter(one.getName(), recs.size()));
				}
			}
			ArrayList<Condition> conds = this.getConditions();
			for (int c = 0; c < conds.size(); c++) {
				Counter inse = new Counter(conds.get(c).getName(), 0);
				for (Counter i : counters) {
					if (i.getName().equals(conds.get(c).getName())) {
						inse.addValue(i.getValue());
					}
				}
				merged.add(inse);

			}
			for (Counter m : merged)
				System.out.println(m.getName() + " " + m.getValue());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return merged;
	}

	/**
	 * This method calculates how many patients are prescribed each drug in a
	 * specific clinic.
	 * 
	 * @param clinic_id Integer id of a clinic
	 * @return An ArrayList of counters
	 */
	public ArrayList<Counter> getPatientsOfEachTreatment(int clinic_id) {
		ArrayList<Counter> counters = new ArrayList<Counter>();
		ArrayList<Counter> merged = new ArrayList<>();
		ArrayList<Doctor> doctors_of_a_clinic = this.getDoctorsOfAClinic(clinic_id);
		String week[] = Clock.getLastWeek();
		for (String week_day : week)
			System.out.println(week_day);
		try {
			for (String day : week) {
				ArrayList<Drug> drugs = this.getDrugList();
				for (int i = 0; i < drugs.size(); i++) {
					Drug one = drugs.get(i);
					PreparedStatement cs = this.conn.prepareCall("{call getNumberOfTreatment(?,?,?)}");
					cs.setInt(1, one.getId());
					cs.setBoolean(2, true);
					cs.setString(3, day);
					ResultSet rs = cs.executeQuery();
					ArrayList<Treatment> recs = new ArrayList<>();
					while (rs.next()) {
						Treatment in = new Treatment();
						in.setPatient_id(rs.getInt("patient_id"));
						in.setDoctor_id(rs.getInt("doctor_id"));
						in.setDate(rs.getString("date"));
						if (!idDoesExist(recs, in) && clinicsDoctor(doctors_of_a_clinic, in.getDoctor_id()))
							recs.add(in);
					}
					counters.add(new Counter(one.getCommercial_name(), recs.size()));
				}
			}

			ArrayList<Drug> drugs = this.getDrugList();
			for (int c = 0; c < drugs.size(); c++) {
				Counter inse = new Counter(drugs.get(c).getCommercial_name(), 0);
				for (Counter i : counters) {
					if (i.getName().equals(drugs.get(c).getCommercial_name())) {
						inse.addValue(i.getValue());
					}
				}
				merged.add(inse);

			}
			for (Counter m : merged)
				System.out.println(m.getName() + " " + m.getValue());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return merged;
	}

	public ArrayList<Doctor> getDoctors() {

		ArrayList<Doctor> doctors = new ArrayList<>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getAllDoctors()}");

			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Doctor doc = new Doctor();
				doc.setId(rs.getInt("doctor_id"));
				doc.setUsername(rs.getString("username"));
				doc.setSurname(rs.getString("surname"));
				doc.setClinic_id(rs.getInt("clinic_id"));
				doc.setName(rs.getString("name"));

				doctors.add(doc);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doctors;
	}

	public ArrayList<Patient> getPatients() {

		ArrayList<Patient> patients = new ArrayList<Patient>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call ShowAllPatients()}");
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Patient p = new Patient();
				p.setPatient_id(rs.getInt("patient_id"));
				p.setName(rs.getString("name"));
				p.setSurname(rs.getString("surname"));
				p.setTelephone(rs.getString("telephone"));
				p.setEmail(rs.getString("email"));

				p.setAlive(rs.getBoolean("alive"));
				patients.add(p);
			}
			return patients;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Helper function checks if a doctor belongs to a list.
	 * 
	 * @param doctors_of_a_clinic An ArrayList of doctors
	 * @param in                  Integer id of a doctor
	 * @return true if the doctor is on the list, otherwise false
	 */
	private boolean clinicsDoctor(ArrayList<Doctor> doctors_of_a_clinic, int in) {
		for (Doctor doc : doctors_of_a_clinic)
			if (in == doc.getId())
				return true;
		return false;
	}

	/**
	 * Helper function checks if a patient record exists in a list of records.
	 * 
	 * @param recs An ArrayList of records
	 * @param in   PatientRecord record
	 * @return true if the doctor is on the list, otherwise false
	 */
	private boolean idDoesExist(ArrayList<PatientRecord> recs, PatientRecord in) {
		for (PatientRecord records : recs)
			if (records.getPatient_id() == in.getPatient_id())
				return true;
		return false;
	}

	/**
	 * Helper function checks if a treatment exists in a list of treatments.
	 * 
	 * @param recs An ArrayList of treatments
	 * @param in   Treatment treatment
	 * @return true if the doctor is on the list, otherwise false
	 */
	private boolean idDoesExist(ArrayList<Treatment> recs, Treatment in) {
		for (Treatment records : recs)
			if (records.getPatient_id() == in.getPatient_id())
				return true;
		return false;
	}

	/**
	 * This method fetches the appointments for a day of a doctor, based on their
	 * id.
	 * 
	 * @param doctor Doctor doctor id
	 * @param date   String the day in question
	 * @return An ArrayList of appointments
	 */
	public ArrayList<Appointment> getDoctorsAppointments(int doctor, String date) {
		ArrayList<Appointment> rendez = new ArrayList<>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getDoctorsAppointmentsForADay(?,?)}");
			cs.setInt(1, doctor);
			cs.setString(2, date);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Appointment rec = new Appointment();
				rec.setDoctor_id(rs.getInt("doctor_id"));
				rec.setAppoint_id(rs.getInt("appoint_id"));
				rec.setPatient_id(rs.getInt("patient_id"));
				rec.setDate(rs.getString("date"));
				rec.setTime(rs.getString("time"));
				rec.setDropIn(rs.getBoolean("dropIn"));
				rec.setReceptionist_id(rs.getInt("receptionist_id"));
				rec.setAttended(rs.getBoolean("attended"));

				PreparedStatement cs2 = this.conn.prepareCall("{call getAppointmentRecords(?)}");
				cs2.setInt(1, rs.getInt("appoint_id"));
				ResultSet rs2 = cs2.executeQuery();
				boolean doesItExist = false;
				while (rs2.next()) {
					rec.setRecord_id(rs2.getInt("record_id"));
					doesItExist = true;
				}
				if (doesItExist == false)
					rec.setRecord_id(-1);

				rendez.add(rec);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rendez;
	}

	public ArrayList<Request> getRequests() {
		ArrayList<Request> requests = new ArrayList<>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getDeaths()}");

			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Request rec = new Request();
				rec.setAccepted(0);
				rec.setDescr(null);
				rec.setDt(rs.getString("date"));
				rec.setType(RequestType.Death);
				rec.setView(Viewpoint.Clinical);
				rec.setId(rs.getInt("death_id"));
				rec.setInfoA(null);

				rec.setInfoR(null);
				rec.setInfoT(null);

				PreparedStatement p = this.conn.prepareCall("{call getPatient(?)}");
				p.setInt(1, rs.getInt("patient_id"));
				ResultSet r = p.executeQuery();
				ArrayList<String> list = new ArrayList<>();
				while (r.next()) {
					String name = r.getString("name");
					list.add(name);
				}

				rec.setInfoD(list);

				requests.add(rec);
			}

			PreparedStatement cs1 = this.conn.prepareCall("{call getRequestAllergies()}");

			ResultSet rs1 = cs1.executeQuery();

			while (rs1.next()) {

				Request rec = new Request();
				rec.setAccepted(0);
				rec.setDescr(null);
				rec.setDt(rs1.getString("date"));
				rec.setType(RequestType.Allergies);
				rec.setView(Viewpoint.Clinical);
				rec.setId(rs1.getInt("allergy_id"));

				rec.setInfoD(null);

				rec.setInfoR(null);
				rec.setInfoT(null);

				PreparedStatement p = this.conn.prepareCall("{call getDrug(?)}");
				p.setInt(1, rs1.getInt("drug_id"));
				ResultSet r = p.executeQuery();
				ArrayList<String> list = new ArrayList<>();
				while (r.next()) {
					String name = r.getString("name");
					list.add(name);
				}

//thore mpas j kamo tpt 
				rec.setInfoA(list);

				requests.add(rec);

			}
			PreparedStatement cs2 = this.conn.prepareCall("{call getRequestRecord()}");

			ResultSet rs2 = cs2.executeQuery();

			while (rs2.next()) {
				Request rec = new Request();
				rec.setAccepted(0);
				rec.setDescr(null);
				rec.setDt(rs2.getString("date"));
				rec.setType(RequestType.Reccord);
				rec.setView(Viewpoint.Clinical);
				rec.setId(rs2.getInt("record_id"));

				rec.setInfoD(null);

				rec.setInfoA(null);
				rec.setInfoT(null);

				PreparedStatement p = this.conn.prepareCall("{call getConditionName(?)}");
				p.setInt(1, rs2.getInt("condition_id"));
				ResultSet r = p.executeQuery();
				ArrayList<String> list = new ArrayList<>();
				while (r.next()) {
					String name = r.getString("name");
					list.add(name);
				}

				rec.setInfoR(list);
				requests.add(rec);
			}

			PreparedStatement cs3 = this.conn.prepareCall("{call getRequestTreatment()}");

			ResultSet rs3 = cs3.executeQuery();

			while (rs3.next()) {
				Request rec = new Request();
				rec.setAccepted(0);
				rec.setDescr(null);
				rec.setDt(rs3.getString("date"));
				rec.setType(RequestType.Treatment);
				rec.setView(Viewpoint.Clinical);
				rec.setId(rs3.getInt("treatment_id"));

				rec.setInfoD(null);

				rec.setInfoA(null);
				rec.setInfoR(null);

				PreparedStatement p = this.conn.prepareCall("{call getTreatment(?)}");
				p.setInt(1, rs3.getInt("treatment_id"));
				ResultSet r = p.executeQuery();
				ArrayList<String> list = new ArrayList<>();

				while (r.next()) {
					String name = r.getString("comments");
					list.add(name);
				}

				rec.setInfoT(list);

				requests.add(rec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}

	/**
	 * This method fetches the appointments of a patient with a doctor, based on
	 * their id.
	 * 
	 * @param doctor  Doctor doctor id
	 * @param patient Patient patient id
	 * @return An ArrayList of appointments
	 */
	public ArrayList<Appointment> getPatientAppointmentsWithDoctor(int doctor, int patient) {
		ArrayList<Appointment> rendez = new ArrayList<>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getPatientAppointmentWithADoctor(?,?)}");
			cs.setInt(1, doctor);
			cs.setInt(2, patient);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Appointment rec = new Appointment();
				rec.setDoctor_id(rs.getInt("doctor_id"));
				rec.setAppoint_id(rs.getInt("appoint_id"));
				rec.setPatient_id(rs.getInt("patient_id"));
				rec.setDate(rs.getString("date"));
				rec.setTime(rs.getString("time"));
				rec.setDropIn(rs.getBoolean("dropIn"));
				rec.setReceptionist_id(rs.getInt("receptionist_id"));
				rec.setAttended(rs.getBoolean("attended"));

				PreparedStatement cs2 = this.conn.prepareCall("{call getAppointmentRecords(?)}");
				cs2.setInt(1, rs.getInt("appoint_id"));
				ResultSet rs2 = cs2.executeQuery();
				boolean doesItExist = false;
				while (rs2.next()) {
					rec.setRecord_id(rs2.getInt("record_id"));
					doesItExist = true;
				}
				if (doesItExist == false)
					rec.setRecord_id(-1);

				rendez.add(rec);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rendez;
	}

	/**
	 * This method gets the appointment count of a clinic for a week. IF AN
	 * APPOINTMENT IS ATTENDED
	 * 
	 * @param clinic Integer id
	 * @return Integer array with the patient count
	 */
	public int[] getWeeksAppointments(int clinic) {
		int week[] = new int[7];
		String dates[] = Clock.getLastWeek();
		for (int i = 0; i < 7; i++) {
			try {
				CallableStatement cs = this.conn.prepareCall("{ ? = call  getDaysAppointments(?,?)}");
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.setString(2, dates[i]);
				cs.setInt(3, clinic);
				cs.execute();

				week[i] = cs.getInt(1);
				System.out.println(dates[i] + " " + week[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return week;
	}

	/**
	 * This method, fetches all the patients from the database
	 * 
	 * @return An Array List that contains all patients in the database
	 */
	public static void main(String[] args) {
		JDBC base = new JDBC();
		base.insertDoctorPatientRelationship(4, 0);

	}

//***************************************************/
	public Patient getPatientByID(int pat) {
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getPatientByID(?)}");
			cs.setInt(1, pat);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Patient p = new Patient();
				p.setEmail(rs.getString("email"));
				p.setName(rs.getString("name"));
				p.setSurname(rs.getString("surname"));
				p.setAlive(rs.getBoolean("alive"));

				return p;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//***************************************************/		

	public PatientRecord getRecordByID(int pat) {
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getRecordByID(?)}");
			cs.setInt(1, pat);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				PatientRecord p = new PatientRecord();
				p.setSelf_harm(rs.getBoolean("selfharm"));
				p.setPatient_id(rs.getInt("patient_id"));

				return p;

			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Clinic> getClinicsJo() {
		ArrayList<Clinic> clinics = new ArrayList<>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call getClinics()}");

			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Clinic rec = new Clinic();
				rec.setName(rs.getString("name"));
				rec.setClinic_id(rs.getInt("clinic_id"));
				clinics.add(rec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clinics;
	}

	/**
	 * This method, fetches all the clinics in the database
	 * 
	 * @return An array list of all the Clinics from the database
	 */
	public ArrayList<Clinic> getClinics() {
		ArrayList<Clinic> clinic_list = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getAllClinics()}");
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Clinic c = new Clinic();
				c.setClinic_id(rs.getInt("clinic_id"));
				c.setName(rs.getString("name"));

				clinic_list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clinic_list;
	}

	/**
	 * This method, fetches all the patients that were treated with a specific drug
	 * for a specific condition
	 * 
	 * @param cond  Specific condition id
	 * @param treat Specific treatment id
	 * @return Array list of patients
	 */
	public ArrayList<Patient> getReport2(int cond, int treat) {
		ArrayList<Patient> patient_list = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getPatientsConditionTreatment(?, ?)}");
			cs.setInt(1, cond);
			cs.setInt(2, treat);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Patient p = new Patient();
				p.setPatient_id(rs.getInt("patient_id"));
				p.setName(rs.getString("name"));
				p.setSurname(rs.getString("surname"));
				p.setDate(rs.getString("birthday"));
				p.setTelephone(rs.getString("telephone"));
				p.setEmail(rs.getString("email"));
				patient_list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient_list;
	}

	/**
	 * This method, fetches all the appointments of a specific clinic
	 * 
	 * @param clinic_id Clinic ID
	 * @return An array list of appointments
	 */
	public ArrayList<Appointment> getAppointments(int clinic_id) {
		ArrayList<Appointment> rendez = new ArrayList<>();
		try {

			PreparedStatement cs = this.conn.prepareCall("{call showAppointment_All(?)}");
			cs.setInt(1, clinic_id);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Appointment rec = new Appointment();
				rec.setDoctor_id(rs.getInt("doctor_id"));
				rec.setAppoint_id(rs.getInt("appoint_id"));
				rec.setPatient_id(rs.getInt("patient_id"));
				rec.setDate(rs.getString("date"));
				rec.setTime(rs.getString("time"));
				rec.setDropIn(rs.getBoolean("dropIn"));
				rec.setReceptionist_id(rs.getInt("receptionist_id"));
				rec.setAttended(rs.getBoolean("attended"));
				rendez.add(rec);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rendez;
	}

	/**
	 * This method, generates an array list of patients that have similar name as
	 * the user input
	 * 
	 * @param name Name of the patients that will get searched
	 * @return An array list of patients that have similar name with what was
	 *         entered in the search
	 */
	public ArrayList<Patient> search(String name) {
		ArrayList<Patient> patient_list = new ArrayList<>();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call searchPatient(?)}");
			cs.setString(1, name);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Patient p = new Patient();
				p.setPatient_id(rs.getInt("patient_id"));
				p.setName(rs.getString("name"));
				p.setSurname(rs.getString("surname"));
				p.setDate(rs.getString("birthday"));
				p.setTelephone(rs.getString("telephone"));
				p.setEmail(rs.getString("email"));
				patient_list.add(p);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return patient_list;
	}

	/**
	 * This method, gets an instance which is a copy of a specific appointment
	 * 
	 * @param app_id Appointment ID
	 * @return An appointment instance
	 */
	public Appointment getAppointment(int app_id) {
		Appointment rec = new Appointment();
		System.out.println(app_id);
		try {
			PreparedStatement cs = this.conn.prepareCall("{call ShowAppointment(?)}");
			cs.setInt(1, app_id);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				rec.setDoctor_id(rs.getInt("doctor_id"));
				rec.setAppoint_id(rs.getInt("appoint_id"));
				rec.setPatient_id(rs.getInt("patient_id"));
				rec.setDate(rs.getString("date"));
				rec.setTime(rs.getString("time"));
				rec.setDropIn(rs.getBoolean("dropIn"));
				rec.setReceptionist_id(rs.getInt("receptionist_id"));
				rec.setAttended(rs.getBoolean("attended"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rec;
	}

	/**
	 * This method, checks if a patient is still alive
	 * 
	 * @param p_id Patient ID
	 * @return true if the patient is alive, otherwise false
	 */
	public boolean CheckIfAlive(int p_id) {
		boolean is = true;
		try {
			PreparedStatement cs = this.conn.prepareCall("{call checkIfAlive(?)}");
			cs.setInt(1, p_id);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				is = rs.getBoolean("alive");
				System.out.println(is);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * This methos, inserts a new appoint ment into the database
	 * 
	 * @param doctor_id       Doctor ID
	 * @param patient_id      Patient ID
	 * @param clinic_id       Clinic ID
	 * @param date            Date
	 * @param time            Time
	 * @param drop_in         If it is drop in
	 * @param receptionist_id Receptionist ID
	 * @param att             If it was attended or not
	 * @return true for successful insertion otherwise false
	 */
	public boolean insertAppointment(int doctor_id, int patient_id, int clinic_id, String date, String time,
			int drop_in, int receptionist_id, int att) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call insertAppointment(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, doctor_id);
			cs.setInt(2, patient_id);
			cs.setInt(3, clinic_id);
			cs.setString(4, date);
			cs.setString(5, time);
			cs.setInt(6, drop_in);
			cs.setInt(7, receptionist_id);
			cs.setInt(8, att);
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method updates an appointment (attendance)
	 *
	 * @param app_id Integer id of an appointment
	 * @param val    1 or 0 for indicating attendance
	 * @return true if successful otherwise false
	 */
	public boolean updateAppointment(int app_id, int val) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call updateAppointment(?,?)}");
			cs.setInt(1, val);
			cs.setInt(2, app_id);
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method gets the last treatment given of a specific patient
	 * 
	 * @param pid Patient id
	 * @return Treatment instance (last treatment given)
	 */
	public Treatment getPersc(int pid) {
		Treatment rec = new Treatment();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getPerscription(?)}");
			cs.setInt(1, pid);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				rec.setDate(rs.getString("date"));
				rec.setDose(rs.getInt("dose"));
				rec.setComments(rs.getString("comments"));
				rec.setDoctor_id(rs.getInt("doctor_id"));
				rec.setPatient_id(rs.getInt("patient_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rec;
	}

	/**
	 * This method updates the data for a treatment record.
	 * 
	 * @param treat Updated Treatment record
	 */
	public void updateTreatment(Treatment treat) {
		System.out.println("\n\n Update Treatment \n\n");

		try {
			PreparedStatement cs = this.conn.prepareCall("{call updateTreatment(?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, treat.getTreatment_id());
			cs.setInt(2, treat.getDose());
			cs.setString(3, treat.getComments());
			cs.setBoolean(4, treat.isWarning());
			cs.setInt(5, treat.getDrug_id());
			cs.setString(6, treat.getLast_updated());
			cs.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method updates the data for a treatment record.
	 * 
	 * @param rec Updated Patient record
	 */
	public void updateRecord(PatientRecord rec) {
		System.out.println("\n\n Update Record \n\n");
		try {
			PreparedStatement cs = this.conn.prepareCall("{call updateRecord(?, ?, ?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, rec.getRecord_id());
			cs.setString(2, Clock.currentSQLTime());
			cs.setBoolean(3, rec.isSelf_harm());
			cs.setBoolean(4, rec.isThreat());
			cs.setBoolean(5, rec.isOverdose());
			cs.setBoolean(6, rec.isUnderdose());
			cs.setInt(7, rec.getCondition_id());
			cs.setInt(8, rec.getTreatment_id());
			cs.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method fetches the ID of the last appointment created.
	 * 
	 * @return Integer ID of appointment
	 */
	public int getLastAppointmentID() {
		int ret = -1;
		try {
			PreparedStatement cs = this.conn.prepareCall("{  call  getLastAppointmentID()}");
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				ret = rs.getInt("last_value");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 
	 * @param patient_id
	 * @return
	 */
	public Treatment getgenTreat(int patient_id) {

		Treatment treat = new Treatment();
		try {
			PreparedStatement cs = this.conn.prepareCall("{call ShowAllTreatments(?)}");
			cs.setInt(1, patient_id);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				treat.setPatient_id(rs.getInt("patient_id"));
				treat.setDoctor_id(rs.getInt("doctor_id"));
				treat.setDose(rs.getInt("dose"));
				treat.setComments(rs.getString("comments"));
				if (rs.getInt("warning") == 1)
					treat.setWarning(true);
				else
					treat.setWarning(false);
				treat.setDrug_id(rs.getInt("drug_id"));
				if (rs.getInt("accepted") == 1)
					treat.setAccepted(true);
				else
					treat.setAccepted(false);
				treat.setDate(rs.getString("date"));
			}
			return treat;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This method fetches the id of the last record inserted in the Database.
	 * 
	 * @return Integer ID
	 */
	public int getIDofLastRecord() {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call getIDofInsertedRecord()}");
			ResultSet rs = cs.executeQuery();
			int ret = -1;
			while (rs.next()) {
				ret = rs.getInt("last_value");
			}
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;

	}

	/**
	 * This method fetches the corresponding record of an appointment.
	 * 
	 * @param appoint Integer ID of an appointment
	 * @return Integer ID of the corresponding record
	 */
	public int getRecordOfAnAppointment(int appoint) {
		int rec = -1;
		try {
			PreparedStatement cs = this.conn.prepareCall("{call checkAppointmentRecordConnection(?)}");
			cs.setInt(1, appoint);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				rec = rs.getInt("record_id");
			}
			return rec;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * This method inserts a Record and Appointment relationship.
	 * 
	 * @param rec     Integer id of record
	 * @param appoint Integer id of appointment
	 */
	public void insertRecordAppointmentRelationship(int rec, int appoint) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call insertRecordAppointmentRealtion(?,?)}");
			cs.setInt(1, rec);
			cs.setInt(2, appoint);
			cs.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}