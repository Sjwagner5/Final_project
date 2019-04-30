package Final_project;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.io.FileReader;
import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSON {
   private ArrayList<Question> questionsFromFile;
   private Question toInsert;
   private String topic;
   private String imageFileName;
   private String question;
   private ArrayList<String> choices;
   private String solution;
   
   // Default no arg constructor
   public JSON() {
     
   }
  
   public ArrayList<Question> JSONReader(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
     questionsFromFile = new ArrayList<Question>();
     
     // Getting file
     FileReader jsonRead = new FileReader(jsonFilepath);
     
     // Parsing File
     Object obj = new JSONParser().parse(jsonRead);
     
     // Type Cast to JSONObject
     JSONObject jo = (JSONObject) obj;
     
     // Get the questions
     JSONArray packages = (JSONArray) jo.get("questionArray");
     
     // Make a new instance of a question from each Question in JSON
     for(int i = 0; i < packages.size(); ++i) {
       choices = new ArrayList<String>();
       
       // Get an individual question
       JSONObject singleQuestion = (JSONObject) packages.get(i);
       
       // Get the question text
       question = (String) singleQuestion.get("questionText");
       
       // Get the topic text
       topic = (String) singleQuestion.get("topic");
       
       // Get the image file name
       imageFileName = (String) singleQuestion.get("image");
       
       // Make an array of the choices for the question
       JSONArray choicesPackage = (JSONArray) singleQuestion.get("choiceArray");
       
       // Get the array of choices
       for(int j = 0; j < choicesPackage.size(); ++j) {
         // Make an object instance of a single choice
         JSONObject singleChoice = (JSONObject) choicesPackage.get(j);
         
         String possibleSolution = (String) singleChoice.get("isCorrect");
         
         // Check if the solution, if it is setting it to solution
         if(possibleSolution.equals("T")) {
           solution = ((String) singleChoice.get("choice"));
         }
         
         // Add all possible options for a question to the choices array
         choices.add((String) singleChoice.get("choice"));
       }
       
       // Make an instance of a question and add it to the question ArrayList
       toInsert = new Question(topic, imageFileName, question, choices, solution);
       questionsFromFile.add(toInsert);
     }
     
     return questionsFromFile;
   }
   
   /**
    * This method writes the user finished questions
    * 
    * @param quesList  a list of questions
    * @param save      if the user wants to save
    * @throws IOException  
    */
   public void JSONWriter(ArrayList<Question> quesList, boolean save) throws IOException {
       JSONObject outputFile = new JSONObject(); // the out most json
       JSONObject questions = new JSONObject(); // the questions json
       for (int i = 0; i < quesList.size(); i++) {
           questions.put("meta-data", "unused"); // add multiple fields
           questions.put("questionText", quesList.get(i).getQuestion());
           questions.put("topic", quesList.get(i).getTopic());
           questions.put("image", quesList.get(i).getImageFileName());
           JSONObject choices = new JSONObject();
           String correct = quesList.get(i).getSolution();
           for (int j = 0; j < quesList.get(i).getChoices().size(); j++) { // get all choices
               if (correct.equals(quesList.get(i).getChoices().get(j))) {
                   choices.put("isCorrect", "T");
                   continue;
               }
               choices.put("isCorrect", "F");
           }
           questions.put("choiceArray", choices);
       }
       outputFile.put("choiceArray", questions);
       FileWriter file = new FileWriter("save1.json");
       if (save) { // if user wants to save, write to a file
           file.write(outputFile.toJSONString());
           file.flush();
           file.close();
       }
       
       
   }
     
}
