package com.example.apgroupassignment;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class RegisterController {

    public String pathToCSV = "src/main/resources/userDetails.csv";
    @FXML
    private TextField userName,user;
    @FXML
    private TextField userEmail;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<String> userGender;
    @FXML
    private TextField userNationality;
    @FXML
    private PasswordField userPassword,confirmPassword;

    @FXML
    private Label error;

    private Application application;

    public void setApplication(Application application){
        this.application = application;
    }
    public void register(){
        String name = userName.getText();
        String username = user.getText();
        String email = userEmail.getText();
        String birthYear = dateOfBirth.getValue().toString();
        String gender = userGender.getValue();
        String nationality = userNationality.getText();
        String password = userPassword.getText();

        if(verifyRegister()){
            try{
                FileWriter fileWriter = new FileWriter(pathToCSV, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter);

                String[] csvData = {name, email, birthYear, gender, nationality, password,username};
                csvWriter.writeNext(csvData);
                csvWriter.close();
                application.loginScene();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private boolean verifyRegister(){
        return checkAllFieldsFilled() && checkEmailRegisteredOrNot(userEmail.getText()) && checkPasswordMatched() && checkUsernameRegisteredOrNot(user.getText());
    }

    private boolean checkUsernameRegisteredOrNot(String username) {
        try (CSVReader reader = new CSVReader(new FileReader(pathToCSV))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (username.equals(line[6])) {
                    error.setText("Username already registered");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean checkPasswordMatched() {
        if (userPassword.getText().equals(confirmPassword.getText())) {
            return true;
        }
        error.setText("Password does not match");
        return false;
    }

    private boolean checkAllFieldsFilled() {
        String name = userName.getText();
        String email = userEmail.getText();
        String birthYear = dateOfBirth.getValue().toString();
        String gender = userGender.getValue();
        String nationality = userNationality.getText();
        String password = userPassword.getText();
        String confPwd = confirmPassword.getText();
        String username = user.getText();

        if(name.equals("") || email.equals("") || birthYear.equals("") || gender.equals("") || nationality.equals("") || password.equals("") || confPwd.equals("") || username.equals("")){
            error.setText("Please fill all the fields");
            return false;
        }
        return true;
    }

    private boolean checkEmailRegisteredOrNot(String email) {
        try (CSVReader reader = new CSVReader(new FileReader(pathToCSV))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (email.equals(line[1])) {
                    error.setText("Email already registered");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void loginScenebtn() throws IOException {
        application.loginScene();
    }

}
