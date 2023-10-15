package application.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private Hyperlink hyperlink;
    @FXML
    private ListView<String> projectList;

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
    	DatabaseController dbController = new DatabaseController();
    	dbController.openDatabase();
    	dbController.createTable();
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	DatabaseController dbController = new DatabaseController();
    	ObservableList<String> projects = dbController.getAllProjects();
    	System.out.println(projects);
    	projectList.setItems(projects);
		
	}
}
