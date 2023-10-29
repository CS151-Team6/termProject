package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import application.database.Ticket;


public class CreateCommentController implements Initializable{

	@FXML
	private TextArea comment;
	@FXML
	private TextField timestamp;
	
	private String ticketId;
	
	private String current;
	
	public void setTicketId(String id) {
		ticketId = id;
		
	}
	
	public String getId() {
		return ticketId;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		current = LocalDate.now().toString() + " " + LocalTime.now().toString();
		timestamp.setText(current);
	}
	
	@FXML
	private void goBack(ActionEvent event) {
		DatabaseController dbc = new DatabaseController();
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/ViewProject.fxml")); 
        try {
        	Parent root = loader.load();
        	ViewProjectController vpc = loader.getController();
        	Ticket curr = dbc.getTicket(ticketId);
        	vpc.setProject(String.valueOf(curr.getProjectId()));
        	stage.getScene().setRoot(root);
        }
        catch (IOException e){
        	e.printStackTrace();
        }
	}
	
	@FXML
	private void newCommentToView(ActionEvent event) {
		DatabaseController dbc = new DatabaseController();
		dbc.insertComment(ticketId, current, comment);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/ViewProject.fxml")); 
        try {
        	Parent root = loader.load();
        	ViewProjectController vpc = loader.getController();
        	Ticket curr = dbc.getTicket(ticketId);
        	vpc.setProject(String.valueOf(curr.getProjectId()));
        	stage.getScene().setRoot(root);
        }
        catch (IOException e){
        	e.printStackTrace();
        }
	}
	

	
}
