import random
import matplotlib.pyplot as plt
from collections import Counter

def geometrica(p):
    intentos = 1
    while random.random() >= p:
        intentos += 1
    return intentos

def hipergeometrica(N, K, n):
    exitos = 0
    poblacion = N
    exitos_restantes = K
    
    for _ in range(n):
        if random.random() < (exitos_restantes / poblacion):
            exitos += 1
            exitos_restantes -= 1
        poblacion -= 1
        
    return exitos

def main():
    print("=" * 55)
    print("   SIMULACIÓN DE INVENTARIO Y CONTROL DE CALIDAD")
    print("=" * 55)
    print("1. Modelo Geométrico (Intentos hasta la primera falla/éxito)")
    print("2. Modelo Hipergeométrico (Muestreo de lotes sin reemplazo)")
    print("-" * 55)
    
    opcion = input("Selecciona un modelo (1 o 2): ")
    n_simulaciones = int(input("Número de simulaciones (ej. 1000): "))
    
    if opcion == "1":
        p = float(input("Probabilidad de defecto/éxito (p) [0-1]: "))
        datos = [geometrica(p) for _ in range(n_simulaciones)]
        titulo = f"Distribución Geométrica (p = {p})"
        color = '#2ca02c'
    elif opcion == "2":
        N = int(input("Tamaño total del lote / población (N): "))
        K = int(input("Unidades defectuosas en el lote (K): "))
        n = int(input("Unidades inspeccionadas / muestra (n): "))
        datos = [hipergeometrica(N, K, n) for _ in range(n_simulaciones)]
        titulo = f"Distribución Hipergeométrica (N={N}, K={K}, n={n})"
        color = '#1f77b4'
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

    ax.set_title(f"Simulación de Inventario: {titulo}", fontsize=13, fontweight='bold', pad=15)
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
    