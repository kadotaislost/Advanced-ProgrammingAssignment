package com.example.apgroupassignment;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ResultController {

    public Label passOrFail;
    public Button analysis;

    @FXML
    public Label mean, median, mode, max, min, sd;
    @FXML
    private ChoiceBox<String> choiceBox;

    private String passFail;

    @FXML
    private Label marks, name, gender, error;

    @FXML
    public void initialize() {
        displayAndStoreResult();
        loadChoiceBox();
        choiceBox.setOnAction(event -> {
            String selectedName = choiceBox.getValue();
            displayMarks(selectedName);
        });
        viewStats();
    }

    private void displayMarks(String selectedName) {
        String csvPath = "src/main/resources/test_result.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<String[]> records = reader.readAll();

            for (String[] record : records) {
                if (record[0].equals(selectedName)) {
                    if (record[4].equals("Pass")) {
                        passOrFail.setStyle("-fx-background-color: #71c765");
                        passOrFail.setText("Pass");
                    } else {
                        passOrFail.setStyle("-fx-background-color: #a82a2a");
                        passOrFail.setText("Fail");
                    }

                    marks.setText(record[2] + "/20");
                    name.setText(record[0]);
                    gender.setText(record[1]);
                    return;
                }
            }

            // If the selected name is not found in the CSV
            error.setText("Could not load data for " + selectedName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChoiceBox() {
        String csvPath = "src/main/resources/test_result.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<String[]> records = reader.readAll();

            List<String> names = new ArrayList<>();
            for (String[] record : records) {
                names.add(record[0]);
            }

            // Populate the ChoiceBox with names
            choiceBox.setItems(FXCollections.observableArrayList(names));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAndStoreResult() {
        if (quizController.score >= 8) {
            passFail = "Pass";
            passOrFail.setStyle("-fx-background-color: #71c765");
            passOrFail.setText("Pass");
        } else {
            passOrFail.setStyle("-fx-background-color: #a82a2a");
            passFail = "Fail";
            passOrFail.setText("Fail");
        }
        marks.setText(quizController.score + "/20");
        name.setText(CurrentUserDetails.naam);
        gender.setText(CurrentUserDetails.linga);
        String pathToCSV = "src/main/resources/test_result.csv";
        try {
            FileWriter fileWriter = new FileWriter(pathToCSV, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            String[] csvData = {CurrentUserDetails.naam, CurrentUserDetails.linga, Integer.toString(quizController.score), CurrentUserDetails.email, passFail};
            csvWriter.writeNext(csvData);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
    private void viewStats() {
        String csvPath = "src/main/resources/test_result.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<Integer> scores = new ArrayList<>();

            List<String[]> records = reader.readAll();

            for (String[] record : records) {
                int score = Integer.parseInt(record[2]);
                scores.add(score);
            }

            // Mean
            double meanValue = calculateMean(scores);
            mean.setText(String.format("%.2f", meanValue));

            // Median
            double medianValue = calculateMedian(scores);
            median.setText(String.format("%.2f", medianValue));

            // Mode
            int modeValue = calculateMode(scores);
            mode.setText(Integer.toString(modeValue));

            // Max
            int maxValue = Collections.max(scores);
            max.setText(Integer.toString(maxValue));

            // Min
            int minValue = Collections.min(scores);
            min.setText(Integer.toString(minValue));

            // Standard Deviation
            double sdValue = calculateStandardDeviation(scores, meanValue);
            sd.setText(String.format("%.2f", sdValue));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private double calculateMean(List<Integer> scores) {
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    private double calculateMedian(List<Integer> scores) {
        List<Integer> sortedScores = scores.stream().sorted().collect(Collectors.toList());
        int size = sortedScores.size();
        if (size % 2 == 0) {
            int mid1 = sortedScores.get(size / 2 - 1);
            int mid2 = sortedScores.get(size / 2);
            return (double) (mid1 + mid2) / 2;
        } else {
            return sortedScores.get(size / 2);
        }
    }

    private int calculateMode(List<Integer> scores) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int score : scores) {
            frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
        }
        int modeValue = 0;
        int maxFrequency = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                modeValue = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return modeValue;
    }

    private double calculateStandardDeviation(List<Integer> scores, double mean) {
        double sum = scores.stream().mapToDouble(score -> Math.pow(score - mean, 2)).sum();
        double variance = sum / scores.size();
        return Math.sqrt(variance);
    }
}



