package tables_in_postgresql;

import java.sql.Connection;
import java.util.List;

public class TableListenerApp {

	public static void main(String[] args) {

		Connection conn = null;
		
		try {
			conn = DicDbConnector.getConnection();
			if(conn != null) {
				System.out.println("Connection created");
				System.out.println("Listing Tables");
				List<String> tables = DicDbConnector.listTables();
				for(String tableName : tables) {
					System.out.println(tableName);
				}
			} else {
				System.out.println("Connection NOT created.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DicDbConnector.close(conn);
		}
	}

}
