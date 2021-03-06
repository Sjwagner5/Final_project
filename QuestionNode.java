package Final_project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class QuestionNode {
  private Button nextButton;
  private Question question;
  private Scene display;
  private ComboBox<String> answerChoices;
  
  public QuestionNode(Question q, Button next) {
    this.nextButton = next;
    this.question = q;
    answerChoices = new ComboBox<String>(FXCollections.observableArrayList(q.getChoices()));
  }
  
  public Button getNextButton() {
    return this.nextButton;
  }
  
  public void setDisplay(Scene s) {
    this.display = s;
  }
  
  public Scene getDisplay() {
    return this.display;
  }
  
  public Question getQuestion() {
    return this.question;
  }
  
  public String getAnswer() {
    return (String) this.answerChoices.getValue();
  }
  
  public ComboBox<String> getAnswers() {
    return this.answerChoices;
  }
}
