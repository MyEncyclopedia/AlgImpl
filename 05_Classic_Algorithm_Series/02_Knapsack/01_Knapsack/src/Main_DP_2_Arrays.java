
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=DPL_1_B
 */
/**
 * Result: AC
 * TC: O(N^2)
 * SC: O(2N)
 */
import java.util.Scanner;

public class Main_DP_2_Arrays {

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
            int[] rowPrev = new int[capacity + 1];
            int[] rowCurr = new int[capacity + 1];
            for (int numOfItems = 1; numOfItems <= n; numOfItems++) {
                int[] tmp = rowCurr;
                rowCurr = rowPrev;
                rowPrev = tmp;
                for (int weight = 1; weight <= capacity; weight++) {
                    if (weight < cost[numOfItems - 1]) {
                        rowCurr[weight] = rowPrev[weight];
                    } else {
                        rowCurr[weight] = Math.max(
                                rowPrev[weight - cost[numOfItems - 1]] + value[numOfItems - 1],
                                rowPrev[weight]);
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
