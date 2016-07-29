
public class KMP {

    private String strNeedle;
    private int[] table;

    public KMP1(String strNeedle) {
        this.strNeedle = strNeedle;
        if (strNeedle != null && strNeedle.length() > 1) {
            table = new int[strNeedle.length()];
            table[0] = -1;
            table[1] = 0;
            int pos = 2;
            int cnd = 0;
            while (pos < strNeedle.length()) {
                if (strNeedle.charAt(pos - 1) == strNeedle.charAt(cnd)) {
                    // case 1: the substring continues
                    cnd++;
                    table[pos] = cnd;
                    pos++;
                } else if (cnd > 0) {
                    // case 2: it doesn't, but we can fall back
                    cnd = table[cnd];
                } else {
                    // case 3: we have run out of candidates.  Note cnd = 0
                    table[pos] = 0;
                    pos++;
                }
            }
        }

    }

    public int match(String strHayStack) {
        int m = 0;
        int i = 0;
        while (m + i < strHayStack.length()) {
            if (strNeedle.charAt(i) == strHayStack.charAt(m + i)) {
                // case 1: the substring continues
                if (i == strNeedle.length() - 1) {
                    return m;
                }
                i++;
            } else {
                if (table[i] > -1) {
                    // case 2: fall back to shorter prefix
                    m = m + i - table[i];
                    i = table[i];
                } else {
                    // case 3: mismatch and table[i]==0
                    i = 0;
                    m = m + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        KMP1 k = new KMP1("abcababc");


    }
}
