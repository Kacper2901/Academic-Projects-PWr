n <- 180
p <- 1/6

dbinom(27, size = n, prob = p)
1 - pbinom(31, size = n, prob = p)
pbinom(28, size = n, prob = p)
pbinom(33, size = n, prob = p) - pbinom(24, size = n, prob = p)