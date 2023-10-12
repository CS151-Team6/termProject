package application.controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProjectController {

    @FXML
    private Hyperlink hyperlink;

    
    @FXML
    private void newProjectToHome(ActionEvent event) {
        String link = "view/Main.fxml";
        MainController mainController = new MainController();
        mainController.redirectHelper(event, link);
    }
}
