package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class ProjectController {

    @FXML
    private Hyperlink hyperlink;
    @FXML
    private TextField name;
    @FXML
    private DatePicker dateCreated;
    @FXML 
    private TextArea description;
    
    @FXML
    private void newProjectToHome(ActionEvent event) {
    	System.out.println(name.getText());
    	System.out.println(dateCreated.getValue().getClass());
    	System.out.println(description.getText());
    	
    	DatabaseController databaseController = new DatabaseController();
    	databaseController.insertProject(name, dateCreated, description);
    	
        String link = "view/Main.fxml";
        MainController mainController = new MainController();
        mainController.redirectHelper(event, link);
    }
}
