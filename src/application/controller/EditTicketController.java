package application.controller;

import java.time.LocalDate;

import application.database.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditTicketController {
    private String ticketId;
    private String projectId;

    @FXML
    private TextField name;
    
    @FXML
    private TextField projectIdField;

    @FXML
    private TextArea description;

    void setTicket(String ticketId, String projectId) {
        this.ticketId = ticketId;
        this.projectId = projectId;

        DatabaseController dbc = new DatabaseController();
        Ticket curr = dbc.getTicket(ticketId);
        name.setText(curr.getName());
        description.setText(curr.getDescription());
        projectIdField.setText(projectId);
    }

    @FXML
    private void editTicket(ActionEvent event) {
        DatabaseController dbc = new DatabaseController();
        String newProjectId = projectIdField.getText(); // Get the new project ID
        dbc.editTicket(ticketId, newProjectId, name.getText(), description.getText());
        goHome(event);
    }

    void goHome(ActionEvent event) {
        MainController mc = new MainController();
        mc.redirectHelper(event, "view/Main.fxml");
    }
}