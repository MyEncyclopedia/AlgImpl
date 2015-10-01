import numpy as np

class GradientDescent:
    "Batch Gradient Descent method of linear regression"
    alpha = 0.01
    step = 100

    def __init__(self, alpha=0.01, step=100):
        self.alpha = alpha
        self.step = step

    def distance(self, v1, v2):
        v = v1 - v2
        v = v * v
        return np.sum(v)


    def h(self, X, theta):
        n = len(theta)
        return np.dot(theta, X)


    def cost(self, X):
        c = np.sum(X*X)
        m = len(X)
        c = 1.0/(2*m) * c
        return c


    def featureNormalize(self, X):
        "Get every feature into approximately [-1, 1] range."
        featureN = len(X)
        mu = np.mean(X[0:featureN + 1], axis=1, dtype=np.float64)
        sigma = np.std(X[0:featureN + 1], axis=1, dtype=np.float64)
        X_norm = X
        for i in range(featureN):
            X_norm[i] = (X[i]-mu[i])/sigma[i]
        return X_norm, mu, sigma

    def iterate(self, data, toNormalize=False):
        m = len(data)
        data = np.transpose(data)
        featureN = len(data) - 1
        Y = data[featureN]  # shape 1 * m
        X = data[0:featureN]  # shape N * m
        if toNormalize:
            X, mu, sigma = self.featureNormalize(X)
        X = np.vstack((np.ones(m), X))  # shape (N+1) * m

        theta = np.zeros(featureN + 1, dtype=np.float64)

        for iter in range(self.step):
            theta_temp = np.zeros(featureN + 1, dtype=np.float64)
            V = self.h(X, theta)
            V = V - Y
            costValue = self.cost(V)

            for i in range(featureN+1):
                theta_temp[i] = np.sum(V * X[i])
            theta_temp *= self.alpha / m
            theta_temp = theta - theta_temp
            # dist = distance(theta, theta_temp)
            theta = theta_temp
        return theta


