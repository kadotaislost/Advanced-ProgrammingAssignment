package com.example.apgroupassignment;

import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileReader;
import java.io.IOException;

public class LoginController {
    private String[] line;
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Label resultLabel;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    private String gender;

    private String name;

    private Application application;

    public void setApplication(Application application){
        this.application = application;
    }
    public void login(ActionEvent event) throws IOException {
        String email = userEmail.getText();
        String password = userPassword.getText();
        if (validateLogin(email, password)) {
            test.naam = line[0];;
            test.linga = line[3];
            test.birthYear = line[2];
            test.nationality = line[4];
            application.homeScene();
        }
        else {
            resultLabel.setText("Invalid Email or Password");
            resultLabel.getStyleClass().clear();
            resultLabel.getStyleClass().add("login-error");
        }
    }

    private boolean validateLogin(String email, String password) {
        String csvPath = "src/main/resources/userDetails.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {

            while ((line = reader.readNext()) != null){
                if (line.length == 6 && line[1].equals(email) && line[5].equals(password)){


                    return true;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public void registerScenebtn(ActionEvent event) throws IOException {
        application.registerScene();
    }
}
