package Final_project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GUI extends Application {
  Stage stage = new Stage();
  JSON jsonHelper = new JSON();
  ArrayList<Question> quiz;
  QuestionDatabase questionBank = new QuestionDatabase();
  ArrayList<String> topics = new ArrayList<String>();
  ArrayList<String> topicsForThisQuiz; // Keeps track off the topics requested for this specific quiz
  int quizLength = 0;
  ArrayList<QuestionNode> questionGUI;

  // Default, no argument constructor
  public GUI() {
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      //jsonHelper.JSONReader("questions_001.json");
      primaryStage = stage;
      primaryStage.setTitle("Quiz Generator");
      primaryStage.setScene(MainGUI());
      primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,
          this::closeWindowEvent);
      primaryStage.show();

    } catch (Exception e) {
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  public Scene MainGUI() {
    topicsForThisQuiz = new ArrayList<String>();
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1200, 650);
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

    topics = questionBank.getAllTopics();
    ComboBox<String> choicesDropDown =
        new ComboBox<String>(FXCollections.observableArrayList(topics));
    
    // Add choices button
    Button addAChoice = new Button("Add Topic to Quiz Breadth");
    addAChoice.setOnMouseClicked(e -> addTopicToQuiz(choicesDropDown));

    //
    Label numberOfQuestions = new Label("# of Questions for this Quiz:");
    numberOfQuestions.setFont(new Font("Arial", 30));

    // Textfield
    TextField txt = new TextField();
    
 // Display the total number of questions available
    Label totalQuestions = new Label(
        "(Total Number of Questions Available: " + questionBank.getAllQuestions().size() + ")");
    totalQuestions.setFont(new Font("Arial", 15));

    // Generate Quiz Button
    Button generateQuiz = new Button("Generate Quiz");
    generateQuiz.setOnMouseClicked(e -> generateQuiz(txt));

    VBox leftSide = new VBox(20, makeQuiz, topicSelection, choicesDropDown, addAChoice,
        numberOfQuestions, totalQuestions, txt, generateQuiz);
    leftSide.setAlignment(Pos.TOP_CENTER);
    root.setLeft(leftSide);
    root.setMargin(root.getLeft(), new Insets(30, 0, 0, 120));

    // Right Side VBox

    // Main Label for Right Side
    Label addQuestion = new Label("Add a Question:");
    addQuestion.setFont(Font.font("Arial", FontWeight.BOLD, 50));
    addQuestion.setStyle("-fx-underline: true");
    addQuestion.setTextFill(Color.DARKRED);

    // Label for By File
    Label byFile = new Label("By File:");
    byFile.setFont(new Font("Arial", 30));

    // Text for Adding File
    TextField addFileTxt = new TextField();
    
    // Add File Button
    Button addFile = new Button("Add File");
    
    // If the add file button is clicked, the file typed in is attempted to be added to quiz
    addFile.setOnMouseClicked(e -> addFileToQuiz(addFileTxt));

    // Label for or
    Label or = new Label("Or");
    or.setFont(new Font("Arial", 30));

    Button enterQuestion = new Button("Type in your Own Question");
    enterQuestion.setOnMouseClicked(e -> this.stage.setScene(AddQuestionGUI()));

    VBox rightSide = new VBox(20, addQuestion, byFile, addFileTxt, addFile, or, enterQuestion);
    rightSide.setAlignment(Pos.TOP_CENTER);
    root.setRight(rightSide);
    // Insets(top, right, bottom, left)
    root.setMargin(root.getRight(), new Insets(30, 120, 0, 0));

    return scene;
  }

  public Scene EndQuizGUI(int score, int question) {
    BorderPane root = new BorderPane();
    BorderPane bottomPane = new BorderPane();
    root.setBottom(bottomPane);
    Scene scene = new Scene(root, 400, 400);

    Label finalScore = new Label("Final Score: " + score + "/" + quiz.size());
    finalScore.setTextFill(Color.DARKRED);
    finalScore.setFont(Font.font("Arial", FontWeight.BOLD, 30));
    
    Double percent = ((double) score) / ((double) quiz.size()) * 100;
    DecimalFormat df = new DecimalFormat("###.#");
    Label finalPercentage = new Label("Percentage: " + df.format(percent) + "%");
    finalPercentage.setTextFill(Color.DARKRED);
    finalPercentage.setFont(Font.font("Arial", FontWeight.BOLD, 30));
    
    VBox scores = new VBox(20, finalScore, finalPercentage);
        
    root.setCenter(scores);
    scores.setAlignment(Pos.CENTER);

    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    Button newButton = new Button("Done");
    newButton.setOnAction(e -> stage.setScene(MainGUI()));
    bottomPane.setCenter(newButton);
    newButton.setAlignment(Pos.TOP_CENTER);

    return scene;
  }

  public Scene QuizGUI(Question q, Button next, ComboBox<String> answers, int number) {
    BorderPane root = new BorderPane();

    Scene scene = new Scene(root, 1200, 600);
    BorderPane topPane = new BorderPane();
    root.setTop(topPane);
    topPane.setPadding(new Insets(20, 50, 0, 50));
    Label lab = new Label("Quiz: Question " + number + " of " + quiz.size());
    lab.setFont(Font.font("", FontWeight.BOLD, 30));
    lab.setTextFill(Color.DARKRED);
    topPane.setCenter(lab);
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(50, 50, 50, 50));
    root.setCenter(grid);

    Label quesLab = new Label(q.getQuestion());
    quesLab.setFont(new Font("", 30));
    quesLab.setWrapText(true);
    BorderPane centerPane = new BorderPane();
    root.setCenter(centerPane);
    centerPane.setTop(quesLab);
    centerPane.setPadding(new Insets(50, 50, 50, 50));
    centerPane.setCenter(grid);

    if (!q.getImageFileName().equals("none")) {
      try {
          FileInputStream input = new FileInputStream(q.getImageFileName());
          Image img = new Image(input);
          ImageView display = new ImageView(img);
          display.setFitWidth(200);
          display.setFitHeight(200);
          centerPane.setRight(display);
      } catch (Exception e) {
          Alert badAlarm = new Alert(AlertType.WARNING);
          badAlarm.setHeaderText("Problems Loading Image");
          badAlarm.setContentText("The image for the current question couldn't be loaded due "
                  + "to the file not being found");
      }
    }
    
    Label ansLab = new Label("Answer: ");
    ansLab.setTextFill(Color.DARKRED);
    ansLab.setFont(new Font("", 30));
    grid.setPadding(new Insets(100, 0, 0, 0));
    grid.setVgap(30);
    grid.add(ansLab, 0, 0);

    String tempAns[] = {"A", "B", "C", "D"}; // create a drop down menu to the left
    ComboBox<String> ans = answers; //new ComboBox<String>(FXCollections.observableArrayList(tempAns));
    grid.add(ans, 1, 0);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    HBox bottomGrid = new HBox(250);
    ColumnConstraints col = new ColumnConstraints();
    col.setHalignment(HPos.CENTER);
    root.setBottom(bottomGrid);
    bottomGrid.setPadding(new Insets(20, 20, 20, 20));
    bottomGrid.setPrefWidth(100);
    bottomGrid.setPrefHeight(50);

    Button nextButton = next;
    Button menuButton = new Button("Main Menu");
    menuButton.setOnMouseClicked(e -> this.stage.setScene(MainGUI()));

    nextButton.setMinWidth(bottomGrid.getPrefWidth());
    nextButton.setMinHeight(bottomGrid.getPrefHeight());
    menuButton.setMinWidth(bottomGrid.getPrefWidth());
    menuButton.setMinHeight(bottomGrid.getPrefHeight());

    bottomGrid.getChildren().add(nextButton);
    nextButton.setAlignment(Pos.CENTER);
    bottomGrid.getChildren().add(menuButton);
    menuButton.setAlignment(Pos.CENTER);

    return scene;
  }

  public Scene AddQuestionGUI() {
    // Top add question label
    Label addQuestion = new Label("Add Question");
    addQuestion.setFont(Font.font("Arial", FontWeight.BOLD, 90));
    addQuestion.setTextFill(Color.DARKRED);
    addQuestion.setAlignment(Pos.TOP_CENTER);
    addQuestion.setPadding(new Insets(0, 0, 15, 0));

    // Question text box
    Label questionLabel = new Label("Question: ");
    questionLabel.setFont(new Font("Arial", 30));
    questionLabel.setTextFill(Color.DARKRED);
    TextField newQuestionTxt = new TextField();

    HBox enterNewQuestion = new HBox(questionLabel, newQuestionTxt);
    enterNewQuestion.setAlignment(Pos.CENTER);
    enterNewQuestion.setPadding(new Insets(10, 0, 0, 0));

    // Topic text box
    Label topicLabel = new Label("Topic: ");
    topicLabel.setTextFill(Color.DARKRED);
    topicLabel.setFont(new Font("Arial", 30));
    TextField topicTxt = new TextField();

    HBox enterNewTopic = new HBox(topicLabel, topicTxt);
    enterNewTopic.setAlignment(Pos.CENTER);
    enterNewTopic.setPadding(new Insets(20, 0, 20, 0));

    // Options and text boxes
    Label optionsLabel = new Label("Options:");
    optionsLabel.setTextFill(Color.DARKRED);
    optionsLabel.setFont(new Font("Arial", 30));

    TextField option1 = new TextField("Option 1");
    option1.setAlignment(Pos.CENTER);
    TextField option2 = new TextField("Option 2");
    option2.setAlignment(Pos.CENTER);
    TextField option3 = new TextField("Option 3");
    option3.setAlignment(Pos.CENTER);
    TextField option4 = new TextField("Option 4");
    option4.setAlignment(Pos.CENTER);
    TextField option5 = new TextField("Option 5");
    option5.setAlignment(Pos.CENTER);

    // Answer text box
    Label answerLabel = new Label("Answer: ");
    answerLabel.setTextFill(Color.DARKRED);
    answerLabel.setFont(new Font("Arial", 30));
    TextField answerTxt = new TextField();

    HBox enterAnswer = new HBox(answerLabel, answerTxt);
    enterAnswer.setAlignment(Pos.CENTER);
    enterAnswer.setPadding(new Insets(20, 0, 20, 0));

    // Submit Button
    Button mainMenu = new Button("Main Menu");
    mainMenu.setFont(new Font("Arial", 20));
    Button submitButton = new Button("Submit");
    submitButton.setFont(new Font("Arial", 20));
    mainMenu.setOnMouseClicked(e -> this.stage.setScene(MainGUI()));
    submitButton.setOnMouseClicked(e -> addOwnQuestion(newQuestionTxt, topicTxt, option1, option2, option3, option4, option5, answerTxt));

    // Blank HBox's to space things out
    HBox blank1 = new HBox();
    blank1.setPadding(new Insets(20, 0, 10, 0));
    HBox blank2 = new HBox();
    blank2.setPadding(new Insets(10, 0, 10, 0));
    HBox blank3 = new HBox();
    blank3.setPadding(new Insets(10, 0, 20, 0));
    HBox blank4 = new HBox();
    blank4.setPadding(new Insets(10, 0, 20, 0));
    HBox blank5 = new HBox();
    blank5.setPadding(new Insets(10, 0, 10, 0));
    
    HBox buttons = new HBox(20, mainMenu, submitButton);
    buttons.setAlignment(Pos.CENTER);

    VBox root = new VBox(addQuestion, enterNewQuestion, enterNewTopic, optionsLabel, blank5, option1,
        blank1, option2, blank2, option3, blank3, option4, blank4, option5, enterAnswer, buttons);
    root.setAlignment(Pos.CENTER);
    VBox.setMargin(option1, new Insets(0, 200, 0, 200));
    VBox.setMargin(option2, new Insets(0, 200, 0, 200));
    VBox.setMargin(option3, new Insets(0, 200, 0, 200));
    VBox.setMargin(option4, new Insets(0, 200, 0, 200));
    VBox.setMargin(option5, new Insets(0, 200, 0, 200));

    Scene scene = new Scene(root, 1100, 700);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }
  
  public void addTopicToQuiz(ComboBox<String> c) {
    if(c.getValue() == null || c.getValue().equals("")) {
      Alert badAlert = new Alert(AlertType.ERROR);
      badAlert.setHeaderText("No Topic Selected");
      badAlert.setContentText("Be sure a topic is selected before adding to quiz breadth");
      badAlert.showAndWait();
      return;
    }
    this.topicsForThisQuiz.add(c.getValue());
    c.getItems().remove(c.getValue());
    c.setValue("");
  }
  
  private void getQuizSize(TextField t) {
    this.quizLength = Integer.parseInt(t.getText());
    int maxQuestions = 0;
    for(int i = 0; i < this.topicsForThisQuiz.size(); i++) {
      maxQuestions += this.questionBank.get(this.topicsForThisQuiz.get(i)).size();
    }
    // If too many questions are requested for the given topics, tell how many can be requested
    if(maxQuestions < this.quizLength) {
      Alert badAlert = new Alert(AlertType.ERROR);
      badAlert.setContentText("You requested to many questions for the Databse to fulfill based on you topics, the database only "
          + "has " + maxQuestions + " questions for the specified topics. Quiz will be run on maximum available");
      badAlert.setHeaderText("Too Many Questions Requested");
      badAlert.showAndWait();
      this.quizLength = maxQuestions;
    }
  }
  
  public void generateQuiz(TextField t) {
    quiz = new ArrayList<Question>();
    questionGUI = new ArrayList<QuestionNode>();

    try {
      getQuizSize(t);
      LinkedList<Question> visited = new LinkedList<Question>();
      int score = 0;
      // Make an array list with all of the possible questions from the available topics

      ArrayList<Question> possibleQuestionsForQuiz = new ArrayList<Question>();

      for(int i = 0; i < topicsForThisQuiz.size(); ++i) {
        String currTopic = topicsForThisQuiz.get(i);
        ArrayList<Question> holder = questionBank.get(currTopic);
        for(int j = 0; j < holder.size(); ++j) {
          possibleQuestionsForQuiz.add(holder.get(j));
        }

      }

      Random rand = new Random();
      int QuestionsInQuiz = 0;

      // Randomly add questions to quiz, marking visited as such, while you have less questions

      // then requested

      while(QuestionsInQuiz < quizLength) {
        int holder = Math.abs(rand.nextInt()) % possibleQuestionsForQuiz.size();
        Question currQuestion = possibleQuestionsForQuiz.get(holder);

        // If the question is already in the quiz, don't add it and look for another

        if(visited.contains(currQuestion)) {
          continue;
        }

        // If question isn't in quiz, add it, add it to visited, and increment number in quiz
        else {
          quiz.add(currQuestion);
          visited.add(currQuestion);
          QuestionsInQuiz++;
        }

      }

      // Make all questions into Question Nodes so they can be displayed

      for(int i = 0; i < this.quiz.size(); i++) {
        QuestionNode curr = new QuestionNode(quiz.get(i), new Button("Next"));
        this.questionGUI.add(curr);
        curr.setDisplay(this.QuizGUI(curr.getQuestion(), curr.getNextButton(), curr.getAnswers(), i + 1));

      }

      runQuiz(questionGUI.get(0).getNextButton(), 0, questionGUI.get(0).getAnswers(), score);

    }
    catch(NumberFormatException n) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Quiz input invalid");;
      errorAlert.setContentText("Please make sure that number of questions input is a whole number");
      errorAlert.showAndWait();
    }

    catch(Exception e) {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Quiz input invalid");;
      errorAlert.setContentText("Please make sure that number of questions input is a whole number and "
          + "topics have been selected");
      errorAlert.showAndWait();
    }

  }
 
  public void runQuiz(Button next, int question, ComboBox<String> answers, int score) {
    this.stage.setScene(questionGUI.get(question).getDisplay());
    next.setOnMouseClicked(e -> 
        checkAnswer(questionGUI.get(question).getQuestion().getSolution(), answers, question, score)); 
    
  }

  // (Works)This method adds Questions to the quiz based on the file typed in by the user when
  // they click AddFile on the MainGUI
  public void addFileToQuiz(TextField fileToAdd) {
    // First, check to make sure the file isn't null and empty
    if(fileToAdd.getText() != null && !fileToAdd.getText().isEmpty()) {
      ArrayList<Question> questionsToInsert = new ArrayList<Question>();
      String file = fileToAdd.getText();
      try {
        // Read the given file then attempt to insert it into the QuestionBank
        questionsToInsert = jsonHelper.JSONReader(file);
        questionBank.addQuestionsList(questionsToInsert);
        Alert goodAlert = new Alert(AlertType.CONFIRMATION);
        goodAlert.setHeaderText("File Succesfully Added");
        goodAlert.setContentText("The contents of your file were added to the QuestionDatabase");
        goodAlert.showAndWait();
        stage.setScene(MainGUI());
      }
      // If an exception is caught, display an error message as file was incorrect in some way
      catch(Exception e) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("File is not Valid");
        errorAlert.setContentText("Your provided file resulted in an exception");
        errorAlert.showAndWait();
      } 
    }
    // If there is no input, display an error message
    else {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("File is not Valid");
      errorAlert.setContentText("You failed to provide a usable file");
      errorAlert.showAndWait();
    }
  }

  // (Works)This method adds the users own Question to the quiz database based on the inputs they provided
  public void addOwnQuestion(TextField question, TextField topic, TextField option1, TextField option2, TextField option3,
      TextField option4, TextField option5, TextField solution) {
    
    // If all the fields are filled in, make a new question and add it to the database
     if(!(question.getText().isEmpty() || topic.getText().isEmpty() || option1.getText().isEmpty() || 
        option2.getText().isEmpty() || option3.getText().isEmpty() || option4.getText().isEmpty() || 
        option5.getText().isEmpty() || solution.getText().isEmpty()) && 
        (solution.getText().equals(option1.getText()) == true || 
        solution.getText().equals(option2.getText()) == true ||
        solution.getText().equals(option3.getText()) == true || 
        solution.getText().equals(option4.getText()) == true ||
        solution.getText().equals(option5.getText()) == true)) {
      
      // Add  all the options to an ArrayList
      ArrayList<String> options = new ArrayList<String>(Arrays.asList(option1.getText(), option2.getText(), option3.getText(), option4.getText(), option5.getText()));
      
      // Make a new instance of a topic with all the pertinent values
      Question toAdd = new Question(topic.getText(), "none", question.getText(), options, solution.getText());
      
      // Add the Question to the QuestionBank
      questionBank.addQuestions(toAdd);
      Alert goodAlert = new Alert(AlertType.CONFIRMATION);
      goodAlert.setHeaderText("Succesful Addition of Question");
      goodAlert.setContentText("The Question was succesfully added to the database");
      goodAlert.showAndWait();
      this.stage.setScene(MainGUI());
    }
    // Display an error message if any inputs are not valid
    else {
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Question is not Valid");;
      errorAlert.setContentText("Please fill out all available fields and make sure that the "
          + "correct answer is one of the filled out options");
      errorAlert.showAndWait();
    }
  }
  
  public void checkAnswer(String solution, ComboBox<String> answers, int question, int score) {
    try {
      if(question == quiz.size() - 1) {
        if(answers.getValue().equals(solution)) {
          Alert correctAlert = new Alert(AlertType.CONFIRMATION);
          correctAlert.setContentText("Answer correct!");
          correctAlert.showAndWait();
          score++;
          this.stage.setScene(EndQuizGUI(score, question));
        } else {
          Alert wrongAlert = new Alert(AlertType.ERROR);
          wrongAlert.setContentText("Answer wrong. The correct answer is " + solution);
          wrongAlert.showAndWait();
          this.stage.setScene(EndQuizGUI(score, question));
        }
      }   
     
      else if(answers.getValue().equals(solution)) {
        Alert correctAlert = new Alert(AlertType.CONFIRMATION);
        correctAlert.setContentText("Answer correct!");
        correctAlert.showAndWait();
        score++;
        runQuiz(questionGUI.get(question + 1).getNextButton(), question + 1, questionGUI.get(question + 1).getAnswers(), score);
      } else {
        Alert wrongAlert = new Alert(AlertType.ERROR);
        wrongAlert.setContentText("Answer wrong. The correct answer is " + solution);
        wrongAlert.showAndWait();
        runQuiz(questionGUI.get(question + 1).getNextButton(), question + 1, questionGUI.get(question + 1).getAnswers(), score);
      }
   
    } catch(Exception e) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setContentText("Please enter an answer.");
        errorAlert.showAndWait();
      }
  }

  /**
   * This method will display the final GUI that will ask the user if they want to save all the
   * current questions to a JSON file
   * 
   * @param event
   */
  public void closeWindowEvent(WindowEvent event) {
    Stage finalStage = new Stage();
    
    //ask user if they want to save
    Label askToSave = new Label("Before you exit, would you like to save all current questions to a .JSON file?");
    askToSave.setFont(Font.font("Arial", FontWeight.BOLD, 25));
    askToSave.setTextFill(Color.DARKRED);
    
    Label explainSave = new Label("If so, enter a filename below: ");
    explainSave.setFont(new Font("Arial", 20));
    explainSave.setTextFill(Color.DARKRED);
    
    //yes and no buttons
    Button yes = new Button("Save");
    yes.setMinWidth(100);
    Button no = new Button("Exit without Save");
    no.setMinWidth(200);
    HBox options = new HBox(yes, no);
    HBox.setMargin(yes, new Insets(0, 50, 0, 0));
    
    //HBox spacing = new HBox();
    // Textfield
    TextField file = new TextField();
    file.setMaxWidth(250);
    
    VBox root = new VBox(10, askToSave, explainSave, file, options);
    root.setAlignment(Pos.CENTER);
    VBox.setMargin(options, new Insets(30, 400, 0, 400));
    
    Scene scene = new Scene(root, 1000, 200);
    finalStage.setScene(scene);
    finalStage.show();

    // controls what happens when yes or no is clicked
    yes.setOnMouseClicked(e -> {
      try {
        finalStage.close();
        jsonHelper.JSONWriter(questionBank.getAllQuestions(), true, file);
        finalJSON();
      }
      catch (Exception e1) {
        Alert badAlert = new Alert(AlertType.ERROR);
        badAlert.setContentText("Make sure you've chosen a valid FileName");
        badAlert.setHeaderText("File Save Error");
        badAlert.showAndWait();
      }
    });
    no.setOnMouseClicked(e -> {
      finalStage.close();
      confirmChoiceNoSave();
    });
    
  }
  
  private void finalJSON() {
    Stage finalStage = new Stage();
    Label saving = new Label("Saving file to .JSON... Please Wait");
    saving.setFont(new Font("Arial", 30));
    saving.setAlignment(Pos.CENTER);
    Scene savingScene = new Scene(saving, 1000, 200);
    finalStage.setScene(savingScene);
    finalStage.show();
    
    PauseTransition delay = new PauseTransition(Duration.seconds(1));
    delay.setOnFinished(finishedEvent -> finalStage.close());
    delay.play();
  }
  
  private void confirmChoiceNoSave() {
    Stage confirmChoice = new Stage();
    Label exitWithoutSave = new Label("Are you sure you want to exit without saving?");
    exitWithoutSave.setFont(Font.font("Arial", 20));
    exitWithoutSave.setTextFill(Color.DARKRED);
    Button yes = new Button("Yes");
    Button no = new Button("No");
    HBox buttons = new HBox(10, yes, no);
    buttons.setAlignment(Pos.CENTER);
    VBox toPresent = new VBox(10, exitWithoutSave, buttons);
    Scene confirmingScene = new Scene(toPresent, 500, 100);
    toPresent.setAlignment(Pos.CENTER);
    confirmChoice.setScene(confirmingScene);
    confirmChoice.show();
    
    yes.setOnMouseClicked(e -> confirmChoice.close());
    
    no.setOnMouseClicked(e -> {
      confirmChoice.close();
      stage.setScene(MainGUI());
      stage.show();
    });
  }
 
}

