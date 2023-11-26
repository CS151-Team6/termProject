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
	
	
	void setProject(String id) {
		DatabaseController dbc = new DatabaseController();
		projId = id;
		Project project = dbc.getProject(id);
		title.setText(project.getName());
		date.setText(project.getStartDate());
		description.setText(project.getDescription());
		
		getTickets(id);
	}	
	
	
	void getTickets(String id) {
		
		DatabaseController dbc = new DatabaseController();
		ObservableList<String> tickets = dbc.getTickets(id);
		ticketList.setItems(tickets);
		adjustTicketListHeight();
		
		System.out.println("Project ID: " + id);
		System.out.println("WE GOT TICketS");
	}
	
	@FXML
	void redirectToViewTicket(ActionEvent event) {
		MainController mc = new MainController();
    	String ticketString = ticketList.getSelectionModel().getSelectedItem();
        String ticketId = mc.getIdFromString(ticketString);
        setViewTicketScene(event, ticketId, projId);
	}
	
    @FXML
    private void deleteSelectedTicket(ActionEvent event) {
        String ticketString = ticketList.getSelectionModel().getSelectedItem();
        
        if (ticketString != null) {
            String id = getIdFromString(ticketString);
            deleteTicket(id);
        }
    }
    
    private void deleteTicket(String id) {
        DatabaseController dbController = new DatabaseController();
        dbController.deleteById(id, "ticketTable");
        refreshTicketList();
    }
    
    private void refreshTicketList() {
        DatabaseController dbController = new DatabaseController();
        ObservableList<String> tickets = dbController.getTickets(projId);
        ticketList.setItems(tickets);
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

	
	private void setViewTicketScene(ActionEvent event, String ticketId, String projectId) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ViewTicket.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			ViewTicketController vtc = fxmlLoader.getController();
	        vtc.setTicket(ticketId);
	        vtc.setProject(projectId);
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
	
    String getIdFromString(String projectString) {
        // badly extract the id of the project through the string (gotta be a better way but its already saturday)
        int idOffset = 3;
        int idIndex = projectString.indexOf("id") + idOffset;
        int commaIndex = projectString.indexOf(",");
        
        String id = projectString.substring(idIndex, commaIndex);
        return id;
    }
}