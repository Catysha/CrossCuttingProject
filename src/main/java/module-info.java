module com.example.crosscuttingprojectapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.xml;

    opens com.example.crosscuttingprojectapplication to javafx.fxml; // Открываем пакет для JavaFX
    exports com.example.crosscuttingprojectapplication;
}