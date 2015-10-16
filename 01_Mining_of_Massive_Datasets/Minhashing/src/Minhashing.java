
import java.util.function.Function;

public class Minhashing {

    Integer[][] sig;

    public Minhashing(int[][] sets, Function<Integer, Integer>[] hashs) {
        int setNumber = sets.length;
        // use Integer[] so that infinity can be represented by null
        sig = new Integer[setNumber][];
        for (int i = 0; i < setNumber; i++) {
            sig[i] = new Integer[hashs.length];
        }

        // for each row index value
        for (int i = 0; i < sets[0].length; i++) {
            // calc hash vector 
            int[] hashVector = new int[hashs.length];
            for (int hashIdx = 0; hashIdx < hashs.length; hashIdx++) {
                hashVector[hashIdx] = hashs[hashIdx].apply(i);
            }

            // for each set signature vector
            for (int setIdx = 0; setIdx < setNumber; setIdx++) {
                for (int hashIdx = 0; hashIdx < hashs.length; hashIdx++) {
                    if (sets[setIdx][i] != 0) {
                        if (sig[setIdx][hashIdx] == null || sig[setIdx][hashIdx] > hashVector[hashIdx]) {
                            sig[setIdx][hashIdx] = hashVector[hashIdx];
                        }
                    }
                }
            }
        }
    }

    public float computeSimilarity(int setA, int setB) {
        int equals = 0;

        for (int i = 0; i < sig[0].length; i++) {
            if (sig[setA][i] == sig[setB][i]) {
                equals++;
            }
        }
        return (float) equals / sig[0].length;
    }
}
