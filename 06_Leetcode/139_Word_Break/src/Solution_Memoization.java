
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
 * Result: AC, 6ms
 * TC: O(L*N), L: length of s, N: size of wordDict
 * SC: O(1)
 */
import java.util.HashMap;
import java.util.Set;

public class Solution_Memoization {

    private HashMap<String, Boolean> cache = new HashMap<String, Boolean>();

    public boolean wordBreak(String s, Set<String> wordDict) {
        if (s.isEmpty()) {
            return true;
        } else if (cache.get(s) != null) {
            return cache.get(s);
        }
        for (String dict : wordDict) {
            if (s.startsWith(dict) && wordBreak(s.substring(dict.length()), wordDict)) {
                cache.put(s, Boolean.TRUE);
                return true;
            }
        }
        cache.put(s, Boolean.FALSE);
        return false;
    }
}
