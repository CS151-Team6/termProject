package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
    public static Connection connect(String databaseName) throws SQLException {
    	
        String db_url = "jdbc:sqlite:" + databaseName + ".db";
        return DriverManager.getConnection(db_url);
        
    }
}