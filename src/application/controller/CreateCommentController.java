package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateCommentController implements Initializable {
	@FXML
	private ListView<String> ticketList;
	@FXML
	private TextArea description;
	
	private String ticketId;
	
	private String projId;
	
	@FXML
	private TextField timestamp;
	
	
	@FXML
	void createComment(ActionEvent event) {
    	DatabaseController databaseController = new DatabaseController();
    	MainController mainController = new MainController();
    	 
    	String ticketString = ticketList.getSelectionModel().getSelectedItem();
        String ticketId = mainController.getIdFromString(ticketString);
        System.out.println("TICKET ID: " + ticketId);
        
        String now = LocalDate.now().toString() + " " + LocalTime.now().toString();
    	
        databaseController.insertComment(ticketId, now, description);
        String homePage = "view/Main.fxml";
        mainController.redirectHelper(event, homePage);
	}
	
	@FXML
	void commentToView(ActionEvent event) {
		//TODO: write the code to take the user back to the main page(for now)
		MainController mc = new MainController();
		mc.redirectHelper(event, "view/Main.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DatabaseController dbc = new DatabaseController();
		ObservableList<String> tickets = dbc.getTickets(""); // blank id gets all tickets
		ticketList.setItems(tickets);
		adjustTicketListHeight();
		String curr =  LocalDate.now().toString() + " " + LocalTime.now().toString();
		timestamp.setText(curr);
	}
	
	private void adjustTicketListHeight() {
		int ROW_HEIGHT = 24;
		ticketList.setPrefHeight(3 * ROW_HEIGHT);
	}
	
	public void setProjectId(String proj) {
		projId = proj;
	}

}
