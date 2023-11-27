package com.example.apgroupassignment;

import com.opencsv.CSVReader;
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

    private Application application;

    public void setApplication(Application application){
        this.application = application;
    }
    public void login() throws IOException {
        String email = userEmail.getText();
        String password = userPassword.getText();
        if (validateLogin(email, password)) {
            CurrentUserDetails.naam = line[0];;
            CurrentUserDetails.linga = line[3];
            CurrentUserDetails.birthYear = line[2];
            CurrentUserDetails.nationality = line[4];
            CurrentUserDetails.email = line[1];
            CurrentUserDetails.username = line[6];
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
                if (line.length == 7 && (line[1].equals(email) || line[6].equals(email)) && line[5].equals(password)){


                    return true;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public void registerScenebtn() throws IOException {
        application.registerScene();
    }
}
