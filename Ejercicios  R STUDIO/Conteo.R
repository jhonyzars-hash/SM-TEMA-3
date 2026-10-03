poisson_custom <- function(lambda) {
  L <- exp(-lambda)
  k <- 0
  p <- 1.0
  while (p > L) {
    k <- k + 1
    p <- p * runif(1)
  }
  return(k - 1)
}

binomial_negativa_custom <- function(r, p) {
  exitos <- 0
  fallas <- 0
  while (exitos < r) {
    if (runif(1) < p) {
      exitos <- exitos + 1
    } else {
      fallas <- fallas + 1
    }
  }
  return(fallas)
}

simular_conteo <- function() {
  cat("=== SIMULACIÓN DE MODELOS DE CONTEO Y TIEMPOS ===\n")
  cat("1. Modelo Poisson (Sucesos por intervalo)\n")
  cat("2. Modelo Binomial Negativa (Fallas hasta 'r' éxitos)\n")
  
  opcion <- as.integer(readline(prompt = "Selecciona una opción (1 o 2): "))
  n_muestras <- as.integer(readline(prompt = "Cantidad de muestras (ej. 1000): "))
  
  if (is.na(opcion) || !(opcion %in% 1:2)) {
    stop("Opción no válida.")
  }
  
  if (opcion == 1) {
    lambda <- as.numeric(readline(prompt = "Tasa media de ocurrencia (lambda): "))
    datos <- replicate(n_muestras, poisson_custom(lambda))
    titulo <- paste("Poisson (lambda =", lambda, ")")
    color_bar <- "sienna"
  } else if (opcion == 2) {
    r <- as.integer(readline(prompt = "Número de éxitos requeridos (r): "))
    p <- as.numeric(readline(prompt = "Probabilidad de éxito (p) [0-1]: "))
    datos <- replicate(n_muestras, binomial_negativa_custom(r, p))
    titulo <- paste("Binomial Negativa (r =", r, ", p =", p, ")")
    color_bar <- "orchid"
  }
  
  cat("\nPrimeros 50 resultados:\n")
  print(head(datos, 50))
  
  tabla_freq <- table(datos)
  
  barplot(
    tabla_freq,
    main = paste("Simulación de Conteo:", titulo),
    xlab = "Resultado (X)",
    ylab = "Frecuencia Absoluta",
    col = color_bar,
    border = "black"
  )
}