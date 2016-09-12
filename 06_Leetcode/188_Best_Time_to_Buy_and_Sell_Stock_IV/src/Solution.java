
/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete at most k transactions.
 *
 * Note:
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 */
/**
 * Result: AC, 7ms
 * TC: O(N * min(N/2, k))
 * SC: O(N * min(N/2, k))
 *
 */
public class Solution {

    class Pair {
        int taken;
        int notTaken;
        int result;
    }

    Pair[][] dp;
    int[] prices;
    int k;

    public int maxProfit(int k, int[] prices) {
        if (k == 0 || prices == null || prices.length <= 1) {
            return 0;
        }
        if (k >= prices.length / 2) {
            return solveMaxProfit(prices);
        }

        k = Math.min(k, prices.length / 2);

        dp = new Pair[prices.length + 1][];
        this.prices = prices;
        for (int i = 0; i <= prices.length; i++) {
            dp[i] = new Pair[k + 1];
        }

        return compute(0, k).result;
    }

    public Pair compute(int buyIdx, int transN) {
        Pair pair = dp[buyIdx][transN];
        if (pair != null) {
            return pair;
        }
        pair = new Pair();
        if (buyIdx == prices.length || buyIdx == prices.length - 1 || transN == 0) {
            pair.result = pair.taken = 0;
            dp[buyIdx][transN] = pair;
            return pair;
        }
        Pair prev = compute(buyIdx + 1, transN);
        pair.taken = prev.taken + prices[buyIdx + 1] - prices[buyIdx];
        int newTerm = prices[buyIdx + 1] - prices[buyIdx] + compute(buyIdx + 2, transN - 1).result;
        pair.taken = Math.max(pair.taken, newTerm);
        pair.notTaken = prev.result;
        pair.result = Math.max(pair.taken, pair.notTaken);

        dp[buyIdx][transN] = pair;
        return pair;
    }

    int solveMaxProfit(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; ++i) {
            if (prices[i] - prices[i - 1] > 0) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

}
