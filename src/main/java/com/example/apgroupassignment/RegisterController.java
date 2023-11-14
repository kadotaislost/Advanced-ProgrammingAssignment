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
    private TextField userName;
    @FXML
    private TextField userEmail;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox<String> userGender;
    @FXML
    private TextField userNationality;
    @FXML
    private PasswordField userPassword;

    private Application application;

    public void setApplication(Application application){
        this.application = application;
    }
    public void register(){
        String name = userName.getText();
        String email = userEmail.getText();
        String birthYear = dateOfBirth.getValue().toString();
        String gender = userGender.getValue();
        String nationality = userNationality.getText();
        String password = userPassword.getText();

        if(verifyRegister()){
            try{
                FileWriter fileWriter = new FileWriter(pathToCSV, true);
                CSVWriter csvWriter = new CSVWriter(fileWriter);

                String[] csvData = {name, email, birthYear, gender, nationality, password};
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
        return checkAllFieldsFilled() && checkEmailRegisteredOrNot(userEmail.getText());
    }

    private boolean checkAllFieldsFilled() {
        String name = userName.getText();
        String email = userEmail.getText();
        String birthYear = dateOfBirth.getValue().toString();
        String gender = userGender.getValue();
        String nationality = userNationality.getText();
        String password = userPassword.getText();

        if(name.equals("") || email.equals("") || birthYear.equals("") || gender.equals("") || nationality.equals("") || password.equals("")){
            System.out.println("Please fill all the fields requested");
            return false;
        }
        return true;
    }

    private boolean checkEmailRegisteredOrNot(String email) {
        try (CSVReader reader = new CSVReader(new FileReader(pathToCSV))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (email.equals(line[1])) {
                    System.out.println("Email already registered");
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
