package Database;

//import java.io.*;
import java.sql.*;

public class JDBC {
	private boolean dbDriverLoaded = false;
	private Connection conn = null;
	// handling the keyboard inputs through a BufferedReader
	// This variable became global for your convenience.
	// private BufferedReader in = new BufferedReader(new
	// InputStreamReader(System.in));

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

	public static void main(String[] args) throws SQLException {
		JDBC base = new JDBC();
		base.select();

	}

	/**
	 * Example for insert action
	 */
	public void insert() {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call vale(?,?)}");
			cs.setString(1, "test1");
			cs.setString(2, "kaloee");
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Example for get action
	 */
	public void select() {
		try {
			PreparedStatement cs = this.conn.prepareCall("{call pare(?)}");
			cs.setString(1, "kaloee");
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("test") + "  " + rs.getString("extra_col"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}