
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
 * Result: AC, 5ms
 * TC: O(L*N), L: length of s, N: size of wordDict
 * SC: O(L)
 */
import java.util.Set;

public class Solution_DP_1D_TopDown {

    Boolean[] dp;

    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() == 0) {
            return false;
        }
        dp = new Boolean[s.length()];
        return search(0, s, dict);
    }

    public boolean search(int startIdx, String s, Set<String> set) {
        if (startIdx == s.length()) {
            return true;
        }
        if (dp[startIdx] != null) {
            return dp[startIdx];
        }
        String leftStr = s.substring(startIdx, s.length());
        boolean found = false;
        for (String str : set) {
            if (leftStr.startsWith(str)) {
                found = search(startIdx + str.length(), s, set);
            }
            if (found) {
                dp[startIdx] = true;
                return dp[startIdx];
            }
        }
        dp[startIdx] = false;
        return dp[startIdx];
    }

}
