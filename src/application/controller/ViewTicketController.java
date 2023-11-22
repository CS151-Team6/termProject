package application.controller;
import java.io.IOException;

import application.database.Comment;
import application.database.Project;
import application.database.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewTicketController {
	@FXML
	ListView<String> commentList;
	
	@FXML
	Text title;
	
	@FXML
	Text date;
	
	@FXML
	Text description;
	
	private String projectId;
	
	
	void setTicket(String id) {
		DatabaseController dbc = new DatabaseController();
		Ticket ticket = dbc.getTicket(id);
		commentList.setItems(dbc.getComments(id));
		title.setText(ticket.getName());
		description.setText(ticket.getDescription());
	}
	
	void setProject(String id) {
		projectId = id;
	}
	
	@FXML
	void ticketToViewProject(ActionEvent event) {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ViewProject.fxml")); 
        try {
			Parent root = fxmlLoader.load();
			ViewProjectController vpc = fxmlLoader.getController();
	        vpc.setProject(projectId);
	        stage.getScene().setRoot(root);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
