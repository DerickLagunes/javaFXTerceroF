module mx.edu.utez.vista {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires ojdbc8;
    requires ucp;

    opens mx.edu.utez.modelo to javafx.fxml;
    opens mx.edu.utez.controller to javafx.fxml;
    exports mx.edu.utez.modelo;
    exports mx.edu.utez.controller;
}