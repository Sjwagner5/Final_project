package application;
	

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application {
    GUI userInterface = new GUI();
    JSON jsonHelper = new JSON();
  
	@Override
	public void start(Stage primaryStage) {
		try {
		    //jsonHelper.JSONReader("questions_001.json");
		    primaryStage.setTitle("Quiz Generator");
			primaryStage.setScene(userInterface.AddQuestionGUI());
            primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
