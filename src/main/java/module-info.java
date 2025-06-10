module mx.edu.utez.vista {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens mx.edu.utez.controller to javafx.fxml;
    exports mx.edu.utez.controller;
}