package application.controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    	System.out.println(dateCreated.getValue());
    	System.out.println(description.getText());
        String link = "view/Main.fxml";
        MainController mainController = new MainController();
        mainController.redirectHelper(event, link);
    }
}