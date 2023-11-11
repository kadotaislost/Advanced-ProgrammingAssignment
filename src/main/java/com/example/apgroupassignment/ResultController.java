package com.example.apgroupassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultController {
    @FXML
    private Label marks;

    public void setScore(int score) {
        marks.setText(score + "/20");
    }
}
