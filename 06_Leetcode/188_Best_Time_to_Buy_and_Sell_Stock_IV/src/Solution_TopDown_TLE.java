
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
 * Result: TLE
 * TC: O(N * N * min(N/2, k))
 * SC: O(N * min(N/2, k))
 * 
 */
public class Solution_TopDown_TLE {

    int[][] dp;
    int[] prices;

    public int maxProfit(int k, int[] prices) {
        if (k == 0 || prices == null || prices.length == 0) {
            return 0;
        }
        this.prices = prices;
        if (k >= prices.length) {
            return solveMaxProfit(0, prices.length);
        }
        dp = new int[prices.length + 1][];
        for (int i = 0; i <= prices.length; i++) {
            dp[i] = new int[k + 1];
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;
            }
        }
        return compute(0, k);
    }

    int solveMaxProfit(int start, int end) {
        int res = 0;
        for (int i = start + 1; i < end; ++i) {
            if (prices[i] - prices[i - 1] > 0) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    int compute(int buyIdx, int k) {
        if (buyIdx == prices.length || k == 0) {
            return 0;
        }
        if (dp[buyIdx][k] != -1) {
            return dp[buyIdx][k];
        }
        int atMostTransactions = (prices.length - buyIdx) / 2;
        if (atMostTransactions < k) {
            return compute(buyIdx, atMostTransactions);
        }
        dp[buyIdx][k] = 0;
        for (int sellDay = buyIdx + 1; sellDay < prices.length; sellDay++) {
            if (prices[sellDay] > prices[buyIdx]) {
                dp[buyIdx][k] = Math.max(dp[buyIdx][k],
                        prices[sellDay] - prices[buyIdx] + compute(sellDay + 1, k - 1));
            }
        }
        dp[buyIdx][k] = Math.max(dp[buyIdx][k], compute(buyIdx + 1, k));
        return dp[buyIdx][k];
    }
}
