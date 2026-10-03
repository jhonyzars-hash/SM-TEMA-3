package com.example.ejercicio3variablesdiscretas;

import java.util.HashMap;
import java.util.Map;

public class CalculadorPoissonBinomial {

    public static double factorial(int n) {
        if (n <= 1) return 1.0;
        double fact = 1.0;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static double combinatoria(int n, int k) {
        if (k < 0 || k > n) return 0.0;
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static Map<Integer, Double> calcularPoisson(double lambda, int maxK) {
        Map<Integer, Double> probabilidades = new HashMap<>();
        for (int k = 0; k <= maxK; k++) {
            double p = (Math.pow(lambda, k) * Math.exp(-lambda)) / factorial(k);
            probabilidades.put(k, p);
        }
        return probabilidades;
    }

    public static Map<Integer, Double> calcularBinomialNegativa(int r, double p, int maxK) {
        Map<Integer, Double> probabilidades = new HashMap<>();
        for (int k = 0; k <= maxK; k++) {
            double prob = combinatoria(k + r - 1, k) * Math.pow(p, r) * Math.pow(1 - p, k);
            probabilidades.put(k, prob);
        }
        return probabilidades;
    }
}