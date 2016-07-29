
public class Test {

    public static void main(String[] args) {
        Main.UnionFind uf = new Main.UnionFind(5);
        int[][] input = new int[][]{
            {0, 1, 4},
            {0, 2, 3},
            {1, 1, 2},
            {1, 3, 4},
            {1, 1, 4},
            {1, 3, 2},
            {0, 1, 3},
            {1, 2, 4},
            {1, 3, 0},
            {0, 0, 4},
            {1, 0, 2},
            {1, 3, 0}};
        for (int[] cmd : input) {
            if (cmd[0] == 0) {
                uf.union(cmd[1], cmd[2]);
            } else {
                System.out.println(uf.connected(cmd[1], cmd[2]));
            }
        }
    }

}
