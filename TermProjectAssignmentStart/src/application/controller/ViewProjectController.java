package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.database.Project;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ViewProjectController  {

	@FXML
	private Text title;
	@FXML
	private Text date;
	@FXML
	private Text description;
	@FXML
	private ListView<String> ticketList;
	
	@FXML
	void setProject(String id) {
		DatabaseController dbc = new DatabaseController();

		Project project = dbc.getProject(id);
		title.setText(project.getName());
		date.setText(project.getStartDate());
		description.setText(project.getDescription());
		
		getTickets(id);
	}	
	
	@FXML
	void getTickets(String id) {
		DatabaseController dbc = new DatabaseController();
		ObservableList<String> tickets = dbc.getTickets(id);
		ticketList.setItems(tickets);
		adjustTicketListHeight();
	}
	
	private void adjustTicketListHeight() {
		int ROW_HEIGHT = 24;
		ticketList.setPrefHeight(3 * ROW_HEIGHT);
	}
}
