package tables_in_postgresql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DicDbConnector {
	
	private static String _POSTGRESQL_DRIVER = "org.postgresql.Driver";
	private static String _URL = "jdbc:postgresql://localhost:5432/";
	private static String _UID = "xxx";
	private static String _PWD = "xxx";
	private static String _DBNAME = "test";
	private static Connection _conn = null;
	
	public static Connection getConnection() {
		return getConnection(_DBNAME);
	}
	

	public static Connection getConnection(String dbName) {
		try {
			if(_conn == null || _conn.isClosed()) {
				Class.forName(_POSTGRESQL_DRIVER);
				String url = (_URL + dbName);
				_conn = DriverManager.getConnection(url, _UID, _PWD);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return _conn;
	}
	
	public static boolean close(AutoCloseable closeable) {
		try {
			if(closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	} 
	
	public static List<String> listTables() {
		List<String> tables = new ArrayList<>();
		try (Connection connection = getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, null, new String[] {"TABLE"});
			while(resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				tables.add(tableName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tables;
	}
	
}
