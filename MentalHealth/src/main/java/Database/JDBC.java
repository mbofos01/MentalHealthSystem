package Database;

import java.sql.*;
import java.util.ArrayList;

import Objects.*;
import Objects.PatientRecord;

/**
 * This object is the middleware between the server and the SQL Database. Each
 * method of this object should call a procedure which is defined in the actual
 * DB, and also return an object or a list of objects whom handle the returned
 * data.
 * 
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class JDBC {
	private boolean dbDriverLoaded = false;
	private Connection conn = null;

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
	 * @param id
	 * @return
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
				flag++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (flag == 0)
			patient.setPatient_id(-1);
		return patient;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

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
				allergies.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allergies;
	}

	public int insertTreatment(Treatment treat) {
		try {
			CallableStatement cs = this.conn.prepareCall("{ ? = call  insertTreatment(?,?,?,?,?,?,?)}");

			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setInt(2, treat.getPatient_id());
			cs.setInt(3, treat.getDoctor_id());
			cs.setInt(4, treat.getDose());
			cs.setString(5, treat.getComments());
			cs.setBoolean(6, treat.isWarning());
			cs.setInt(7, treat.getDrug_id());
			cs.setBoolean(8, treat.isAccepted());
			cs.execute();
			System.out.println(cs.getInt(1));
			return cs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public boolean insertPendingDeath(int patient, int doctor, String dateOfDeath) {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call requestDeathOfPatient(?,?,?)}");
			cs.setInt(1, patient);
			cs.setInt(2, doctor);
			cs.setString(3, dateOfDeath);

			cs.execute();

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static void main(String[] args) {
		JDBC base = new JDBC();
		base.getPatientRecords(1, 0);
	}

}