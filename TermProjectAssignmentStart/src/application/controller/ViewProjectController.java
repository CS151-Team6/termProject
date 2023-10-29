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
	private ListView<String> commentList;
	
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
	
	String getIdFromTicket(String ticket) {
		int offset = 3;
		int idIndex = ticket.indexOf("id");
		int end = ticket.indexOf(",");
		
		return ticket.substring(idIndex, end);
	}
	
	@FXML
	void viewProjectToNewComment(ActionEvent event) {
		String ticket = ticketList.getSelectionModel().getSelectedItem();
		String id = getIdFromTicket(ticket);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/NewComment.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			CreateCommentController ccc = fxmlLoader.getController();
	        ccc.setTicketId(id);
	        stage.getScene().setRoot(root);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void adjustTicketListHeight() {
		int ROW_HEIGHT = 24;
		ticketList.setPrefHeight(3 * ROW_HEIGHT);
	}
	
	@FXML 
	void showComments(ActionEvent event) {
		String ticket = ticketList.getSelectionModel().getSelectedItem();
		String id = getIdFromTicket(ticket);
		DatabaseController dbc = new DatabaseController();
		ObservableList<String>  comments = dbc.getComments(id);
		commentList.setItems(comments);
		adjustTicketListHeight();
		
	}
	
	@FXML 
	void goBack(ActionEvent event) {
		Parent newRoot = null;
		try {
			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
	}
}