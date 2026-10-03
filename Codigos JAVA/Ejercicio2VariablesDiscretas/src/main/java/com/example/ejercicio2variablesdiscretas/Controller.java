package com.example.ejercicio2variablesdiscretas;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    @FXML private ComboBox<String> comboModelo;
    @FXML private TextField txtCampo1;
    @FXML private TextField txtCampo2;
    @FXML private TextField txtCampo3;
    @FXML private TextField txtMuestras;
    @FXML private Label lblCampo1;
    @FXML private Label lblCampo2;
    @FXML private Label lblCampo3;
    @FXML private TextArea txtSalida;
    @FXML private BarChart<String, Number> graficoBarras;

    @FXML
    public void initialize() {
        comboModelo.getItems().addAll("Geométrica (Fallas/Intentos)", "Hipergeométrica (Muestreo Defectos)");
        comboModelo.getSelectionModel().selectFirst();
        configurarInterfaz();

        comboModelo.setOnAction(e -> configurarInterfaz());
    }

    private void configurarInterfaz() {
        if (comboModelo.getSelectionModel().getSelectedIndex() == 0) {
            lblCampo1.setText("Probabilidad de éxito (p):");
            txtCampo1.setPromptText("0.2");
            lblCampo2.setVisible(false);
            txtCampo2.setVisible(false);
            lblCampo3.setVisible(false);
            txtCampo3.setVisible(false);
        } else {
            lblCampo1.setText("Población total (N):");
            txtCampo1.setPromptText("100");
            lblCampo2.setText("Éxitos en población (K):");
            lblCampo2.setVisible(true);
            txtCampo2.setVisible(true);
            txtCampo2.setPromptText("20");
            lblCampo3.setText("Tamaño muestra (n):");
            lblCampo3.setVisible(true);
            txtCampo3.setVisible(true);
            txtCampo3.setPromptText("10");
        }
    }

    @FXML
    protected void ejecutarSimulacion() {
        try {
            int numMuestras = Integer.parseInt(txtMuestras.getText());
            List<Integer> datos;

            if (comboModelo.getSelectionModel().getSelectedIndex() == 0) {
                double p = Double.parseDouble(txtCampo1.getText());
                datos = SimuladorInventario.generarGeometrica(p, numMuestras);
            } else {
                int N = Integer.parseInt(txtCampo1.getText());
                int K = Integer.parseInt(txtCampo2.getText());
                int n = Integer.parseInt(txtCampo3.getText());
                datos = SimuladorInventario.generarHipergeometrica(N, K, n, numMuestras);
            }

            mostrarDatos(datos);
            actualizarGrafico(datos);

        } catch (Exception ex) {
            txtSalida.setText("Parámetros no válidos.");
        }
    }

    private void mostrarDatos(List<Integer> datos) {
        StringBuilder sb = new StringBuilder("Resultados de Simulación:\n");
        for (int i = 0; i < Math.min(datos.size(), 80); i++) {
            sb.append(datos.get(i)).append(" | ");
        }
        txtSalida.setText(sb.toString());
    }

    private void actualizarGrafico(List<Integer> datos) {
        graficoBarras.getData().clear();
        Map<Integer, Integer> conteo = new HashMap<>();

        for (int val : datos) {
            conteo.put(val, conteo.getOrDefault(val, 0) + 1);
        }

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ocurrencias");

        conteo.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> serie.getData().add(new XYChart.Data<>(String.valueOf(e.getKey()), e.getValue())));

        graficoBarras.getData().add(serie);
    }
}