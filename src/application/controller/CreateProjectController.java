package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class CreateProjectController implements Initializable{

    @FXML
    private TextField name;
    @FXML
    private DatePicker dateCreated;
    @FXML 
    private TextArea description;
    
    @FXML
    private void createProject(ActionEvent event) {
    	DatabaseController databaseController = new DatabaseController();
    	databaseController.insertProject(name, dateCreated, description);
    	
        String link = "view/Main.fxml";
        MainController mainController = new MainController();
        mainController.redirectHelper(event, link);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the default value of the DatePicker in the initialize method
        dateCreated.setValue(LocalDate.now()); // You can set any default date you want
    }
}
