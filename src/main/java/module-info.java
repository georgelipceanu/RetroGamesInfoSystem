module com.example.retro {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;
            
                            
    opens com.example.retro to javafx.fxml,xstream;
    exports com.example.retro;

    exports com.example.retro.controllers;
    opens com.example.retro.controllers to javafx.fxml;

    exports com.example.retro.models;
    opens com.example.retro.models to javafx.fxml;
}