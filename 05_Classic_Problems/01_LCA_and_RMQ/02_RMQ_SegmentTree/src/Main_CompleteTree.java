
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

public class Main_CompleteTree {

    public static class SegmentTree {

        int[] tree;
        int[] raw;
        int rawLen;

        public SegmentTree(int[] arr) {
            int len = Integer.highestOneBit(arr.length) << 2;
            tree = new int[len];
            raw = arr;
            rawLen = raw.length;
            initTree(1, 0, arr.length - 1);
        }

        /**
         * @param idx of tree[]
         * @param rangeBegin of raw[]
         * @param rangeEnd of raw[]
         */
        private void initTree(int idx, int rangeBegin, int rangeEnd) {
            if (rangeBegin == rangeEnd) {
                tree[idx] = raw[rangeBegin];
            } else {
                initTree(idx * 2, rangeBegin, (rangeEnd + rangeBegin) / 2);
                initTree(idx * 2 + 1, (rangeEnd + rangeBegin) / 2 + 1, rangeEnd);
                tree[idx] = tree[idx * 2] < tree[idx * 2 + 1] ? tree[idx * 2] : tree[idx * 2 + 1];
            }
        }

        /**
         * left <= right
         *
         * @param left
         * @param right
         * @return
         */
        public int query(int left, int right) {
            return query(1, 0, rawLen - 1, left, right);
        }

        public void update(int idx, int newValue) {
            update(idx, newValue, 1, 0, rawLen - 1);
        }

        /**
         * Invariant: rangeBegin <= left <= right <= rangeEnd
         */
        private int query(int treeNodeIdx, int rangeBegin, int rangeEnd, int left, int right) {
            int leftResult = Integer.MAX_VALUE;
            int rightResult = Integer.MAX_VALUE;;
            if (rangeBegin == rangeEnd) {
                return tree[treeNodeIdx];
            }
            if (left == rangeBegin && right == rangeEnd) {
                return tree[treeNodeIdx];
            }
            int rangeMid = (rangeBegin + rangeEnd) / 2;
            if (left <= rangeMid) {
                leftResult = query(treeNodeIdx * 2, rangeBegin, rangeMid, left, Math.min(right, rangeMid));
            }
            if (rangeMid + 1 <= right) {
                rightResult = query(treeNodeIdx * 2 + 1, rangeMid + 1, rangeEnd, Math.max(left, rangeMid + 1), right);
            }

            return Math.min(leftResult, rightResult);

        }

        private void update(int idxToUpdate, int newValue, int idx, int rangeBegin, int rangeEnd) {
            if (rangeBegin == rangeEnd) {
                tree[idx] = newValue;
            } else {
                int rangeMid = (rangeBegin + rangeEnd) / 2;
                if (idxToUpdate > rangeMid) {
                    update(idxToUpdate, newValue, idx * 2 + 1, rangeMid + 1, rangeEnd);
                } else {
                    update(idxToUpdate, newValue, idx * 2, rangeBegin, rangeMid);
                }
                tree[idx] = tree[idx * 2] < tree[idx * 2 + 1] ? tree[idx * 2] : tree[idx * 2 + 1];
            }
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
