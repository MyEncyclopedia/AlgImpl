
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=DPL_1_C
 */
/**
 * Result: TLE
 * TC: O(N*W^2)
 * SC: O(2N)
 */
import java.util.Scanner;

public class Main_DP_NWW_2_Arrays {

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
            int[] rowPrev;
            rowCurr = new int[capacity + 1];
            rowPrev = new int[capacity + 1];
            for (int numOfItems = 1; numOfItems <= n; numOfItems++) {
                int[] tmp = rowCurr;
                rowCurr = rowPrev;
                rowPrev = tmp;
                int costThis = cost[numOfItems - 1];
                for (int weight = 1; weight <= capacity; weight++) {
                    rowCurr[weight] = rowPrev[weight];
                    for (int num = 1; weight >= costThis * num; num++) {
                        rowCurr[weight] = Math.max(rowCurr[weight],
                                rowPrev[weight - costThis * num] + value[numOfItems - 1] * num);
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
        KnapsackComplete knapsack = null;
        while ((knapsack = readCase()) != null) {
            System.out.println(knapsack.solve());
        }
    }

}
