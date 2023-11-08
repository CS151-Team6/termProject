package application.controller;
import application.database.Comment;
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
}
