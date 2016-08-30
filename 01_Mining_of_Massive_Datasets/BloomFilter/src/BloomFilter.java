
/**
 * https://github.com/apache/flink/blob/master/flink-runtime/src/main/java/org/apache/flink/runtime/operators/util/BloomFilter.java
 */

import java.util.BitSet;

public class BloomFilter {

    protected BitSet bitSet;
    protected int expectedEntries;
    protected int numHashFunctions;

    public BloomFilter(int expectedEntries, int byteSize) {
        if (!(expectedEntries > 0)) {
            throw new IllegalArgumentException("expectedEntries should be > 0");
        }
        this.expectedEntries = expectedEntries;
        this.numHashFunctions = optimalNumOfHashFunctions(expectedEntries, byteSize << 3);
        this.bitSet = new BitSet(byteSize * 8);
    }

    public void addHash(int hash32) {
        int hash1 = hash32;
        int hash2 = hash32 >>> 16;

        for (int i = 1; i <= numHashFunctions; i++) {
            int combinedHash = hash1 + (i * hash2);
            // hashcode should be positive, flip all the bits if it's negative
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            int pos = combinedHash % bitSet.size();
            bitSet.set(pos);
        }
    }

    public boolean testHash(int hash32) {
        int hash1 = hash32;
        int hash2 = hash32 >>> 16;

        for (int i = 1; i <= numHashFunctions; i++) {
            int combinedHash = hash1 + (i * hash2);
            // hashcode should be positive, flip all the bits if it's negative
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            int pos = combinedHash % bitSet.size();
            if (!bitSet.get(pos)) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        this.bitSet.clear();
    }

    /**
     * compute the optimal hash function number with given input entries and bits size, which would
     * make the false positive probability lowest.
     *
     * @param expectEntries
     * @param bitSize
     * @return hash function number
     */
    static int optimalNumOfHashFunctions(long expectEntries, long bitSize) {
        return Math.max(1, (int) Math.round((double) bitSize / expectEntries * Math.log(2)));
    }

    /**
     * Compute optimal bits number with given input entries and expected false positive probability.
     *
     * @param inputEntries
     * @param fpp
     * @return optimal bits number
     */
    public static int optimalNumOfBits(long inputEntries, double fpp) {
        int numBits = (int) (-inputEntries * Math.log(fpp) / (Math.log(2) * Math.log(2)));
        return numBits;
    }

    /**
     * Compute the false positive probability based on given input entries and bits size.
     * Note: this is just the math expected value, you should not expect the fpp in real case would under the return value for certain.
     *
     * @param inputEntries
     * @param bitSize
     * @return
     */
    public static double estimateFalsePositiveProbability(long inputEntries, int bitSize) {
        int numFunction = optimalNumOfHashFunctions(inputEntries, bitSize);
        double p = Math.pow(Math.E, -(double) numFunction * inputEntries / bitSize);
        double estimatedFPP = Math.pow(1 - p, numFunction);
        return estimatedFPP;
    }

}
