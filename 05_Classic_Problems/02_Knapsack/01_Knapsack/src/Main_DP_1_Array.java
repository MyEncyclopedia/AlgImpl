
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=DPL_1_B
 */
/**
 * Result: AC
 * TC: O(N^2)
 * SC: O(N)
 */
import java.util.Scanner;

public class Main_DP_1_Array {

    public static class Knapsack01 {

        int capacity;
        int n;
        int[] cost;
        int[] value;

        public Knapsack01(int n, int capacity, int[] cost, int[] value) {
            this.n = n;
            this.capacity = capacity;
            this.cost = cost;
            this.value = value;
        }

        int solve() {
            int[] rowCurr = new int[capacity + 1];
            for (int numOfItems = 1; numOfItems <= n; numOfItems++) {
                for (int weight = capacity; weight >= cost[numOfItems - 1]; weight--) {
                    if (weight >= cost[numOfItems - 1]) {
                        rowCurr[weight] = Math.max(
                                rowCurr[weight - cost[numOfItems - 1]] + value[numOfItems - 1],
                                rowCurr[weight]);
                    }
                }
            }
            return rowCurr[capacity];
        }

    }

    static Scanner in = new Scanner(System.in);

    static Knapsack01 readCase() {
        while (in.hasNext()) {
            int n = in.nextInt();
            int capacity = in.nextInt();
            int[] cost = new int[n];
            int[] value = new int[n];
            for (int i = 0; i < n; i++) {
                value[i] = in.nextInt();
                cost[i] = in.nextInt();
            }
            Knapsack01 knapsack01 = new Knapsack01(n, capacity, cost, value);
            return knapsack01;
        }
        return null;
    }

    public static void main(String[] args) {
        Knapsack01 knapsack01 = null;
        while ((knapsack01 = readCase()) != null) {
            System.out.println(knapsack01.solve());
        }
    }

}
