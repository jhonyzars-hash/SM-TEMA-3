package com.example.ejercicio3variablesdiscretas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("calculador-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 520);
        stage.setTitle("Cálculo de Probabilidades Discretas - Proyecto 3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}