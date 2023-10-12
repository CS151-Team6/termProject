package application.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private void homeToNewProject(ActionEvent event) {
        // Handle the hyperlink click event here
        String link = "view/NewProject.fxml"; // Set your relative link here
        Parent newRoot = null;
		try {
			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource(link));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
    }
    
    void redirectHelper(ActionEvent event, String link) {
        Parent newRoot = null;
		try {
			newRoot = FXMLLoader.load(getClass().getClassLoader().getResource(link));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
    }
    
    public void databaseOpenTest() {
        try (Connection conn =
                DriverManager.getConnection("jdbc:sqlite:test.db");
            ) {
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
