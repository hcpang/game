library(data.table)
library(dplyr)
library(ggplot2)

plot_correlation = function(x, y, xlabel, ylabel, title='') {
	x_temp = data.table(x) %>% mutate(x_bin = ntile(x, n=10))
	plot(aggregate(y, data.frame(x_temp$x_bin), mean), xlab = xlabel, ylab = ylabel, main = title)
}


data = fread("C:\\Users\\yharm\\git\\game\\output\\filename.txt")

marbleDiff = data$V1
h4 = data$V2
emptyPocketsDiff = data$V3

topScore = data$V5

ggplot(data, aes(x=marbleDiff, y=topScore) ) +
		geom_bin2d() +
		theme_bw()

ggplot(data, aes(x=h4, y=topScore) ) +
		geom_bin2d() +
		theme_bw()

ggplot(data, aes(x=emptyPocketsDiff, y=topScore) ) +
		geom_bin2d() +
		theme_bw()

ggplot(data, aes(x=marbleDiff, y=emptyPocketsDiff) ) +
		geom_bin2d() +
		theme_bw()