mu <- 170
sigma <- sqrt(144)

1 - pnorm(180, mean = mu, sd = sigma)
pnorm(165, mean = mu, sd = sigma)
pnorm(190, mean = mu, sd = sigma) - pnorm(155, mean = mu, sd = sigma)
qnorm(0.9, mean = mu, sd = sigma)