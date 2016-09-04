
/**
 * https://leetcode.com/problems/word-break/
 *
 * Given a string s and a dictionary of words dict,
 * determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * For example, given
 * s = "leetcode",
 * dict = ["leet", "code"].
 *
 * Return true because "leetcode" can be segmented as "leet code".
 */
/**
 * Result: AC, 39ms
 * TC: O(L^2), L: length of s, N: size of wordDict
 * SC: O(L^2)
 */
import java.util.Set;

public class Solution_DP_2D_TopDown {

    private String s;
    private Set<String> dict;
    private Boolean[][] dp;

    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() == 0) {
            return false;
        }
        this.s = s;
        this.dict = dict;
        dp = new Boolean[s.length()][];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new Boolean[s.length()];
        }

        return search(0, s.length() - 1);
    }

    /**
     * start <= end <= s.length-1
     */
    private boolean search(int start, int end) {
        if (dp[start][end] != null) {
            return dp[start][end];
        }
        if (dict.contains(s.substring(start, end + 1))) {
            dp[start][end] = true;
            return true;
        } else if (start == end) {
            dp[start][end] = false;
            return false;
        }
        for (int idx = start; idx < end; idx++) {
            if (dp[start][idx] == null) {
                search(start, idx);
            }
            if (Boolean.TRUE.equals(dp[start][idx])) {
                if (dp[idx + 1][end] == null) {
                    search(idx + 1, end);
                }
                if (Boolean.TRUE.equals(dp[idx + 1][end])) {
                    dp[start][end] = true;
                    return true;
                }
            }
        }
        dp[start][end] = false;
        return false;
    }

}
