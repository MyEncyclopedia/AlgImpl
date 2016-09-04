
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
 * SC: O(1)
 */
import java.util.ArrayList;
import java.util.List;

public class Solution_Mine {

    public List<Integer> grayCode(int n) {
        List<Integer> results = new ArrayList<Integer>();
        int grayCode = 0;
        results.add(grayCode);
        int powerN = (1 << n);
        for (int i = 1; i < powerN; i++) {
            int bitIdx = diffBitIdx(i);
            grayCode = revertBit(grayCode, bitIdx);
            results.add(grayCode);
        }
        return results;
    }

    /**
     * The bit index of first different bit starting from most significant pos 
     * between i and i-1
     * For example: diffBitIdx(3) = diffBit(0b011, 0b010) = 2
     * 
     * @param i
     * @return 
     */
    private int diffBitIdx(int i) {
        i = i ^ (i - 1);
        int bit = 31;
        int mask = (-1) >> 31 << 31;
        while ((i & mask) == 0) {
            bit--;
            i = i << 1;
        }
        return bit;
    }

    /**
     * Reverts bit of position bitIdx in int v
     */
    private int revertBit(int v, int bitIdx) {
        return v ^ (1 << (bitIdx));
    }
}
