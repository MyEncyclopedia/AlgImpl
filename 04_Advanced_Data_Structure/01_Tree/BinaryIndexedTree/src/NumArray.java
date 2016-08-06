/**
 * https://leetcode.com/problems/range-sum-query-mutable/
 * 
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
 *
 * The update(i, val) function modifies nums by updating the element at index i to val.
 * Example:
 * Given nums = [1, 3, 5]
 *
 * sumRange(0, 2) -> 9
 * update(1, 2)
 * sumRange(0, 2) -> 8
 * Note:
 * The array is only modifiable by the update function.
 * You may assume the number of calls to update and sumRange function is distributed evenly.
 */
public class NumArray {

    BinaryIndexedTree bit;

    public NumArray(int[] nums) {
        bit = new BinaryIndexedTree(nums);
    }

    public void update(int i, int val) {
        bit.update(i, val);
    }

    public int sumRange(int i, int j) {
        return bit.sum(j) - bit.sum(i - 1);
    }

    static class BinaryIndexedTree {

        // c[0] not used
        int[] c;
        int[] nums;

        public BinaryIndexedTree(int[] nums) {
            this.nums = nums;
            if (nums == null || nums.length == 0) {
                c = new int[0];
                return;
            }
            c = new int[nums.length + 1];
            for (int idx = 1; idx <= nums.length; idx++) {
                int span = lowbit(idx);
                c[idx] = nums[idx - 1];
                int rangeCovered = 1;
                while (span - rangeCovered > 0) {
                    c[idx] += c[idx - rangeCovered];
                    rangeCovered += lowbit(idx - rangeCovered);
                }
            }

        }

        public int sum(int idxInclusive) {
            if ((idxInclusive < 0) || (idxInclusive >= c.length - 1)) {
                return 0;
            }
            idxInclusive = idxInclusive + 1;
            int sum = 0;
            while (idxInclusive > 0) {
                sum += c[idxInclusive];
                idxInclusive = idxInclusive - lowbit(idxInclusive);
            }
            return sum;
        }

        public void update(int idx, int newValue) {
            if ((idx < 0) || (idx >= c.length - 1)) {
                return;
            }
            idx += 1;
            int diff = newValue - nums[idx - 1];
            nums[idx - 1] = newValue;
            while (idx < c.length) {
                c[idx] += diff;
                idx = idx + lowbit(idx);
            }

        }

        int lowbit(int x) {
            return x & (-x);
        }
    }
}

// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.update(1, 10);
// numArray.sumRange(1, 2);
