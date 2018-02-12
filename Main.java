package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(getClass().getResource("/application/ListView.fxml"));
			AnchorPane root = (AnchorPane)loader.load();


			ListViewController listController = loader.getController();
			// Either find file here and pass through parameter
			// Or let listController find the file
			//But I think it would be best if the Main found the file
			File file = null;
			listController.start(primaryStage, file);

			Scene scene = new Scene(root,700,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Song Library");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
