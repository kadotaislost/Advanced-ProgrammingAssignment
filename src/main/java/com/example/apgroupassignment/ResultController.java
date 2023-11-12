package com.example.apgroupassignment;

import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.FileWriter;
import java.io.IOException;

public class ResultController {
    @FXML
    private Label marks;

    @FXML
    public void initialize() {

        marks.setText("You scored " + quizController.score +"/20");
        String pathToCSV = "src/main/resources/test_result.csv";
        try{
            FileWriter fileWriter = new FileWriter(pathToCSV, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            String[] csvData = {CurrentUserDetails.naam, CurrentUserDetails.linga, Integer.toString(quizController.score),CurrentUserDetails.email};
            csvWriter.writeNext(csvData);
            csvWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
