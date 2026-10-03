package com.example.variablesdiscretas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generador {
    private static final Random random = new Random();

    public static List<Integer> bernoulli(double p, int n) {
        List<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double u = random.nextDouble();
            resultados.add(u < (1 - p) ? 0 : 1);
        }
        return resultados;
    }

    public static List<Integer> binomial(int ensayos, double p, int cantidad) {
        List<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int x = 0;
            for (int j = 0; j < ensayos; j++) {
                if (random.nextDouble() < p) {
                    x++;
                }
            }
            resultados.add(x);
        }
        return resultados;
    }

    public static List<Integer> poisson(double lambda, int cantidad) {
        List<Integer> resultados = new ArrayList<>();
        double L = Math.exp(-lambda);
        for (int i = 0; i < cantidad; i++) {
            int k = 0;
            double p = 1.0;
            do {
                k++;
                p *= random.nextDouble();
            } while (p > L);
            resultados.add(k - 1);
        }
        return resultados;
    }
}