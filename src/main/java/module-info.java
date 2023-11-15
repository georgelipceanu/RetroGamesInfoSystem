module com.example.retro {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.retro to javafx.fxml;
    exports com.example.retro;
}