module com.example.retro {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;
            
                            
    opens com.example.retro to javafx.fxml,xstream;
    exports com.example.retro;
}