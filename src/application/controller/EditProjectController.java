package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditProjectController {
	private String projectId;
	
	@FXML
	private TextField name;
	
	@FXML
	private DatePicker dateCreated;
	
	@FXML
	private TextArea description;
	
	void setProject(String projectId) {
		this.projectId = projectId;
	}
	
	@FXML
	private void editProject(ActionEvent event) {
		DatabaseController dbc = new DatabaseController();
		dbc.editProject(projectId, name.getText(), dateCreated.getValue().toString(), description.getText());
		goHome(event);
	}
	
	void goHome(ActionEvent event) {
		MainController mc = new MainController();
		mc.redirectHelper(event, "view/Main.fxml");
	}
}
