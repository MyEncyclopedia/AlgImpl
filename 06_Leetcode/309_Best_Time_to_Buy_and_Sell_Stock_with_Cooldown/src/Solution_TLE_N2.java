
/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
 * (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
 *
 * You may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
 * Example:
 *
 * prices = [1, 2, 3, 0, 2]
 * maxProfit = 3
 * transactions = [buy, sell, cooldown, buy, sell]
 */
/**
 * Result: TLE
 * TC: O(N^2)
 * SC: O(N)
 */
public class Solution_TLE_N2 {

    int[][] maxProfitCache;

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        maxProfitCache = new int[prices.length][];
        for (int i = 0; i < prices.length; i++) {
            maxProfitCache[i] = new int[prices.length];
        }
        for (int end = prices.length - 1; end >= 0; end--) {
            int max = 0;
            int maxProfit = 0;
            for (int start = end; start >= 0; start--) {
                max = Math.max(max, prices[start]);
                maxProfit = Math.max(maxProfit, max - prices[start]);
                maxProfitCache[start][end] = maxProfit;
            }
        }
        int[] dp = new int[prices.length];
        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= i - 3; j++) {
                int lastDeal = maxProfitCache[j + 2][i];
                dp[i] = Math.max(dp[i], dp[j] + lastDeal);
            }
            dp[i] = Math.max(dp[i], dp[i - 1]);
            dp[i] = Math.max(dp[i], maxProfitCache[0][i]);
        }
        return dp[prices.length - 1];
    }

}
