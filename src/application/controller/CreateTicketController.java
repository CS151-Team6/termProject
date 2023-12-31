package application.controller;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    	
        databaseController.insertTicket(projectId, name, description);
        String homePage = "view/Main.fxml";
        mainController.redirectHelper(event, homePage);
	}
	
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
