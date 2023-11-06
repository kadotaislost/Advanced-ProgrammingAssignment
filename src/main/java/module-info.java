module com.example.apgroupassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;


    opens com.example.apgroupassignment to javafx.fxml;
    exports com.example.apgroupassignment;
}