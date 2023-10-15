package application.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.sqlite.SQLiteDataSource;

import application.database.Comment;
import application.database.Project;
import application.database.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	// used for UPDATE, INSERT, and DELETE
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
	
	// used for SELECT
	private ResultSet executeQuery(String query) {
		SQLiteDataSource ds = getDataSource();
		ResultSet resultSet;
		
		try {
	        Connection connection = ds.getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        System.out.println("query: " + query + " succeeded");

	        return resultSet;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private int countRowsInTable() {
		String query = "SELECT COUNT(*) FROM projectTable";
		SQLiteDataSource ds = getDataSource();
		ResultSet resultSet;
		
		try {
	        Connection connection = ds.getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        int rowCount = resultSet.getInt(1);
	        
	        connection.close();
	        return rowCount;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
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
                "id int AUTO_INCREMENT PRIMARY KEY, " +
        		"name string, " +
                "created date, " +
                "description text)";
        
        updateQuery(query);
    }
    
    void insertProject(TextField nameField, DatePicker dateField, TextArea descriptionField) {
    	int id = countRowsInTable(); // used to get the id since autoincrement isnt working
    	String name = "\"" + nameField.getText() + "\"" ;
    	String date = "\""  + dateField.getValue().toString() + "\"" ;
    	String text = "\""  + descriptionField.getText() + "\"";
    	
    	System.out.println("ID: " + id);
    	String query = "INSERT INTO projectTable (id, name, created, description) " +
    			"VALUES (" + id + ", " + name + ", " + date + ", " + text + ")";
    	updateQuery(query);
    }

    public void updateComment(Comment comment) {
        String updateSQL = "UPDATE comments SET text = ?, timeStamp = ? WHERE id = ?";
        SQLiteDataSource ds = getDataSource();
        
        try(Connection connection = ds.getConnection()) {
        	PreparedStatement statement = connection.prepareStatement(updateSQL);
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
        SQLiteDataSource ds = getDataSource();
        
        try(Connection connection = ds.getConnection()) {
        	PreparedStatement statement = connection.prepareStatement(updateSQL);
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
        SQLiteDataSource ds = getDataSource();
        
        try(Connection connection = ds.getConnection()) {
        	PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setString(1, projectName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTicket(Ticket ticket) {
        String updateSQL = "UPDATE tickets SET name = ?, description = ?, status = ? WHERE id = ?";
        SQLiteDataSource ds = getDataSource();
        
        try(Connection connection = ds.getConnection()) {
        	PreparedStatement statement = connection.prepareStatement(updateSQL);
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
        SQLiteDataSource ds = getDataSource();
        
        try(Connection connection = ds.getConnection()) {
        	PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setInt(1, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    
    ObservableList<String> getAllProjects() {
    	String query = "SELECT * from projectTable";
    	ResultSet records = executeQuery(query);
    	ObservableList<String> projects = FXCollections.observableArrayList();
    	if (records == null) return projects;
    	
    	// create project instances and add them to the list of project strings
    	try {
			while (records.next()) {
				try {
					int id = records.getInt("id");
					String name = records.getString("name");
	                String createdAt = records.getString("created");
	                String description = records.getString("description");
	                
	                Project project = new Project(id, name, createdAt, description);
	                projects.add(project.toString());
	                System.out.println(project);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return projects;
    }
    
}