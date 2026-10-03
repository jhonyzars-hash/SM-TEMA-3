module com.example.ejercicio2variablesdiscretas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.ejercicio2variablesdiscretas to javafx.fxml;
    exports com.example.ejercicio2variablesdiscretas;
}