
import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        String str = "leetcode";
        Set<String> wordDict = new HashSet<String>();
        wordDict.add("leeti");
        wordDict.add("code");

        Solution_DP_1D_TopDown s = new Solution_DP_1D_TopDown();
        System.out.println(s.wordBreak(str, wordDict));
    }

    public static void test2() {
        String str = "ab";
        Set<String> wordDict = new HashSet<String>();
        wordDict.add("a");
        wordDict.add("b");

        Solution_DP_1D_TopDown s = new Solution_DP_1D_TopDown();
        System.out.println(s.wordBreak(str, wordDict));
    }

}
