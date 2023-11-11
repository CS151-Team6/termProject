package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.database.Project;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	private TextField keyword;
	
	private String projId;
	
	@FXML
	void setProject(String id) {
		DatabaseController dbc = new DatabaseController();
		projId = id;
		System.out.println(projId);
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
	
	@FXML
	void redirectToViewComment(ActionEvent event) {
		MainController mc = new MainController();
    	String ticketString = ticketList.getSelectionModel().getSelectedItem();
        String id = mc.getIdFromString(ticketString);
        setViewCommentScene(event, id);
	}
	
	@FXML
	void searchTicket(ActionEvent event) {
		DatabaseController dbc = new DatabaseController();
		if (keyword.getText() == null) {
    		ticketList.setItems(dbc.getTickets(projId));
    	} else {
    		ticketList.setItems(dbc.getTicketsWithKeyword(projId, keyword.getText().toLowerCase()));
    	}
	}

	
	private void setViewCommentScene(ActionEvent event, String id) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ViewTicket.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			ViewTicketController vtc = fxmlLoader.getController();
	        vtc.setTicket(id);
	        stage.getScene().setRoot(root);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	@FXML
	void goHome(ActionEvent event) {
		MainController mc = new MainController();
		mc.redirectHelper(event, "view/Main.fxml");
	}
	
	private void adjustTicketListHeight() {
		int ROW_HEIGHT = 24;
		ticketList.setPrefHeight(3 * ROW_HEIGHT);
	}
}
