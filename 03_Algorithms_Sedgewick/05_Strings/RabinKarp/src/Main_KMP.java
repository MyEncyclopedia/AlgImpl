
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=ALDS1_14_B
 */
/**
 * Result: AC
 * TC: O(N+K), N:length of text, K:length of pattern
 * SC: O(K)
 */
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Main_KMP {

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

        public List<Integer> searchAll(String text) {
            List<Integer> ret = new ArrayList<Integer>();
            if (t.length == 0) {
                return ret;
            }
            int textIdx = 0, patternIdx = 0;
            while (textIdx < text.length()) {
                if (pattern.charAt(patternIdx) == text.charAt(textIdx)) {
                    if (patternIdx == pattern.length() - 1) {
                        ret.add(textIdx - patternIdx);
                        patternIdx = t[patternIdx];
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
            return ret;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        String text = in.next();
        String pattern = in.next();

        KMP kmp = new KMP(pattern);

        for (int pos : kmp.searchAll(text)) {
            out.println(pos);
        }
        out.flush();
    }

    static class InputReader {

        private InputStream in;
        private byte[] buffer = new byte[1024];
        private int curbuf;
        private int lenbuf;

        public InputReader(InputStream in) {
            this.in = in;
            this.curbuf = this.lenbuf = 0;
        }

        public boolean hasNextByte() {
            if (curbuf >= lenbuf) {
                curbuf = 0;
                try {
                    lenbuf = in.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0) {
                    return false;
                }
            }
            return true;
        }

        private int readByte() {
            if (hasNextByte()) {
                return buffer[curbuf++];
            } else {
                return -1;
            }
        }

        private boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }

        private void skip() {
            while (hasNextByte() && isSpaceChar(buffer[curbuf])) {
                curbuf++;
            }
        }

        public boolean hasNext() {
            skip();
            return hasNextByte();
        }

        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while (!isSpaceChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int c = readByte();
            while (isSpaceChar(c)) {
                c = readByte();
            }
            boolean minus = false;
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res = res * 10 + c - '0';
                c = readByte();
            } while (!isSpaceChar(c));
            return (minus) ? -res : res;
        }

        public long nextLong() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int c = readByte();
            while (isSpaceChar(c)) {
                c = readByte();
            }
            boolean minus = false;
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res = res * 10 + c - '0';
                c = readByte();
            } while (!isSpaceChar(c));
            return (minus) ? -res : res;
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextInt();
            }
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextLong();
            }
            return a;
        }

        public char[][] nextCharMap(int n, int m) {
            char[][] map = new char[n][m];
            for (int i = 0; i < n; i++) {
                map[i] = next().toCharArray();
            }
            return map;
        }
    }

}
