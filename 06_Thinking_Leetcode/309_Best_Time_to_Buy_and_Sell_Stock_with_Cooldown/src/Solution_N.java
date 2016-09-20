
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
 * Result: AC, 6ms
 * TC: O(N)
 * SC: O(N)
 */
public class Solution_N {

    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }

        int[] dp = new int[prices.length];
        int preIminus3Max = 0;
        int preIminus3BuyPrice = 0;
        int preIminus3SellPrice = -1;
        for (int i = 1; i < prices.length; i++) {
            if (i >= 0) {
                // try to buy previous day and sell today
                int sellTodayBuyYesterday = 0;
                if (prices[i] > prices[i - 1]) {
                    sellTodayBuyYesterday = prices[i] - prices[i - 1] + (i >= 3 ? dp[i - 3] : 0);
                }
                int preIminus3Updated = preIminus3SellPrice != -1 && prices[i] > preIminus3SellPrice
                        ? (prices[i] - preIminus3SellPrice) + preIminus3Max : preIminus3Max;
                if (prices[i] > preIminus3SellPrice) {
                    preIminus3SellPrice = prices[i];
                }
                preIminus3Max = Math.max(sellTodayBuyYesterday, preIminus3Updated);
                preIminus3BuyPrice = sellTodayBuyYesterday > preIminus3Updated ? prices[i - 1] : preIminus3BuyPrice;
                preIminus3SellPrice = sellTodayBuyYesterday > preIminus3Updated ? prices[i] : preIminus3SellPrice;
            }
            int dp_i_1 = i - 1 >= 0 ? dp[i - 1] : 0;
            int dp_i_2 = i - 2 >= 0 ? dp[i - 2] : 0;
            dp[i] = Math.max(dp_i_1, dp_i_2);
            dp[i] = Math.max(dp[i], preIminus3Max);
        }

        return dp[prices.length - 1];
    }
}
