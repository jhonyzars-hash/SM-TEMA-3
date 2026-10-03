import random
import math
import matplotlib.pyplot as plt
from collections import Counter

def bernoulli(p):
    return 1 if random.random() < p else 0

def binomial(n, p):
    return sum(bernoulli(p) for _ in range(n))

def poisson(lam):
    L = math.exp(-lam)
    k = 0
    p = 1.0
    while p > L:
        k += 1
        p *= random.random()
    return k - 1

def main():
    print("--- GENERADOR DE VARIABLES ALEATORIAS DISCRETAS ---")
    print("1. Bernoulli")
    print("2. Binomial")
    print("3. Poisson")
    
    opcion = input("Selecciona una opción (1-3): ")
    n_muestras = int(input("Cantidad de muestras (ej. 1000): "))
    
    datos = []
    
    if opcion == "1":
        p = float(input("Probabilidad (p) [0-1]: "))
        datos = [bernoulli(p) for _ in range(n_muestras)]
        titulo = f"Bernoulli (p={p})"
    elif opcion == "2":
        n = int(input("Número de ensayos (n): "))
        p = float(input("Probabilidad (p) [0-1]: "))
        datos = [binomial(n, p) for _ in range(n_muestras)]
        titulo = f"Binomial (n={n}, p={p})"
    elif opcion == "3":
        lam = float(input("Tasa media (lambda): "))
        datos = [poisson(lam) for _ in range(n_muestras)]
        titulo = f"Poisson (λ={lam})"
    else:
        print("Opción no válida.")
        return

    print("\nPrimeros 50 resultados:")
    print(datos[:50])

    # Graficar frecuencias
    conteo = Counter(datos)
    valores = sorted(conteo.keys())
    frecuencias = [conteo[v] for v in valores]

    plt.bar([str(v) for v in valores], frecuencias, color='skyblue', edgecolor='black')
    plt.title(f"Distribución {titulo}")
    plt.xlabel("Valor (X)")
    plt.ylabel("Frecuencia")
    plt.grid(axis='y', linestyle='--', alpha=0.7)
    plt.show()

if __name__ == "__main__":
    main()