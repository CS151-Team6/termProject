package application.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resultSet;
	}
	
	private int countRowsInTable(String tableName) {
		String query = "SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT 1;";
		SQLiteDataSource ds = getDataSource();
		ResultSet resultSet;
		
		try {
	        Connection connection = ds.getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.isClosed()) return 0; // results are closed for empty tables
	        int rowCount = resultSet.getInt("id") + 1;
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
    
    private Connection connection;
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    void createProjectTable() {
        String query = "CREATE TABLE IF NOT EXISTS projectTable ( " +
                "id int AUTO_INCREMENT PRIMARY KEY, " +
        		"name string, " +
                "created date, " +
                "description text)";
        
        updateQuery(query);
    }
    
    
    void createTicketTable() {
        String query = "CREATE TABLE IF NOT EXISTS ticketTable ( " +
                "id int AUTO_INCREMENT PRIMARY KEY, " +
        		"project_id int, " +
        		"name string, " +
                "description text)";
        
        updateQuery(query);
    }
    
    void createCommentTable() {
        String query = "CREATE TABLE IF NOT EXISTS commentTable ( " +
                "id int AUTO_INCREMENT PRIMARY KEY, " +
        		"ticket_id int, " +
        		"created string, " +
                "description text)";
        
        updateQuery(query);
    }
    
    void insertProject(TextField nameField, DatePicker dateField, TextArea descriptionField) {
    	int id = countRowsInTable("projectTable"); // used to get the id since autoincrement isnt working
    	System.out.println("rows: " + id);
    	
    	String name = "\"" + nameField.getText() + "\"" ;
    	String date = "\""  + dateField.getValue().toString() + "\"" ;
    	String text = "\""  + descriptionField.getText() + "\"";
    	
    	String query = "INSERT INTO projectTable (id, name, created, description) " +
    			"VALUES (" + id + ", " + name + ", " + date + ", " + text + ")";
    	updateQuery(query);
    }
    
    void insertTicket(String projectId, TextField nameField, TextArea descriptionField) {
    	int id = countRowsInTable("ticketTable"); // used to get the id since autoincrement isnt working
    	String name = "\"" + nameField.getText() + "\"" ;
    	String text = "\""  + descriptionField.getText() + "\"";
    	
    	String query = "INSERT INTO ticketTable (id, project_id, name, description) " +
    			"VALUES (" + id + ", " + projectId + ", " + name + ", " + text + ")";
    	updateQuery(query);
    }
    
    
    void insertComment(String ticketId, String created, TextArea descriptionField) {
    	int id = countRowsInTable("commentTable"); // used to get the id since autoincrement isnt working
    	created = "\"" + created + "\"";
    	String text = "\""  + descriptionField.getText() + "\"";
    	
    	String query = "INSERT INTO commentTable (id, ticket_id, created, description) " +
    			"VALUES (" + id + ", " + ticketId + ", " + created + ", " + text + ")";
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

    public void editProject(String projectId, String name, String dateCreated, String description) {
    	
        String updateSQL = "UPDATE projectTable SET name = " +
        		"\"" + name + "\"" + ", " +
        		"created = " + "\"" +  dateCreated + "\"" + ", " + 
        		"description = " + "\"" +  description + "\"" + 
        		" WHERE id=" + projectId;
        		
        System.out.println(updateSQL);
        updateQuery(updateSQL);
    }
    
    public void editTicket(String ticketId, String projectId, String name, String description) {
        String updateSQL = "UPDATE ticketTable SET project_id = " +
                "\"" + projectId + "\"" + ", " +
                "name = " + "\"" + name + "\"" + ", " +
                "description = " + "\"" + description + "\"" +
                " WHERE id=" + ticketId;

        System.out.println(updateSQL);
        try {
            updateQuery(updateSQL);
        } finally {
            closeConnection();
        }
    }
    
    public void editComment(String commentId, String newText, String newTimeStamp) {
        String updateSQL = "UPDATE commentTable SET description = ?, created = ? WHERE id = ?";

        SQLiteDataSource ds = getDataSource();

        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {

            statement.setString(1, newText);
            statement.setString(2, newTimeStamp);
            statement.setString(3, commentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    void deleteById(String id, String tableName) {
        String deleteSQL = "DELETE FROM " + tableName + " WHERE id = ?";
        SQLiteDataSource ds = getDataSource();

        try (Connection connection = ds.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    void deleteTicketsOfProject(String projectId) {
        String deleteSQL = "DELETE FROM ticketTable WHERE project_id = ?";
        SQLiteDataSource ds = getDataSource();

        try (Connection connection = ds.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setString(1, projectId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    void deleteCommentsOfTicket(String ticketId) {
    	String deleteSQL = "DELETE FROM commentTable WHERE ticket_id = ?";
        SQLiteDataSource ds = getDataSource();

        try (Connection connection = ds.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
            statement.setString(1, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // take an sql query that asks for a project and return a java object representing project
    Project getProject(String id) {
    	String query = "SELECT * from projectTable WHERE id = " + id;
    	ResultSet project = executeQuery(query);
    	Project projectObject = null;
    	
    	try {
    		while(project.next()) {
				int projectId = project.getInt("id");
				String name = project.getString("name");
	            String createdAt = project.getString("created");
	            String description = project.getString("description");
	            projectObject = new Project(projectId, name, createdAt, description);
    		}
            
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
    	
    	return projectObject;
    }
    
    Ticket getTicket(String id) {
    	String query = "SELECT * from ticketTable WHERE id = " + id;
    	ResultSet ticket = executeQuery(query);
    	Ticket ticketObject = null;
    	try {
    		// id, name, created, description
    		while (ticket.next()) {
				int ticketId = ticket.getInt("id");
				String projId = ticket.getString("project_id");
	            String name = ticket.getString("name");
	            String description = ticket.getString("description");

	            ticketObject = new Ticket(ticketId, projId, name, description); 
            }
		} catch (SQLException e) { 
			e.printStackTrace(); 
		}
    	
    	return ticketObject;
    }
    
    public Comment getComment(String id) {
        String query = "SELECT * FROM commentTable WHERE id = " + id;
        ResultSet commentResultSet = executeQuery(query);
        Comment commentObject = null;

        try {
            while (commentResultSet.next()) {
                int commentId = commentResultSet.getInt("id");
                String text = commentResultSet.getString("description");
                String timeStamp = commentResultSet.getString("created");

                commentObject = new Comment(commentId, text, timeStamp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentObject;
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
    	createProjectTable(); // used in case projectTable gets deleted
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
    
    public ObservableList<String> getProjectWithKeyword(String key) {
    	String query = "SELECT * from projectTable";
    	ResultSet records = executeQuery(query);
    	ObservableList<String> projects = FXCollections.observableArrayList();
    	
    	if (records != null) {
    		try {
    			while (records.next()) {
    				try {
    					String name = records.getString("name");
    					if (name.toLowerCase().contains(key)) {
    						int id = records.getInt("id");
    						String desc = records.getString("description");
    		                String createdAt = records.getString("created");
    		                
    		                Project p = new Project(id, name, createdAt, desc);
    		                projects.add(p.toString());
    					}
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return projects;
    }
    
    ObservableList<String> getTickets(String projectId) {
        createProjectTable(); // used in case projectTable gets deleted
        String query = "";

        if (projectId == null || projectId.isEmpty())
            query = "SELECT * FROM ticketTable";
        else
            query = "SELECT * FROM ticketTable WHERE project_id = " + projectId;

        ResultSet records = executeQuery(query);
        ObservableList<String> tickets = FXCollections.observableArrayList();
        if (records == null) return tickets;

        // create project instances and add them to the list of project strings
        try {
            while (records.next()) {
                try {
                    int ticketId = records.getInt("id");
                    String projId = records.getString("project_id");
                    String name = records.getString("name");
                    String description = records.getString("description");

                    Ticket project = new Ticket(ticketId, projId, name, description);
                    tickets.add(project.toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    
    //stores Ticket instead of String
    ObservableList<String> getTicketsWithKeyword(String id, String key) {
    	String query = "SELECT * FROM ticketTable";
    	ResultSet records = executeQuery(query);
    	ObservableList<String> tickets = FXCollections.observableArrayList();
    	
    	if (records != null) {
    		try {
    			while (records.next()) {
    				try {
    					String projId = records.getString("project_id");
    					String title = records.getString("name");
    					if (title.toLowerCase().contains(key) && projId.equals(id)) {
    						int ticketId = records.getInt("id");
    						String desc = records.getString("description");
    						Ticket ticket = new Ticket(ticketId, projId, title, desc);
    						tickets.add(ticket.toString());
    					}
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return tickets;
    }
    
    ObservableList<String> getComments(String id) {
    	createTicketTable(); // used in case ticketTable gets deleted
    	String query = "SELECT * FROM commentTable WHERE ticket_id = " + id;
    	
    	ResultSet records = executeQuery(query);
    	ObservableList<String> comments = FXCollections.observableArrayList();
    	
    	if (records == null) return comments;
    	
    	// create project instances and add them to the list of project strings
    	try {
			while (records.next()) {
				try {
					int commentId = records.getInt("id");
					String text = records.getString("description");
	                String createdAt = records.getString("created");
	                
	                Comment comment = new Comment(commentId, text, createdAt);
	                comments.add(comment.toString());
	            
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return comments;	
    }
    
    
}
