
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=DPL_1_B
 */
/**
 * Result: AC
 * TC: O(N^2)
 * SC: O(N^2)
 */

import java.util.Scanner;

public class Main_Memoization {

    static class Knapsack01 {

        int capacity;
        int n;
        int[] cost;
        int[] value;
        int[][] dp;

        public Knapsack01(int n, int capacity, int[] cost, int[] value) {
            this.n = n;
            this.capacity = capacity;
            this.cost = cost;
            this.value = value;
        }

        int solve() {
            dp = new int[n + 1][];
            for (int i = 0; i < n + 1; i++) {
                dp[i] = new int[capacity + 1];
                for (int j = 0; j < capacity + 1; j++) {
                    dp[i][j] = -1;
                }
            }
            return computeMemoization(0, capacity);
        }

        int computeMemoization(int itemIdx, int weight) {
            if (dp[itemIdx][weight] != -1) {
                return dp[itemIdx][weight];
            }
            int res;
            if (itemIdx == n) {
                res = 0;
            } else if (weight < cost[itemIdx]) {
                res = computeMemoization(itemIdx + 1, weight);
            } else {
                res = Math.max(computeMemoization(itemIdx + 1, weight),
                        computeMemoization(itemIdx + 1, weight - cost[itemIdx]) + value[itemIdx]);
            }
            dp[itemIdx][weight] = res;
            return res;
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
