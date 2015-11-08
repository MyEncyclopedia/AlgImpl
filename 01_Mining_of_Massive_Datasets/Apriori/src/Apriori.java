import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class Apriori {

    /**
     * Keeps all input as Tuple set
     */
    private TreeSet<Tuple> inputTuples;
    
    /**
     * support configured in input file
     */
    private int support;
    
    /**
     * Keeps current round supported Tuple set
     */
    private TreeSet<Tuple> supportedTuples;

    /**
     * Read data from {@link #inputStream} and for easy impl, compute all items meeting support criteria
     * @param inputStream 
     */
    public Apriori(InputStream inputStream) {
        Scanner s = new Scanner(inputStream);
        inputTuples = new TreeSet<>();
        supportedTuples = new TreeSet<>();
        support = s.nextInt();
        // map of item idx -> item number
        Map<Integer, Integer> mapCount = new HashMap<>();
        s.nextLine();

        while (s.hasNextLine()) {
            // each line is a set of sorted items like "1, 3, 5"
            String line = s.nextLine();
            String[] elements = line.split(" ");
            int[] intArray = new int[elements.length];
            for (int i = 0; i < elements.length; i++) {
                int intValue = Integer.valueOf(elements[i]);
                intArray[i] = intValue;
                if (mapCount.containsKey(intValue)) {
                    mapCount.put(intValue, mapCount.get(intValue) + 1);
                } else {
                    mapCount.put(intValue, 1);
                }
            }
            Tuple tuple = new Tuple(intArray);
            inputTuples.add(tuple);
        }

        // figure out all items >= support
        for (Map.Entry<Integer, Integer> entry : mapCount.entrySet()) {
            if (entry.getValue() >= support) {
                supportedTuples.add(new Tuple(entry.getKey()));
            }
        }

    }

    /**
     * Initiate next round iteration.
     * There are two steps:
     * 1. According to current {@link #supportedTuples}, generate all possible candidate set
     * 2. Read input tuples and count candidates, filter out candidates less than support
     */
    public void iterate() {
        TreeSet<Tuple> nextLevelCandidates = TupleUtil.join(supportedTuples);
        supportedTuples = TupleUtil.filter(inputTuples, nextLevelCandidates, support);
    }

    public void printResult() {
        for (Tuple t : supportedTuples) {
            System.out.println(t);
        }
    }
}
