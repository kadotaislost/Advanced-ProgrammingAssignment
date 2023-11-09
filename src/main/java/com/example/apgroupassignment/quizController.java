package com.example.apgroupassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class quizController {
    private List<String> questions;

    @FXML
    private Label question;
    @FXML
    private RadioButton opt1,opt2,opt3,opt4;

    @FXML
    private Button prev, next;

    @FXML
    private Label name,gender,date,timer;

    private int currentQuestionIndex = 0;


    @FXML
    private void initialize() {
        loadQuestions();
        showNextQuestion();
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/mcq.txt");
            if (inputStream != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    StringBuilder questionBuilder = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.endsWith("?")) {
                            // If the line ends with a question mark, it's the end of a question.
                            questionBuilder.append(line);  // Append the question
                            questions.add(questionBuilder.toString());
                            questionBuilder.setLength(0);  // Clear the builder for the next question
                        } else {
                            questionBuilder.append(line).append("\n");  // Append lines of the question
                        }
                    }
                }
            } else {
                System.err.println("mcq.txt not found in resources folder.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            String currentQuestion = questions.get(currentQuestionIndex);
            question.setText( questions.get(17));
            currentQuestionIndex++;
        } else {
            // Handle the case when all questions have been displayed
            question.setText("No more questions.");
        }
    }


}
