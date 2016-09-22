
/**
 * http://hihocoder.com/problemset/problem/1077
 */
/**
 * Result: AC
 * TC: Build   O(2N)
 *     Query   O(log(N))
 *     Update  O(log(N))
 * SC: O(2N)
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Main_FullTree {

    public static class SegmentTree {

        int[] tree;
        int n;
        int rawLength;

        public SegmentTree(int[] rawArray) {
            n = 1;
            while (n < rawArray.length) {
                n <<= 1;
            }
            tree = new int[2 * n - 1];
            rawLength = rawArray.length;
            initTree(0, 0, n - 1, rawArray);
        }

        /**
         * @param idx of tree[]
         * @param rangeBegin of raw[]
         * @param rangeEnd of raw[]
         */
        private void initTree(int idx, int rangeBegin, int rangeEnd, int[] rawArray) {
            if (rangeBegin >= rawArray.length) {
                return;
            }
            if (rangeBegin == rangeEnd) {
                tree[idx] = rawArray[rangeBegin];
            } else {
                initTree(idx * 2 + 1, rangeBegin, (rangeEnd + rangeBegin) / 2, rawArray);
                initTree(idx * 2 + 2, (rangeEnd + rangeBegin) / 2 + 1, rangeEnd, rawArray);
                tree[idx] = tree[idx * 2 + 1] < tree[idx * 2 + 2] ? tree[idx * 2 + 1] : tree[idx * 2 + 2];
            }
        }

        public int query(int left, int right) {
            return query(0, 0, n - 1, left, right);
        }

        public void update(int idx, int newValue) {
            idx += n - 1;
            tree[idx] = newValue;
            while (idx > 0) {
                idx = (idx - 1) / 2;
                tree[idx] = Math.min(tree[idx * 2 + 1], tree[idx * 2 + 2]);
            }
        }

        /**
         * Invariant: rangeBegin <= left <= right <= rangeEnd
         */
        private int query(int idx, int rangeBegin, int rangeEnd, int left, int right) {
            int leftResult = Integer.MAX_VALUE;
            int rightResult = Integer.MAX_VALUE;
            if (rangeBegin == rangeEnd) {
                return tree[idx];
            }
            if (left == rangeBegin && right == rangeEnd) {
                return tree[idx];
            }
            int rangeMid = (rangeBegin + rangeEnd) / 2;
            if (left <= rangeMid) {
                leftResult = query(idx * 2 + 1, rangeBegin, rangeMid, left, Math.min(right, rangeMid));
            }
            if (rangeMid + 1 <= right) {
                rightResult = query(idx * 2 + 2, rangeMid + 1, rangeEnd, Math.max(left, rangeMid + 1), right);
            }

            return Math.min(leftResult, rightResult);

        }

    }

    private static final StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    public static void main(String[] args) throws Exception {
        int n = nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextInt();
        }
        SegmentTree segTree = new SegmentTree(arr);
        int q = nextInt();
        while (q-- > 0) {
            if (nextInt() == 1) { // update
                int idx = nextInt();
                int value = nextInt();
                segTree.update(idx - 1, value);
            } else { // query
                int l = nextInt() - 1;
                int r = nextInt() - 1;
                out.println(segTree.query(l, r));
            }
        }
        out.flush();
    }
}
