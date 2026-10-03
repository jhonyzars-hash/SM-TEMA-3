import math
import random
import matplotlib.pyplot as plt
from collections import Counter

def poisson(lam):
    L = math.exp(-lam)
    k = 0
    p = 1.0
    while p > L:
        k += 1
        p *= random.random()
    return k - 1

def binomial_negativa(r, p):
    exitos = 0
    fallas = 0
    while exitos < r:
        if random.random() < p:
            exitos += 1
        else:
            fallas += 1
    return fallas

def main():
    print("=" * 55)
    print("   SIMULACIÓN DE MODELOS DE CONTEO Y TIEMPOS")
    print("=" * 55)
    print("1. Modelo Poisson (Sucesos por intervalo)")
    print("2. Modelo Binomial Negativa (Fallas hasta 'r' éxitos)")
    print("-" * 55)
    
    opcion = input("Selecciona un modelo (1 o 2): ")
    n_simulaciones = int(input("Número de simulaciones (ej. 1000): "))
    
    if opcion == "1":
        lam = float(input("Tasa media de ocurrencia (λ): "))
        datos = [poisson(lam) for _ in range(n_simulaciones)]
        titulo = f"Distribución Poisson (λ = {lam})"
        color = '#8c564b'
    elif opcion == "2":
        r = int(input("Número de éxitos requeridos (r): "))
        p = float(input("Probabilidad de éxito (p) [0-1]: "))
        datos = [binomial_negativa(r, p) for _ in range(n_simulaciones)]
        titulo = f"Distribución Binomial Negativa (r = {r}, p = {p})"
        color = '#e377c2'
    else:
        print("Opción inválida.")
        return

    print("\n" + "-" * 55)
    print(f"Primeras 20 simulaciones: {datos[:20]}")
    print("-" * 55)

    media = sum(datos) / n_simulaciones
    varianza = sum((x - media) ** 2 for x in datos) / n_simulaciones
    
    conteo = Counter(datos)
    valores = sorted(conteo.keys())
    frecuencias = [conteo[v] for v in valores]

    plt.style.use('seaborn-v0_8-whitegrid' if 'seaborn-v0_8-whitegrid' in plt.style.available else 'default')
    fig, ax = plt.subplots(figsize=(9, 5.5))
    
    barras = ax.bar([str(v) for v in valores], frecuencias, color=color, edgecolor='black', alpha=0.85, width=0.6)

    ax.bar_label(barras, fmt='%d', padding=3, fontsize=9, fontweight='bold')

    ax.set_title(f"Simulación de Conteo: {titulo}", fontsize=13, fontweight='bold', pad=15)
    ax.set_xlabel("Resultado Obtenido (X)", fontsize=11, fontweight='bold')
    ax.set_ylabel("Frecuencia Absoluta", fontsize=11, fontweight='bold')

    texto_stats = f"Simulaciones: {n_simulaciones}\nMedia (μ): {media:.2f}\nVarianza (σ²): {varianza:.2f}"
    ax.text(0.97, 0.93, texto_stats, transform=ax.transAxes, fontsize=10,
            verticalalignment='top', horizontalalignment='right',
            bbox=dict(boxstyle='round,pad=0.5', facecolor='white', alpha=0.9, edgecolor='gray'))

    plt.tight_layout()
    plt.show()

if __name__ == "__main__":
    main()