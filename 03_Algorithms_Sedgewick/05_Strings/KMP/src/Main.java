
import java.io.IOException;
import java.util.Scanner;

/**
 * http://hihocoder.com/problemset/problem/1015
 */
/**
 * Result: AC
 * TC: O(N+K), N:length of text, K:length of pattern
 * SC: O(K)
 */
public class Main {

    public static class KMP {

        String pattern;
        int[] t;

        public KMP(String pattern) {
            this.pattern = pattern;
            buildTable();
        }

        void buildTable() {
            t = new int[pattern.length()];
            switch (t.length) {
                case 2:
                    t[1] = 0;
                case 1:
                    t[0] = -1;
                case 0:
                    return;
            }

            t[0] = -1;
            t[1] = 0;
            int pos = 2;
            int cnd = 0;
            while (pos < pattern.length()) {
                if (pattern.charAt(pos - 1) == pattern.charAt(cnd)) {
                    // case 1: the substring continues
                    cnd++;
                    t[pos] = cnd;
                    pos++;
                } else if (cnd > 0) {
                    // case 2: it doesn't, but we can fall back
                    cnd = t[cnd];
                } else {
                    // case 3: we have run out of candidates.  Note cnd = 0
                    t[pos] = 0;
                    pos++;
                }
            }
        }

        public int searchAll(String text) {
            int total = 0;
            if (t.length == 0) {
                return 0;
            }
            int textIdx = 0, patternIdx = 0;
            while (textIdx < text.length()) {
                if (pattern.charAt(patternIdx) == text.charAt(textIdx)) {
                    if (patternIdx == pattern.length() - 1) {
                        patternIdx = t[patternIdx];
                        total++;
                    } else { // continue to match next
                        textIdx++;
                        patternIdx++;
                    }
                } else { // fallback
                    patternIdx = t[patternIdx];
                }
                if (patternIdx == -1) {
                    textIdx++;
                    patternIdx++;
                }
            }
            return total;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        for (int i = 0; i < n; i++) {
            String pattern = in.nextLine();
            String text = in.nextLine();
            KMP kmp = new KMP(pattern);

            int total = kmp.searchAll(text);
            System.out.println(total);
        }
    }
}
