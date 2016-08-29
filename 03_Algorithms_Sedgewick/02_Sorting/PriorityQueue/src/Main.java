
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=ALDS1_9_C
 */
/**
 * Result: AC
 * TC: Amortized
 *   O(log(N)) insert, pop
 * SC: O(N)
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.lang.reflect.Array;
import java.util.Comparator;

public class Main {

    public static class PriorityQueue<T> {

        private Comparator<T> comparator;
        private T[] array;
        private int lastIdx = 0;
        private Class clazz;

        public PriorityQueue(Comparator<T> comparator, Class clazz) {
            this(comparator, clazz, 50);
        }

        @SuppressWarnings("unchecked")
        public PriorityQueue(Comparator<T> comparator, Class clazz, int initialCapacity) {
            this.comparator = comparator;
            this.clazz = clazz;
            array = (T[]) Array.newInstance(clazz, initialCapacity);
        }

        private void swim(int k) {
            while (k > 1 && less(k / 2, k)) {
                exch(k / 2, k);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= lastIdx) {
                int largerChild = 2 * k;
                if (largerChild + 1 <= lastIdx && less(largerChild, largerChild + 1)) {
                    largerChild++;
                }
                if (!less(k, largerChild)) {
                    break;
                }
                exch(k, largerChild);
                k = largerChild;
            }
        }

        private void exch(int i, int j) {
            T key = array[i];
            array[i] = array[j];
            array[j] = key;
        }

        private boolean less(int i, int j) {
            return comparator.compare(array[i], array[j]) < 0;
        }

        public T peek() {
            return lastIdx == 0 ? null : array[1];
        }

        public T pop() {
            if (lastIdx == 0) {
                return null;
            }
            exch(1, lastIdx);
            T top = array[lastIdx--];
            sink(1);
            return top;
        }

        @SuppressWarnings("unchecked")
        public void insert(T key) {
            if (lastIdx == array.length - 1) {
                T[] newArray = (T[]) Array.newInstance(clazz, array.length * 2);
                for (int i = 1; i < array.length; i++) {
                    newArray[i] = array[i];
                }
                array = newArray;
            }
            array[++lastIdx] = key;
            swim(lastIdx);
        }

        public boolean isEmpty() {
            return lastIdx == 0;
        }

        public int size() {
            return lastIdx;
        }
    }

    public static void main(String[] args) throws IOException {
        PriorityQueue<Integer> maxPQ = new PriorityQueue<Integer>(
                new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        }, Integer.class);

        while (true) {
            String cmd = nextString();
            if (cmd.equals("insert")) {
                int v = nextInt();
                maxPQ.insert(v);
            } else if (cmd.equals("extract")) {
                out.println(maxPQ.pop());
            } else if (cmd.equals("end")) {
                break;
            }
        }
        out.flush();
    }

    private static final StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    public static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }
}
