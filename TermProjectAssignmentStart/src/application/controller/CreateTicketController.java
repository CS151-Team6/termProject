package application.controller;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class CreateTicketController implements Initializable {
	@FXML
	ListView<String> projectList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// populate listView with all projects in  db
    	DatabaseController dbController = new DatabaseController();
    	ObservableList<String> projects = dbController.getAllProjects();
    	System.out.println(projects);
    	projectList.setItems(projects);
    	
    	adjustProjectListHeight();
	}
	
	private void adjustProjectListHeight() {
		int ROW_HEIGHT = 24;
		projectList.setPrefHeight(3 * ROW_HEIGHT);
	}
}
