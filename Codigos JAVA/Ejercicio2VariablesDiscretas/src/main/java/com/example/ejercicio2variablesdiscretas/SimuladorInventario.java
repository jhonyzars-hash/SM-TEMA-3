package com.example.ejercicio2variablesdiscretas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimuladorInventario {
    private static final Random random = new Random();

    public static List<Integer> generarGeometrica(double p, int cantidad) {
        List<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int intentos = 1;
            while (random.nextDouble() >= p) {
                intentos++;
            }
            resultados.add(intentos);
        }
        return resultados;
    }

    public static List<Integer> generarHipergeometrica(int N, int K, int n, int cantidad) {
        List<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int exitosObtenidos = 0;
            int poblacionActual = N;
            int exitosRestantes = K;

            for (int j = 0; j < n; j++) {
                double u = random.nextDouble();
                double probExito = (double) exitosRestantes / poblacionActual;

                if (u < probExito) {
                    exitosObtenidos++;
                    exitosRestantes--;
                }
                poblacionActual--;
            }
            resultados.add(exitosObtenidos);
        }
        return resultados;
    }
}