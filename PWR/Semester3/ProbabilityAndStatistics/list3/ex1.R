n <- 6
p <- 0.5

dbinom(5, size = n, prob = p) #exactly 5
1 - pbinom(2, size = n, prob = p) # >= 3
pbinom(4, size = n, prob = p) - pbinom(1, size = n, prob = p) 2 <= x<= 4

x <- 0:6
plot(x, dbinom(x, size = n, prob = p), type = "h",
     main = "Binomial Distribution",
     xlab = "Number of tails", ylab = "Probability")