
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
 * TC: O(N^3)
 * SC: O(N)
 */
public class Solution_TLE_N3 {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int[] dp = new int[prices.length];
        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= i - 3; j++) {
                int lastDeal = maxProfit(prices, j + 2, i);
                dp[i] = Math.max(dp[i], dp[j] + lastDeal);
            }
            dp[i] = Math.max(dp[i], dp[i - 1]);
            dp[i] = Math.max(dp[i], maxProfit(prices, 0, i));
        }
        return dp[prices.length - 1];
    }

    private int maxProfit(int[] prices, int start, int end) {
        int ret = 0;
        int max = 0;
        for (int i = end; i >= start; i--) {
            max = Math.max(max, prices[i]);
            ret = Math.max(ret, max - prices[i]);
        }
        return ret;
    }
}
