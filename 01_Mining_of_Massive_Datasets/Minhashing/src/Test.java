
import java.util.function.Function;

public class Test {

    public static void main(String[] args) {
        int[][] input = new int[4][];
        input[0] = new int[]{1, 0, 0, 1, 0};
        input[1] = new int[]{0, 0, 1, 0, 0};
        input[2] = new int[]{0, 1, 0, 1, 1};
        input[3] = new int[]{1, 0, 1, 1, 0};
        Function<Integer, Integer> h1 = x -> (x + 1) % 5;
        Function<Integer, Integer> h2 = x -> (3 * x + 1) % 5;
        Function<Integer, Integer>[] hashs = new Function[] {h1, h2};
        Minhashing minhashing = new Minhashing(input, hashs);
        System.out.println(minhashing.computeSimilarity(0, 2));
    }
}
