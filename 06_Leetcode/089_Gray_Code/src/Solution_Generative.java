
/**
 * https://leetcode.com/problems/gray-code/
 *
 * The gray code is a binary numeral system where two successive values differ in only one bit.
 *
 * Given a non-negative integer n representing the total number of bits in the code,
 * print the sequence of gray code. A gray code sequence must begin with 0.
 *
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 *
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * Note:
 * For a given n, a gray code sequence is not uniquely defined.
 * For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
 * For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
 *
 */
/**
 * Result: AC
 * TC: O(2^n)
 * SC: O(2^n)
 */
import java.util.Arrays;
import java.util.List;

public class Solution_Generative {

    public List<Integer> grayCode(int n) {
        Integer[] array = new Integer[(int) Math.pow(2, n)];
        array[0] = 0;
        int halfLen = 1;
        for (int i = 0; i < n; i++) {
            int reflectIdx = halfLen * 2 - 1;
            for (int k = 0; k < halfLen; k++, reflectIdx--) {
                array[reflectIdx] = setBit(array[k], i);
            }
            halfLen = halfLen << 1;
        }
        return Arrays.asList(array);
    }

    private int setBit(int value, int bitPos) {
        int orOp = 1 << bitPos;
        return value | orOp;
    }
}
