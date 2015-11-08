import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class TupleUtil {

    /**
     * Constructs a tuple by combining two input ones.
     * For example combineTwoTuples(t"1, 3, 4", t"2 4 5") => t"1 2 3 4 5"
     * 
     * @param t1
     * @param t2
     * @return 
     */
    public static Tuple combineTwoTuples(Tuple t1, Tuple t2) {
        int length = t1.getLength();
        ArrayList<Integer> sorted = new ArrayList<>();
        int idx1 = 0;
        int idx2 = 0;
        while (idx1 < length || idx2 < length) {
            if (idx1 == length) {
                sorted.add(t2.get(idx2));
                idx2++;
                continue;
            } else if (idx2 == length) {
                sorted.add(t1.get(idx1));
                idx1++;
                continue;
            } else {
                int fromT1 = t1.get(idx1);
                int fromT2 = t2.get(idx2);
                if (fromT1 == fromT2) {
                    sorted.add(fromT1);
                    idx1++;
                    idx2++;
                } else if (fromT1 < fromT2) {
                    sorted.add(fromT1);
                    idx1++;
                } else {
                    sorted.add(fromT2);
                    idx2++;
                }
            }
        }

        return new Tuple(sorted);
    }

    /**
     * Generates all possible tuples of length one less than input tuple
     * For example: generateAllPossibleLower(t"1 3 4") => {t"1 3", t"1 4", t"3 4"}
     * @param tuple
     * @return 
     */
    public static List<Tuple> generateAllPossibleLower(Tuple tuple) {
        ArrayList<Tuple> ret = new ArrayList<>();
        int currentLength = tuple.getLength();
        // there are currentLength possibilies
        // because we can form smaller array by ommtting each position of original tuple
        for (int idxOmitted = 0; idxOmitted < currentLength; idxOmitted++) {
            int[] array = new int[currentLength - 1];
            boolean omitted = false;
            for (int idx = 0; idx < currentLength - 1; idx++) {
                if (idx == idxOmitted) {
                    omitted = true;
                }
                int origTupleIdx = idx + (omitted ? 1 : 0);
                array[idx] = tuple.get(origTupleIdx);

            }
            ret.add(new Tuple(array));
        }
        return ret;
    }

    /**
     * Determines whether a tuple contains a sub tuple.
     * For example: 
     * contains(t"1 3 4", t"1 3") => true
     * contains(t"1 3 4", t"1 3 5") => false
     * @param tuple
     * @param toBeContained
     * @return 
     */
    public static boolean contains(Tuple tuple, Tuple toBeContained) {
        if (tuple.getLength() < toBeContained.getLength()) {
            return false;
        }
        int tupleIdx = 0;
        for (int subIdx = 0; subIdx < toBeContained.getLength(); subIdx++) {
            int subElement = toBeContained.get(subIdx);

            while (tupleIdx < tuple.getLength() && tuple.get(tupleIdx) < subElement) {
                tupleIdx++;
            }
            if (tupleIdx == tuple.getLength()) {
                return false;
            } else if (tuple.get(tupleIdx)== subElement) {
                tupleIdx++;
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Iterate inputSet and filter out candidates less than support.
     * 
     * @param inputSet
     * @param candidates
     * @param support
     * @return 
     */
    public static TreeSet<Tuple> filter(TreeSet<Tuple> inputSet, TreeSet<Tuple> candidates, int support) {
        Map<Tuple, Integer> mapCount = new HashMap<>();
        for (Tuple inputTuple : inputSet) {
            for (Tuple candidate : candidates) {
                if (TupleUtil.contains(inputTuple, candidate)) {
                    if (mapCount.containsKey(candidate)) {
                        mapCount.put(candidate, mapCount.get(candidate) + 1);
                    } else {
                        mapCount.put(candidate, 1);
                    }
                }
            }
        }

        TreeSet<Tuple> retTreeSet = new TreeSet<>();
        // figure out all items >= support
        for (Map.Entry<Tuple, Integer> entry : mapCount.entrySet()) {
            if (entry.getValue() >= support) {
                retTreeSet.add(entry.getKey());
            }
        }
        return retTreeSet;
    }

    /**
     * Given current supported tuples, generates candidate tuples having length one more than originals
     * such that each of the candidates has all combinations in originals.
     * For example
     * join({t"1 2, t"2 3", t"1 3", t"1 4"}) => t"1 2 3"
     * 
     * @param supportedTuples
     * @return 
     */
    public static TreeSet<Tuple> join(TreeSet<Tuple> supportedTuples) {
        TreeSet<Tuple> retTreeSet = new TreeSet<>();

        int currentLength = supportedTuples.first().getLength();
        if (currentLength == 1) {
            // when current length is 1, case is special as we only need to 
            // return full combination of all supportedTuples
            Tuple[] tuples = supportedTuples.toArray(new Tuple[0]);
            for (int firstTupleIdx = 0; firstTupleIdx < tuples.length; firstTupleIdx++) {
                for (int secondTupleIdx = firstTupleIdx + 1; secondTupleIdx < tuples.length; secondTupleIdx++) {
                    retTreeSet.add(new Tuple(tuples[firstTupleIdx].get(0), tuples[secondTupleIdx].get(0)));
                }
            }
            return retTreeSet;
        }

        Tuple firstTuple = supportedTuples.first();
        while (firstTuple != null) {
            Tuple secondTuple = firstTuple;
            while ((secondTuple = supportedTuples.higher(secondTuple)) != null) {
                Tuple combined = TupleUtil.combineTwoTuples(firstTuple, secondTuple);
                if (combined.getLength() != currentLength + 1) {
                    break;
                }
                // for all possible low order tuples, they must in supportedTuples
                List<Tuple> allPossibles = TupleUtil.generateAllPossibleLower(combined);
                if (supportedTuples.containsAll(allPossibles)) {
                    retTreeSet.add(combined);
                }
            }
            firstTuple = supportedTuples.higher(firstTuple);
        }

        return retTreeSet;
    }

    public static void main(String[] args) {
        Tuple t = new Tuple(1, 4, 5, 6);
        for (Tuple tt : generateAllPossibleLower(t)) {
            System.out.println(tt);
        }

    }
}
