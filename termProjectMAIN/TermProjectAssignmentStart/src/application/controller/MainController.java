package application.controller;
import java.io.IOException;

import application.database.Project;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private Hyperlink hyperlink;
    @FXML
    private ListView<String> projectList;
    @FXML
    private TextField keyword;
 
    @FXML
    private void homeToNewProject(ActionEvent event) {
        // Handle the hyperlink click event here
        String link = "view/NewProject.fxml";
        Parent newRoot = null;
		try {
			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource(link));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
    }
    
    @FXML
    private void homeToViewProject(ActionEvent event) {
    	String projectString = projectList.getSelectionModel().getSelectedItem();
        String id = getIdFromString(projectString);
        setViewProjectScene(event, id);
    }
    
    @FXML
    private void homeToNewTicket(ActionEvent event) {
    	redirectHelper(event, "view/NewTicket.fxml");
    }
    
    @FXML
    private void homeToNewComment(ActionEvent event) {
    	String link = "view/NewComment.fxml";
        Parent newRoot = null;
		try {
			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource(link));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
    }
    
    String getIdFromString(String projectString) {
        // badly extract the id of the project through the string (gotta be a better way but its already saturday)
        int idOffset = 3;
        int idIndex = projectString.indexOf("id") + idOffset;
        int commaIndex = projectString.indexOf(",");
        
        String id = projectString.substring(idIndex, commaIndex);
        return id;
    }
    
    // passes id to the viewProject page
    private void setViewProjectScene(ActionEvent event, String id) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ViewProject.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			ViewProjectController vpc = fxmlLoader.getController();
	        vpc.setProject(id);
	        stage.getScene().setRoot(root);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // does not pass parameters to link
    void redirectHelper(ActionEvent event, String link) {
        Parent newRoot = null;
		try {
			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource(link));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
    }
    
    public void databaseOpenTest() {
    	DatabaseController dbController = new DatabaseController();
    	dbController.openDatabase();
    	dbController.createProjectTable();
    	dbController.createTicketTable();
    	dbController.createCommentTable();
    }
    
    //search for project
    @FXML
    public void searchProject(ActionEvent event) {
    	DatabaseController dbc = new DatabaseController();
    	String searchKeyword = keyword.getText();
    	
    	if (searchKeyword == null || searchKeyword.isEmpty()) {
    		projectList.setItems(dbc.getAllProjects());
    	} else {
    		projectList.setItems(dbc.getProjectWithKeyword(searchKeyword.toLowerCase()));
    	}
    	
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	DatabaseController dbController = new DatabaseController();
    	ObservableList<String> projects = dbController.getAllProjects();
    	System.out.println(projects);
    	projectList.setItems(projects);
	}
}