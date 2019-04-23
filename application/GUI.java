package Final_project.application;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class GUI {
  // Default, no argument constructor
  public GUI() {
    
  }
  
  public Scene MainGUI() {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root,1200,600);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    
    // Main GUI Label
    Label mainLabel = new Label("Quiz Generator");
    mainLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 150));
    mainLabel.setMinWidth(1000);
    mainLabel.setMaxHeight(200);
    mainLabel.setTextFill(Color.DARKRED);
    root.setTop(mainLabel);
    root.setAlignment(mainLabel, Pos.TOP_CENTER);
    
    // Left Side VBox
    
    // Main Label for Left Side
    Label makeQuiz = new Label("Make a Quiz:");
    makeQuiz.setFont(Font.font("Arial", FontWeight.BOLD, 50));
    makeQuiz.setStyle("-fx-underline: true");
    makeQuiz.setTextFill(Color.DARKRED);
    
    // Topic Selection Label for Left Side
    Label topicSelection = new Label("Topic Selection:");
    topicSelection.setFont(new Font("Arial", 30));
    
    // Combo Box
    String topicChoices[] = {"Hash Table", "RBT", "AVL Tree", "Graphs"};
    
    ComboBox<String> choicesDropDown = new ComboBox<String>(FXCollections.observableArrayList(topicChoices));
    
    // Add choices button
    Button addAChoice = new Button("Add Topic to Quiz Breadth");
    
    // 
    Label numberOfQuestions = new Label("# of Questions for this Quiz:");
    numberOfQuestions.setFont(new Font("Arial", 30));
    
    // Textfield
    TextField txt = new TextField();
    
    // Generate Quiz Button
    Button generateQuiz = new Button("Generate Quiz");
    
    VBox leftSide = new VBox(20, makeQuiz, topicSelection, choicesDropDown, addAChoice, numberOfQuestions, txt, generateQuiz);
    leftSide.setAlignment(Pos.TOP_CENTER);
    root.setLeft(leftSide);
    root.setMargin(root.getLeft(), new Insets(30,0,0,120));
    
    // Right Side VBox
    
    // Main Label for Right Side
    Label addQuestion = new Label("Add a Question:");
    addQuestion.setFont(Font.font("Arial", FontWeight.BOLD, 50));
    addQuestion.setStyle("-fx-underline: true");
    addQuestion.setTextFill(Color.DARKRED);
    
    // Label for By File
    Label byFile = new Label("By File:");
    byFile.setFont(new Font("Arial", 30));
    
    // Add File Button
    Button addFile = new Button("Add File");
    
    // Label for or
    Label or = new Label("Or");
    or.setFont(new Font("Arial", 30));
    
    Button enterQuestion = new Button("Type in your Own Question");
    
    VBox rightSide = new VBox(20, addQuestion, byFile, addFile, or, enterQuestion);
    rightSide.setAlignment(Pos.TOP_CENTER);
    root.setRight(rightSide);
    // Insets(top, right, bottom, left)
    root.setMargin(root.getRight(), new Insets(30,120,0,0));
    
    return scene;
  }
  
}
