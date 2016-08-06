
/**
 * http://hihocoder.com/problemset/problem/1070
 */
/**
 * Result: AC
 * TC: Query   O(log(N))
 *     Update  O(log(N))
 * SC: O(N)
 */
import java.util.Scanner;

public class Main {

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

        public int query(int left, int right) {
            return query(1, 0, rawLen - 1, left, right);
        }

        public void update(int idx, int newValue) {
            update(idx, newValue, 1, 0, rawLen - 1);
        }

        /**
         * Invariant: rangeBegin <= left <= right <= rangeEnd
         */
        private int query(int idx, int rangeBegin, int rangeEnd, int left, int right) {
            Integer leftResult = null;
            Integer rightResult = null;
            if (rangeBegin == rangeEnd) {
                return tree[idx];
            }
            int rangeMid = (rangeBegin + rangeEnd) / 2;
            if (left <= rangeMid) {
                leftResult = query(idx * 2, rangeBegin, rangeMid, left, Math.min(right, rangeMid));
            }
            if (rangeMid + 1 <= right) {
                rightResult = query(idx * 2 + 1, rangeMid + 1, rangeEnd, Math.max(left, rangeMid + 1), right);
            }
            if (leftResult == null) {
                return rightResult;
            } else if (rightResult == null) {
                return leftResult;
            } else {
                return Math.min(leftResult, rightResult);
            }
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        SegmentTree segTree = new SegmentTree(arr);
        int q = scanner.nextInt();
        while (q-- > 0) {
            if (scanner.nextInt() == 0) { // query
                int l = scanner.nextInt();
                int r = scanner.nextInt();
                System.out.println(segTree.query(l - 1, r - 1));
            } else { // update
                int idx = scanner.nextInt();
                int value = scanner.nextInt();
                segTree.update(idx - 1, value);
            }
        }
    }
}
