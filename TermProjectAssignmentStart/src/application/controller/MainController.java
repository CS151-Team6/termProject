package application.controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private void handleLinkClick(ActionEvent event) {
        String link = "view/NewProject.fxml"; // Set your relative link here
        Stage stage = new Stage();
        try {
            // Parent newContent = loader.load();
			HBox root = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource(link));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}