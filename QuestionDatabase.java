package Final_project;

import java.util.ArrayList;

/**
 * This class stores all the questions. It contains a private class called TopicNode
 * 
 * @author simon
 *
 */
public class QuestionDatabase {

    /**
     * This private class is a node stored in topics arraylist. It has two fields: topic is
     * questions' topics; topicList contains a list of questions
     * 
     * @author simon
     *
     */
    private class TopicNode {
        // declare field for HashNode
        String topic = new String();
        ArrayList<Question> quesList = new ArrayList<Question>();

        // a constructor for HashNode, initializes the field
        private TopicNode(String topic) {
            this.topic = topic;
            this.quesList = new ArrayList<Question>();
        }
    }

    private ArrayList<TopicNode> topics; // stores a list of TopicNode

    /**
     * A constructor to initialize topics.
     */
    public QuestionDatabase() {
        this.topics = new ArrayList<TopicNode>();
    }

    /**
     * This method adds questions into an arrayList containing other questions that have the same
     * topic, and add the topic into the topics arraylist. If a duplicate question is being added,
     * this method returns without adding it again.
     * 
     * @param newQues
     */
    public void addQuestions(Question newQues) {
        int topicIndex = 0;
        if (topics.isEmpty()) {
            addTopic(newQues.getTopic()); // arraylist empty, add topic
        } else { // arraylist not empty, find if topic exists
            boolean topicExist = false;
            for (int i = 0; i < topics.size(); i++) {
                if (topics.get(i).topic.equals(newQues.getTopic())) {
                    topicExist = true;
                    topicIndex = i;
                    break;
                }
            }
            if (!topicExist) { // topic does not exist, create new topic
                addTopic(newQues.getTopic());
                topicIndex = topics.size() - 1;
            }
        }
        ArrayList<Question> quesList = topics.get(topicIndex).quesList; // get topic question list
        if (quesList.isEmpty()) { // no question in list, add
            quesList.add(newQues);
            return;
        }
        for (int i = 0; i < quesList.size(); i++) { // search for duplicate question
            if (quesList.get(i).getTopic().equals(newQues.getTopic())
                    && quesList.get(i).getChoices().equals(newQues.getChoices())
                    && quesList.get(i).getQuestion().equals(newQues.getQuestion())
                    && quesList.get(i).getImageFileName().equals(newQues.getImageFileName())
                    && quesList.get(i).getSolution().equals(newQues.getSolution())) {
                return;
            }
        }
        quesList.add(newQues); // not duplicate, add question
    }

    /**
     * This method adds an arraylist of questions to the topics arraylist.
     * 
     * @param newQuesList
     */
    public void addQuestionsList(ArrayList<Question> newQuesList) {
        for (int i = 0; i < newQuesList.size(); i++) {
            addQuestions(newQuesList.get(i));
        }
    }

    /**
     * This is a helper method to add new topic into the topics arrayList.
     * 
     * @param newTopic to be added
     */
    private void addTopic(String newTopic) {
        TopicNode newNode = new TopicNode(newTopic);
        topics.add(newNode);
    }

    /**
     * This method gets all the questions in a single topic.
     * 
     * @param topic is the topic of the question
     * @return a list of questions
     */
    public ArrayList<Question> get(String topic) {
        for (int i = 0; i < topics.size(); ++i) {
            if (topics.get(i).topic.equals(topic)) {
                return topics.get(i).quesList;
            }
        }
        return null;
    }

    /**
     * This method gets a list of topic lists which contains all questions
     * 
     * @return
     */
    public ArrayList<TopicNode> getList() {
        return topics;
    }
    
    /**
     * This method returns a list of questions for a single topic
     * @param topic is the topic requested
     * @return an arraylist of questions
     */
    public ArrayList<Question> getTopic(String topic) {
        ArrayList<Question> retList = new ArrayList<Question>();
        for (int i = 0; i < topics.size(); i++) {
            retList = topics.get(i).quesList;
        }
        return retList;
    }
    
    /**
     * This method prints all questions
     */
    public void printList() {
        for (int i = 0; i < topics.size(); i++) {
            for (int j = 0; j < topics.get(i).quesList.size(); j++) {
                System.out.println("Topic: " + topics.get(i).topic + " Question: "
                        + topics.get(i).quesList.get(j).getQuestion());
            }
        }
    }

    /**
     * This method returns a list of all questions
     * 
     * @return
     */
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> retList = new ArrayList<Question>();
        for (int i = 0; i < topics.size(); i++) {
            for (int j = 0; j < topics.get(i).quesList.size(); j++) {
                retList.add(topics.get(i).quesList.get(j));
            }
        }
        return retList;
    }
}
