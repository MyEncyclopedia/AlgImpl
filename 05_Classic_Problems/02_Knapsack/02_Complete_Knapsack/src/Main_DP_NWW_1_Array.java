
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=DPL_1_C
 */
/**
 * Result: TLE
 * TC: O(O(N*W^2))
 * SC: O(N)
 */
import java.util.Scanner;

public class Main_DP_NWW_1_Array {

    public static class KnapsackComplete {

        int capacity;
        int n;
        int[] cost;
        int[] value;

        public KnapsackComplete(int n, int capacity, int[] cost, int[] value) {
            this.n = n;
            this.capacity = capacity;
            this.cost = cost;
            this.value = value;
        }

        int solve() {
            int[] rowCurr;
            rowCurr = new int[capacity + 1];
            for (int numOfItems = 1; numOfItems <= n; numOfItems++) {
                int costThis = cost[numOfItems - 1];
                for (int weight = capacity; weight >= costThis; weight--) {
                    for (int num = 1; weight >= costThis * num; num++) {
                        rowCurr[weight] = Math.max(rowCurr[weight],
                                rowCurr[weight - costThis * num] + value[numOfItems - 1] * num);
                    }
                }
            }
            return rowCurr[capacity];
        }

    }

    static Scanner in = new Scanner(System.in);

    static KnapsackComplete readCase() {
        while (in.hasNext()) {
            int n = in.nextInt();
            int capacity = in.nextInt();
            int[] cost = new int[n];
            int[] value = new int[n];
            for (int i = 0; i < n; i++) {
                value[i] = in.nextInt();
                cost[i] = in.nextInt();
            }
            KnapsackComplete knapsack = new KnapsackComplete(n, capacity, cost, value);
            return knapsack;
        }
        return null;
    }

    public static void main(String[] args) {
        KnapsackComplete knapsack = readCase();
        System.out.println(knapsack.solve());
    }

}
