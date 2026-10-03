geometrica_custom <- function(p) {
  intentos <- 1
  while (runif(1) >= p) {
    intentos <- intentos + 1
  }
  return(intentos)
}

hipergeometrica_custom <- function(N, K, n) {
  exitos <- 0
  poblacion <- N
  exitos_restantes <- K
  
  for (i in 1:n) {
    if (runif(1) < (exitos_restantes / poblacion)) {
      exitos <- exitos + 1
      exitos_restantes <- exitos_restantes - 1
    }
    poblacion <- poblacion - 1
  }
  return(exitos)
}

simular_inventario <- function() {
  cat("=== SIMULACIÓN DE INVENTARIO Y CONTROL DE CALIDAD ===\n")
  cat("1. Modelo Geométrico (Intentos hasta el primer defecto)\n")
  cat("2. Modelo Hipergeométrico (Muestreo sin reemplazo)\n")
  
  opcion <- as.integer(readline(prompt = "Selecciona una opción (1 o 2): "))
  n_muestras <- as.integer(readline(prompt = "Cantidad de muestras (ej. 1000): "))
  
  if (is.na(opcion) || !(opcion %in% 1:2)) {
    stop("Opción no válida.")
  }
  
  if (opcion == 1) {
    p <- as.numeric(readline(prompt = "Probabilidad de éxito/defecto (p) [0-1]: "))
    datos <- replicate(n_muestras, geometrica_custom(p))
    titulo <- paste("Geométrica (p =", p, ")")
    color_bar <- "darkgreen"
  } else if (opcion == 2) {
    N <- as.integer(readline(prompt = "Tamaño total del lote (N): "))
    K <- as.integer(readline(prompt = "Unidades defectuosas en lote (K): "))
    n <- as.integer(readline(prompt = "Muestra inspeccionada (n): "))
    datos <- replicate(n_muestras, hipergeometrica_custom(N, K, n))
    titulo <- paste("Hipergeométrica (N=", N, ", K=", K, ", n=", n, ")")
    color_bar <- "steelblue"
  }
  
  cat("\nPrimeros 50 resultados:\n")
  print(head(datos, 50))
  
  tabla_freq <- table(datos)
  
  barplot(
    tabla_freq,
    main = paste("Simulación de Inventario:", titulo),
    xlab = "Resultado (X)",
    ylab = "Frecuencia Absoluta",
    col = color_bar,
    border = "black"
  )
}
