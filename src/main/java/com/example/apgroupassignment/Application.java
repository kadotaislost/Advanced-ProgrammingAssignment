package com.example.apgroupassignment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application{
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        registerScene();
    }

    public void registerScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 400);

        stage.setTitle("Register!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
