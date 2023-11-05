module com.example.apgroupassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.apgroupassignment to javafx.fxml;
    exports com.example.apgroupassignment;
}