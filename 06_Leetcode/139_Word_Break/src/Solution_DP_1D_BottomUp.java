
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
 * Result: AC, 13ms
 * TC: O(L^2), L: length of s, N: size of wordDict
 * SC: O(L)
 */
import java.util.Set;

public class Solution_DP_1D_BottomUp {

    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() == 0) {
            return false;
        }

        boolean[] dp = new boolean[s.length()];

        next_pos:
        for (int endIdx = 0; endIdx < s.length(); endIdx++) {
            String str = s.substring(0, endIdx + 1);
            if (dict.contains(str)) {
                dp[endIdx] = true;
                continue;
            }
            for (int midIdx = 1; midIdx <= endIdx; midIdx++) {
                if (dp[midIdx - 1] == true && dict.contains(s.substring(midIdx, endIdx + 1))) {
                    dp[endIdx] = true;
                    continue next_pos;
                }
            }
            dp[endIdx] = false;
        }
        return dp[s.length() - 1];
    }

}
