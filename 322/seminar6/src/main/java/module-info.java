module org.example.seminar6_322_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.seminar6_322_1 to javafx.fxml;
    exports org.example.seminar6_322_1;
}