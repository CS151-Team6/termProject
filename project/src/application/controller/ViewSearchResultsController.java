package application.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ViewSearchResultsController {
	@FXML
	ListView<String> projectList;
	@FXML
	ListView<String> ticketList;
	
	void setProjectList(ObservableList<String> projects) {
		System.out.println("Projects: " + projects);
		projectList.setItems(projects);
	}
	
	void setTicketList(ObservableList<String> tickets) {
		ticketList.setItems(tickets);
	}
	
	@FXML
	void goHome(ActionEvent event) {
		MainController mc = new MainController();
		mc.redirectHelper(event, "view/Main.fxml");
	}
}
