bernoulli <- function(p) {
  if (runif(1) < p) 1 else 0
}

binomial_custom <- function(n, p) {
  sum(replicate(n, bernoulli(p)))
}

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

simular <- function() {
  cat("=== GENERADOR DE VARIABLES ALEATORIAS DISCRETAS ===\n")
  cat("1. Bernoulli\n")
  cat("2. Binomial\n")
  cat("3. Poisson\n")
  
  opcion <- as.integer(readline(prompt = "Selecciona una opción (1-3): "))
  n_muestras <- as.integer(readline(prompt = "Cantidad de muestras (ej. 1000): "))
  
  if (is.na(opcion) || !(opcion %in% 1:3)) {
    stop("Opción no válida.")
  }
  
  if (opcion == 1) {
    p <- as.numeric(readline(prompt = "Probabilidad (p) [0-1]: "))
    datos <- replicate(n_muestras, bernoulli(p))
    titulo <- paste("Bernoulli (p =", p, ")")
    color_bar <- "skyblue"
  } else if (opcion == 2) {
    n <- as.integer(readline(prompt = "Número de ensayos (n): "))
    p <- as.numeric(readline(prompt = "Probabilidad (p) [0-1]: "))
    datos <- replicate(n_muestras, binomial_custom(n, p))
    titulo <- paste("Binomial (n =", n, ", p =", p, ")")
    color_bar <- "lightgreen"
  } else if (opcion == 3) {
    lambda <- as.numeric(readline(prompt = "Tasa media (lambda): "))
    datos <- replicate(n_muestras, poisson_custom(lambda))
    titulo <- paste("Poisson (lambda =", lambda, ")")
    color_bar <- "orange"
  }
  
  cat("\nPrimeros 50 resultados:\n")
  print(head(datos, 50))
  
  tabla_freq <- table(datos)
  
  barplot(
    tabla_freq,
    main = paste("Distribución", titulo),
    xlab = "Valor (X)",
    ylab = "Frecuencia Absoluta",
    col = color_bar,
    border = "black"
  )
}
