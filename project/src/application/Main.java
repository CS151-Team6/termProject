package application;
	
import application.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			Scene scene = new Scene(root,600,400);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			MainController mainController = new MainController();
			mainController.databaseOpenTest();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}