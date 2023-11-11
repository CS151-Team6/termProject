package application.controller;

import application.database.Comment;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import application.database.Project;
import application.database.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class ViewTicketController {
	@FXML
	ListView<String> commentList;
	
	@FXML
	Text title;
	
	@FXML
	Text date;
	
	@FXML
	Text description;
	
	@FXML
	void setTicket(String id) {
		DatabaseController dbc = new DatabaseController();
		Ticket ticket = dbc.getTicket(id);
		commentList.setItems(dbc.getComments(id));
		
		title.setText(ticket.getName());
		date.setText(ticket.getCreated());
		description.setText(ticket.getDescription());
	}
		
	
	 @FXML
	    private void homeToNewTicket(ActionEvent event) {
	    	redirectHelper(event, "view/NewTicket.fxml");
	    }
	 
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
	
}

