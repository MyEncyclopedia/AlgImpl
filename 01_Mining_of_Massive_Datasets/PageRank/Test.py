import numpy as np
from PageRank_NoTeleport import PageRank_NoTeleport
from PageRank_Matrix import PageRank_Matrix

pr1 = PageRank_NoTeleport()
pr2 = PageRank_Matrix()


matrixRegular = np.matrix(((0, 1, 1), (1, 1, 0), (1, 0, 0)))
matrixDeadEnd = np.matrix(((0, 1, 0), (1, 1, 0), (1, 0, 0)))

matrixSpiderTrap = np.matrix(((0, 1), (1,0)))
mPeriodic = np.matrix(((0, 0, 1), (1, 0, 0), (0, 1, 0)))

print(pr1.compute(matrixRegular))
print(pr2.compute(matrixRegular))
print(pr1.compute(matrixDeadEnd))
print(pr2.compute(matrixDeadEnd))
