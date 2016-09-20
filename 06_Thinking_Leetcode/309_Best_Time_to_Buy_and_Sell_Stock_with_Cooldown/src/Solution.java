
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
 * Result: AC, 1ms
 * TC: O(N)
 * SC: O(N)
 */
public class Solution {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int[] dpEmpty = new int[prices.length];
        int[] dpHeld = new int[prices.length];

        dpHeld[0] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dpEmpty[i] = Math.max(dpEmpty[i - 1], dpHeld[i - 1] + prices[i]);
            dpHeld[i] = Math.max(dpHeld[i - 1], (i >= 2 ? dpEmpty[i - 2] : 0) - prices[i]);
        }
        return dpEmpty[prices.length - 1];

    }
}
