package com.example.variablesdiscretas;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    @FXML private ComboBox<String> comboDistribucion;
    @FXML private TextField txtParametro1;
    @FXML private TextField txtParametro2;
    @FXML private TextField txtCantidad;
    @FXML private TextArea txtResultados;
    @FXML private BarChart<String, Number> barChart;
    @FXML private Label lblParametro1;
    @FXML private Label lblParametro2;

    @FXML
    public void initialize() {
        comboDistribucion.getItems().addAll("Bernoulli", "Binomial", "Poisson");
        comboDistribucion.getSelectionModel().selectFirst();
        actualizarCampos();

        comboDistribucion.setOnAction(e -> actualizarCampos());
    }

    private void actualizarCampos() {
        String seleccion = comboDistribucion.getValue();
        if ("Bernoulli".equals(seleccion)) {
            lblParametro1.setText("Probabilidad (p):");
            txtParametro1.setPromptText("0.5");
            lblParametro2.setVisible(false);
            txtParametro2.setVisible(false);
        } else if ("Binomial".equals(seleccion)) {
            lblParametro1.setText("Ensayos (n):");
            txtParametro1.setPromptText("10");
            lblParametro2.setText("Probabilidad (p):");
            lblParametro2.setVisible(true);
            txtParametro2.setVisible(true);
            txtParametro2.setPromptText("0.5");
        } else if ("Poisson".equals(seleccion)) {
            lblParametro1.setText("Lambda (λ):");
            txtParametro1.setPromptText("3.0");
            lblParametro2.setVisible(false);
            txtParametro2.setVisible(false);
        }
    }

    @FXML
    protected void onGenerarButtonClick() {
        try {
            String distribucion = comboDistribucion.getValue();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            List<Integer> datos;

            if ("Bernoulli".equals(distribucion)) {
                double p = Double.parseDouble(txtParametro1.getText());
                datos = Generador.bernoulli(p, cantidad);
            } else if ("Binomial".equals(distribucion)) {
                int n = Integer.parseInt(txtParametro1.getText());
                double p = Double.parseDouble(txtParametro2.getText());
                datos = Generador.binomial(n, p, cantidad);
            } else {
                double lambda = Double.parseDouble(txtParametro1.getText());
                datos = Generador.poisson(lambda, cantidad);
            }

            mostrarResultados(datos);
            graficarFrecuencias(datos);

        } catch (Exception e) {
            txtResultados.setText("Error en los datos ingresados.");
        }
    }

    private void mostrarResultados(List<Integer> datos) {
        StringBuilder sb = new StringBuilder("Valores generados:\n");
        for (int i = 0; i < Math.min(datos.size(), 100); i++) {
            sb.append(datos.get(i)).append(", ");
        }
        if (datos.size() > 100) sb.append("...");
        txtResultados.setText(sb.toString());
    }

    private void graficarFrecuencias(List<Integer> datos) {
        barChart.getData().clear();
        Map<Integer, Integer> frecuencias = new HashMap<>();

        for (int val : datos) {
            frecuencias.put(val, frecuencias.getOrDefault(val, 0) + 1);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Frecuencias Observadas");

        frecuencias.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue())));

        barChart.getData().add(series);
    }
}