
/**
 * http://hihocoder.com/problemset/problem/1068
 */
/**
 * Result: AC
 * TC: O(1)
 * SC: O(N*log(N))
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Main {

    public static class RMQ_SparseTable {

        private int cache[][];

        public RMQ_SparseTable(int[] a) {
            cache = new int[a.length][];
            for (int i = 0; i < a.length; i++) {
                int len = a.length - i;
                len = (int) (Math.log(len) / Math.log(2)) + 1;
                cache[i] = new int[len];
                cache[i][0] = a[i];
            }

            for (int power = 1; power < cache[0].length; power++) {
                for (int idx = 0; idx < a.length && power < cache[idx].length; idx++) {
                    cache[idx][power] = Math.min(cache[idx][power - 1],
                            cache[idx + (1 << (power - 1))][power - 1]);
                }
            }

        }

        public int query(int left, int right) {
            int maxPower = (int) (Math.log(right - left + 1) / Math.log(2));
            return Math.min(cache[left][maxPower], cache[right - (1 << maxPower) + 1][maxPower]);
        }
    }

    private static final StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = nextInt();
        }
        RMQ_SparseTable rmqST = new RMQ_SparseTable(a);
        int queryNum = nextInt();
        for (int i = 0; i < queryNum; i++) {
            int left = nextInt() - 1;
            int right = nextInt() - 1;
            out.println(rmqST.query(left, right));
        }
        out.flush();
    }

}
