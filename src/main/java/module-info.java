module com.zero.safpswithorder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zero.safpswithorder to javafx.fxml;
    exports com.zero.safpswithorder;
}