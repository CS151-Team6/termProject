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
	
	private String selectedCommentId;
	
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
	
	String getIdFromString(String commentString) {
	    // same way of getting the id for viewProject 
	    int idStartIndex = commentString.indexOf("id=") + 3;
	    int idEndIndex = commentString.indexOf(",", idStartIndex);

	    String id = commentString.substring(idStartIndex, idEndIndex);
	    return id;
	}


	
	@FXML
	private void editSelectedComment(ActionEvent event) {
	    String selectedCommentString = commentList.getSelectionModel().getSelectedItem();
	    selectedCommentId = getIdFromString(selectedCommentString); 

	    if (selectedCommentId != null) {
	        setEditCommentScene(event, selectedCommentId);
	    }
	}

	private void setEditCommentScene(ActionEvent event, String commentId) {
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/EditComment.fxml"));
	    try {
	        Parent root = fxmlLoader.load();
	        EditCommentController ecc = fxmlLoader.getController();
	        ecc.setComment(commentId);
	        stage.getScene().setRoot(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
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
