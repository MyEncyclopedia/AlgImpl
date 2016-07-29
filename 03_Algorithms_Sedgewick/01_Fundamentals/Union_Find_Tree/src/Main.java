
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/finder.jsp?course=DSL
 */
/**
 * Result: AC
 * TC: 
 *   union: O(log*(N)), log* is inverse of Ackermann function
 *   find: O(1)
 * SC: O(N)
 */
import java.util.Scanner;

public class Main {

    public static class UnionFind {

        int[] id;
        int count;
        int[] weight;  // size indexed by root id

        public UnionFind(int n) {
            id = new int[n];
            weight = new int[n];
            count = n;
            for (int idx = 0; idx < id.length; idx++) {
                id[idx] = idx;
                weight[idx] = 1;
            }
        }

        public void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) {
                return;
            }
            // make smaller root point to larger one
            if (weight[pRoot] < weight[qRoot]) {
                id[pRoot] = qRoot;
                weight[qRoot] += weight[pRoot];
            } else {
                id[qRoot] = pRoot;
                weight[pRoot] += weight[qRoot];
            }
            count--;
        }

        // path compression
        public int find(int p) {
            if (id[p] != p) {
                id[p] = find(id[p]);
            }
            return id[p];
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public int count() {
            return count;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        UnionFind uf = new UnionFind(n);
        int q = scanner.nextInt();
        while (q-- > 0) {
            int com = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (com == 0) {
                uf.union(x, y);
            } else {
                System.out.println(uf.connected(x, y) ? "1" : "0");
            }
        }
    }
}
