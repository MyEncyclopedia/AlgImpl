import numpy as np

class PageRank_NoTeleport:
    "Power iteration but does not address Spider trap problem or Dead end problem "
    epsilon = 0.0001

    def __init__(self, beta=0.85, epsilon=0.0001):
        self.epsilon = epsilon

    def distance(self, v1, v2):
        v = v1 - v2
        v = v * v
        return np.sum(v)

    def compute(self, G):
        "G is N*N matrix where if j links to i then G[i][j]==1, else G[i][j]==0"
        N = len(G)
        d = np.zeros(N)
        for i in range(N):
            for j in range(N):
                if (G[j, i] == 1):
                    d[i] += 1

        r0 = np.zeros(N, dtype=np.float32) + 1.0 / N
        # construct stochastic M
        M = np.zeros((N, N), dtype=np.float32)
        for i in range(N):
            for j in range(N):
                if G[j, i] == 1:
                    M[j, i] = 1.0 / d[i]
        while True:
            r1 = np.dot(M, r0)
            dist = self.distance(r1, r0)
            if dist < self.epsilon:
                break
            else:
                r0 = r1

        return r1