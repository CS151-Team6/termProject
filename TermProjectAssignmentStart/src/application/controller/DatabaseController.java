package application.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class DatabaseController {
	
    void openDatabase() {
        try (Connection conn =
                DriverManager.getConnection("jdbc:sqlite:test.db");
            ) {
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    
    void createTable() {
        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println( "Opened database successfully" );
        
        String query = "CREATE TABLE IF NOT EXISTS projectTable ( " +
                "ID INTEGER PRIMARY KEY, " +
                "CREATED DATE, " +
                "DESCRIPTION TEXT)";


        try ( Connection conn = ds.getConnection();
              Statement stmt = conn.createStatement(); ) {
              stmt.executeUpdate( query );
              System.out.println( "Created database successfully" );
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
    }
}