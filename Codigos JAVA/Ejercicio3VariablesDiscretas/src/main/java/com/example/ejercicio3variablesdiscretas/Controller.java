package com.example.ejercicio3variablesdiscretas;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.Map;

public class Controller {

    @FXML private ComboBox<String> comboModelo;
    @FXML private TextField txtParam1;
    @FXML private TextField txtParam2;
    @FXML private TextField txtMaxK;
    @FXML private Label lblParam1;
    @FXML private Label lblParam2;
    @FXML private TextArea txtSalida;
    @FXML private BarChart<String, Number> graficoBarras;

    @FXML
    public void initialize() {
        comboModelo.getItems().addAll("Distribución Poisson", "Distribución Binomial Negativa");
        comboModelo.getSelectionModel().selectFirst();
        configurarCampos();

        comboModelo.setOnAction(e -> configurarCampos());
    }

    private void configurarCampos() {
        if (comboModelo.getSelectionModel().getSelectedIndex() == 0) {
            lblParam1.setText("Tasa media (λ):");
            txtParam1.setPromptText("3.5");
            lblParam2.setVisible(false);
            txtParam2.setVisible(false);
        } else {
            lblParam1.setText("Éxitos requeridos (r):");
            txtParam1.setPromptText("3");
            lblParam2.setText("Probabilidad de éxito (p):");
            lblParam2.setVisible(true);
            txtParam2.setVisible(true);
            txtParam2.setPromptText("0.4");
        }
    }

    @FXML
    protected void calcularDistribucion() {
        try {
            int maxK = Integer.parseInt(txtMaxK.getText());
            Map<Integer, Double> resultados;

            if (comboModelo.getSelectionModel().getSelectedIndex() == 0) {
                double lambda = Double.parseDouble(txtParam1.getText());
                resultados = CalculadorPoissonBinomial.calcularPoisson(lambda, maxK);
            } else {
                int r = Integer.parseInt(txtParam1.getText());
                double p = Double.parseDouble(txtParam2.getText());
                resultados = CalculadorPoissonBinomial.calcularBinomialNegativa(r, p, maxK);
            }

            mostrarProbabilidades(resultados);
            actualizarGrafico(resultados);

        } catch (Exception ex) {
            txtSalida.setText("Error en los parámetros ingresados.");
        }
    }

    private void mostrarProbabilidades(Map<Integer, Double> mapa) {
        StringBuilder sb = new StringBuilder("Tabla de Probabilidades:\n");
        double acumulada = 0;
        for (Map.Entry<Integer, Double> entry : mapa.entrySet()) {
            acumulada += entry.getValue();
            sb.append(String.format("k = %2d  |  P(X=k) = %.5f  |  F(k) = %.5f\n", entry.getKey(), entry.getValue(), acumulada));
        }
        txtSalida.setText(sb.toString());
    }

    private void actualizarGrafico(Map<Integer, Double> mapa) {
        graficoBarras.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Probabilidad P(X=k)");

        mapa.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> serie.getData().add(new XYChart.Data<>(String.valueOf(e.getKey()), e.getValue())));

        graficoBarras.getData().add(serie);
    }
}