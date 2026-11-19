lambda <- 6

dpois(5, lambda = lambda)
1 - ppois(3, lambda = lambda)
ppois(5, lambda = lambda) - ppois(2, lambda = lambda)

x <- 0:30
plot(x, dpois(x, lambda = lambda), type = "h",
     main = "Poisson Distribution",
     xlab = "Cars sold", ylab = "Probability")