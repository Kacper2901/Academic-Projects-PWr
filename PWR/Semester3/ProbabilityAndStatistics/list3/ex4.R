rate <- 4

1 - pexp(0.5, rate = rate)
pexp(20/60, rate = rate)
pexp(80/60, rate = rate) - pexp(40/60, rate = rate)
qexp(0.8, rate = rate)

t <- seq(0, 3, by = 0.01)
plot(t, dexp(t, rate = rate), type = "l",
     main = "Exponential Density Function",
     xlab = "Time", ylab = "Density")