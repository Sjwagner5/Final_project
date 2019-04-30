package Final_project;

import java.util.ArrayList;

public class Question {
  private String topic;
  private String imageFileName;
  private String question;
  private ArrayList<String> choices;
  private String solution;
  
  public Question(String topic, String fileName, String question, ArrayList<String> choices, String solution) {
    this.topic = topic;
    this.question = question;
    this.choices = choices;
    this.solution = solution;
    this.imageFileName = fileName;
  }
  
  public String getQuestion() {
    return this.question;
  }
  
  public ArrayList<String> getChoices() {
    return this.choices;
  }
  
  public String getSolution() {
    return this.solution;
  }
  
  public String getTopic() {
    return this.topic;
  }
  
  public String getImageFileName() {
    return this.imageFileName;
  }
  
}
