package datareader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.Constants;

public class DBConnect {
	private static DBConnect instance = null;
	private static Connection conn;

	private DBConnect() {

		try {
			Class.forName(Constants.dbDriver);
			if(conn == null)
				conn = DriverManager.getConnection(Constants.url, Constants.username, Constants.password);
		} catch (ClassNotFoundException cnfErr) {
			cnfErr.printStackTrace();
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else
			return instance;
	}

	public static Connection getConnection() {
		return getInstance().conn;
	}
}
