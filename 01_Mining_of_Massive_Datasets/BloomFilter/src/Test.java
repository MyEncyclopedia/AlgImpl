
public class Test {

    public static void main(String[] args) {
        long expectedEntries = 1000000L;
        double fpp = 0.01;
        long bitSize = BloomFilter.optimalNumOfBits(expectedEntries, fpp);
        System.out.println("bitSize:" + bitSize);
        System.out.println("expected false positive prob:"
                + BloomFilter.estimateFalsePositiveProbability(expectedEntries, (int) bitSize));
        BloomFilter bloomFilter = new BloomFilter((int) expectedEntries, (int) bitSize / 8);

        for (int i = 0; i < expectedEntries; i++) {
            if (i % 3 == 0) {
                bloomFilter.addHash(i);
            }
        }

        int falsePositive = 0;
        for (int i = 0; i < expectedEntries; i++) {
            boolean exists = bloomFilter.testHash(i);
            if ((i % 3 == 0) && (!exists)) {
                System.out.println("error");
            } else if ((i % 3 != 0) && (exists)) {
                falsePositive++;
            } else {
//                System.out.println("passed");
            }
        }
        for (int i = (int) expectedEntries; i < Integer.MAX_VALUE; i++) {
            boolean exists = bloomFilter.testHash(i);
            if (exists) {
                falsePositive++;
            }
        }
        System.out.println("total flase positive:" + falsePositive);
        System.out.println("positive prob:"
                + (double) falsePositive / (Integer.MAX_VALUE / 2 - expectedEntries / 3));
    }

}
