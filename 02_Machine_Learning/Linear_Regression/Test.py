import numpy as np
from GradientDescent import GradientDescent

GD_OneParam = GradientDescent(alpha=0.01, step=1500)
GD_TwoParams = GradientDescent(alpha=0.1, step=50)

data = np.genfromtxt('ex1data1.txt', delimiter=',')
print(GD_OneParam.iterate(data))

data = np.genfromtxt('ex1data2.txt', delimiter=',')
print(GD_TwoParams.iterate(data, toNormalize=True))


