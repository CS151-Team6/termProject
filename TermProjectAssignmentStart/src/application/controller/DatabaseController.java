package application.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DatabaseController {
	private SQLiteDataSource getDataSource() {
        SQLiteDataSource ds = null;

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        
        return ds;
	}
	
	private int updateQuery(String query) {
		SQLiteDataSource ds = getDataSource();
		try {
			Connection connection = ds.getConnection();
			Statement statement = connection.createStatement();
			System.out.println("query: " + query + " succeeded");
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	private int executeQuery(String query) {
		SQLiteDataSource ds = getDataSource();
		ResultSet resultSet;
		int rowCount = 0;
		
		try {
	        Connection connection = ds.getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        System.out.println("query: " + query + " succeeded");
	        
	        while (resultSet.next()) rowCount++;
		} catch(Exception e) {
			e.printStackTrace();
		}
        
        return rowCount;
	}
	
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
        String query = "CREATE TABLE IF NOT EXISTS projectTable ( " +
                "ID INTEGER AUTO_INCREMENT PRIMARY KEY, " +
        		"NAME STRING, " +
                "CREATED DATE, " +
                "DESCRIPTION TEXT)";
        
        updateQuery(query);
    }
    
    void insertProject(TextField nameField, DatePicker dateField, TextArea descriptionField) {
    	String name = "\"" + nameField.getText() + "\"" ;
    	String date = "\""  + dateField.getValue().toString() + "\"" ;
    	String text = "\""  + descriptionField.getText() + "\"";
    	
    	String query = "INSERT INTO projectTable (NAME, CREATED, DESCRIPTION) " +
    			"VALUES (" + name + ", " + date + ", " + text + ")";
    	updateQuery(query);
    }

    public void updateComment(Comment comment) {
        String updateSQL = "UPDATE comments SET text = ?, timeStamp = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, comment.getText());
            statement.setString(2, comment.getTimeStamp());
            statement.setInt(3, comment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProject(Project project) {
        String updateSQL = "UPDATE projects SET start_date = ?, description = ? WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, project.getStartDate());
            statement.setString(2, project.getDescription());
            statement.setString(3, project.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(String projectName) {
        String deleteSQL = "DELETE FROM projects WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setString(1, projectName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTicket(Ticket ticket) {
        String updateSQL = "UPDATE tickets SET name = ?, description = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, ticket.getName());
            statement.setString(2, ticket.getDescription());
            statement.setInt(3, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTicket(int ticketId) {
        String deleteSQL = "DELETE FROM tickets WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
}
