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
import java.util.ArrayList;
import java.util.List;


public class ResultController {

    public Label passOrFail;
    public Button analysis;
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
    private void viewStats(ActionEvent event) {
        // Load the new FXML file
        try{
            Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisstage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Statistical-Analytics-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
