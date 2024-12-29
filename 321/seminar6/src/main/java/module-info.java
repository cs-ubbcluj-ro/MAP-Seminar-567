module org.example.seminar6_321 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.seminar6_321 to javafx.fxml;
    exports org.example.seminar6_321;
}