package com.example.apgroupassignment;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class quizController {

    String[] answers = {
            "Kathmandu",
            "Cow",
            "Daura Suruwal",
            "Rhododendron",
            "Dhanyabad",
            "Maya Devi Temple",
            "Namaste",
            "Himalayan Monal(Danphe)",
            "Dal Bhat",
            "Momo",
            "Chitwan National Park",
            "Tribhuvan International Airport",
            "Tihar",
            "Kukur Tihar",
            "aila",
            "Buddha Jayanti",
            "Sarangi",
            "Rangoli",
            "lakhey Dance",
            "Dashain"
    };

    private String selectedAnswer;
    private int minutes = 5;
    private int seconds = 0;
    private Timeline timeline;

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
    private Label name, gender, date, timer, warn ;

    public int score = 0;

    @FXML
    private void initialize() {
        loadQuestions();
        showCurrentQuestion();
        initializeTimer();
    }

    public void startTimer() {
        timeline.play();
    }

    private void initializeTimer() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateTimer())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }


    private void updateTimer() {
        if (minutes == 0 && seconds == 0) {
            // Timer expired, handle accordingly
            timeline.stop();
        } else {
            if (seconds == 0) {
                minutes--;
                seconds = 59;
            } else {
                seconds--;
            }
            updateTimerLabel();
        }
    }

    private void updateTimerLabel() {
        timer.setText(String.format("%d:%02d", minutes, seconds));
    }

    private void loadQuestions() {
        questionsWithOptions = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/mcq.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            br.lines()
                    .map(line -> line.split("\\|"))
                    .forEach(questionsWithOptions::add);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentQuestion() {
        warn.setText("");
        if (currentQuestionIndex < questionsWithOptions.size()) {
            String[] currentQuestion = questionsWithOptions.get(currentQuestionIndex);

            question.setText(currentQuestion[0]);
            opt1.setText(currentQuestion[1]);
            opt2.setText(currentQuestion[2]);
            opt3.setText(currentQuestion[3]);
            opt4.setText(currentQuestion[4]);

        } else {
            // Handle the case when all questions have been displayed
            question.setText("No more questions.");
        }
    }

    @FXML
    public void handlePrevButton(ActionEvent actionEvent) {


        if (currentQuestionIndex > 0) {
            score--;
            currentQuestionIndex--;
            showCurrentQuestion();
        } else {
            // Handle the case when there are no previous questions
            System.out.println("No previous question available.");
        }
        updateTimerLabel();
    }

    private void checkAnswer(String selectedAnswer) {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < answers.length) {
            if (selectedAnswer.equals(answers[currentQuestionIndex])) {

                score++;
            }
        }
        System.out.println(score);
    }


    @FXML
    public void handleNextButton() {

        if (optionsGroup.getSelectedToggle() == null) {
            warn.setText("Please select an answer before moving to the next question.");
            return;
        }


        // Get the selected answer
        RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();
        selectedAnswer = selectedRadioButton.getText();

        // Check the selected answer with the correct answer
        checkAnswer(selectedAnswer);




        if (currentQuestionIndex < questionsWithOptions.size() - 1) {
            currentQuestionIndex++;
            showCurrentQuestion();
        } else {
            // Handle the case when all questions have been displayed
            try{
                Stage currentStage = (Stage) next.getScene().getWindow();
                currentStage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("results.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        optionsGroup.selectToggle(null);

        updateTimerLabel();
    }
}