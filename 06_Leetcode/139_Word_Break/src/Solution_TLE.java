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
 * Result: TLE
 * TC: O(N^L), L: length of s, N: size of wordDict
 * SC: O(1)
 */
import java.util.Set;

public class Solution_TLE {

    public boolean wordBreak(String s, Set<String> wordDict) {
        if (s.isEmpty()) {
            return true;
        }
        for (String dict : wordDict) {
            if (s.startsWith(dict) && wordBreak(s.substring(dict.length()), wordDict)) {
                return true;
            }
        }
        return false;
    }
}