library(data.table)
library(dplyr)
library(ggplot2)

plot_correlation = function(x, y, xlabel, ylabel, title='') {
	x_temp = data.table(x) %>% mutate(x_bin = ntile(x, n=10))
	plot(aggregate(y, data.frame(x_temp$x_bin), mean), xlab = xlabel, ylab = ylabel, main = title)
}


data = fread("C:\\Users\\yharm\\git\\game\\output\\filename.txt")


ggplot(data, aes(x=data$V1, y=data$V3) ) +
		     geom_bin2d() +
		     theme_bw()
			 
plot_correlation(data$V1, data$V3, 'feature', 'top score')		 