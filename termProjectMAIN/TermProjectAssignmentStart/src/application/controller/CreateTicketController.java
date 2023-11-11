package application.controller;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTicketController implements Initializable {
	@FXML
	ListView<String> projectList;
    @FXML
    private TextField name;
    @FXML
    private DatePicker dateCreated;
    @FXML 
    private TextArea description;

	@FXML
	private void createTicket(ActionEvent event) {
    	DatabaseController databaseController = new DatabaseController();
    	MainController mainController = new MainController();
    	 
    	String projectString = projectList.getSelectionModel().getSelectedItem();
        String projectId = mainController.getIdFromString(projectString);
    	
        databaseController.insertTicket(projectId, name, dateCreated, description);
        String homePage = "view/Main.fxml";
        mainController.redirectHelper(event, homePage);
	}
	
//	@FXML
//	public void viewTickets(ActionEvent event){
//	    	redirectHelper(event, "view/ViewTicket.fxml");
//	    }
//	
//	void redirectHelper(ActionEvent event, String link) {
//        Parent newRoot = null;
//		try {
//			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource(link));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//        stage.getScene().setRoot(newRoot);
//    }
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// populate listView with all projects in  db
    	DatabaseController dbController = new DatabaseController();
    	ObservableList<String> projects = dbController.getAllProjects();
    	projectList.setItems(projects);
    	
    	adjustProjectListHeight();
	}
	
	private void adjustProjectListHeight() {
		int ROW_HEIGHT = 24;
		projectList.setPrefHeight(3 * ROW_HEIGHT);
	}
}
