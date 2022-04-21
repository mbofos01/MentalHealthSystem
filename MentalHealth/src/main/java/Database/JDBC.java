package Database;

import java.sql.*;
import java.util.ArrayList;

import Objects.*;

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
				rec.setId(rs.getInt("id"));
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
				rec.setId(rs.getInt("id"));
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
}