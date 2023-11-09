package com.example.apgroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class quizController {
    private List<String[]> questionsWithOptions;
    private int currentQuestionIndex = 0;

    @FXML
    private Label question;
    @FXML
    private RadioButton opt1, opt2, opt3, opt4;

    @FXML
    private ToggleGroup optionsGroup;

    @FXML
    private Button prev, next;

    @FXML
    private Label name, gender, date, timer;

    @FXML
    private void initialize() {
        loadQuestions();
        showNextQuestion();
    }

    private void loadQuestions() {
        questionsWithOptions = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/mcq.txt");
            if (inputStream != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] questionWithOptions = line.split("\\|");
                        questionsWithOptions.add(questionWithOptions);
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
        if (currentQuestionIndex < questionsWithOptions.size()) {
            String[] currentQuestion = questionsWithOptions.get(currentQuestionIndex);

            question.setText(currentQuestion[0]);
            opt1.setText(currentQuestion[1]);
            opt2.setText(currentQuestion[2]);
            opt3.setText(currentQuestion[3]);
            opt4.setText(currentQuestion[4]);

            currentQuestionIndex++;
        } else {
            // Handle the case when all questions have been displayed
            question.setText("No more questions.");
        }
    }

    public void handlePrevButton(ActionEvent actionEvent) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showCurrentQuestion();
        } else {
            // Handle the case when there are no previous questions
            System.out.println("No previous question available.");
        }
    }

    public void handleNextButton(ActionEvent actionEvent) {
        showNextQuestion();
    }


    private void showCurrentQuestion() {
        String[] currentQuestion = questionsWithOptions.get(currentQuestionIndex);

        question.setText(currentQuestion[0]);
        opt1.setText(currentQuestion[1]);
        opt2.setText(currentQuestion[2]);
        opt3.setText(currentQuestion[3]);
        opt4.setText(currentQuestion[4]);
    }


}

