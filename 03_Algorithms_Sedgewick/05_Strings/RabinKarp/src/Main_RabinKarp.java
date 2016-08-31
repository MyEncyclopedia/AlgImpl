
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=ALDS1_14_B
 */
/**
 * Result: TLE, cannot pass large data
 * TC: O(N+K), N:length of text, K:length of pattern
 * SC: O(1)
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class Main_RabinKarp {

    public static class RabinKarp {

        private String pattern;
        private long patternHash;
        private int M;  // pattern length
        private long Q; // a large prime
        private int R = 256;  // alphabet size
        private long RM;      // R^(M-1)%Q

        public RabinKarp(String pattern) {
            this.pattern = pattern;
            M = pattern.length();
            Q = longRandomPrime();
            RM = 1;
            for (int i = 1; i <= M - 1; i++) {
                RM = (RM * R) % Q;
            }
            patternHash = hash(pattern, M);
        }

        /**
         *
         * @param txt
         * @return -1 when not found
         */
        public int search(String txt) {
            if (txt == null || txt.length() < M) {
                return -1;
            }
            long txtHash = hash(txt, M);
            if (txtHash == patternHash && check(txt, 0)) {
                return 0;
            }
            for (int i = M; i < txt.length(); i++) {
                // remove leading char, +Q to avoid overflow
                txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
                // add trailing char
                txtHash = (txtHash * R + txt.charAt(i)) % Q;
                if (txtHash == patternHash && check(txt, i - M + 1)) {
                    return i - M + 1;
                }
            }
            return -1;
        }

        public List<Integer> searchAll(String txt) {
            List<Integer> ret = new ArrayList<Integer>();
            if (txt == null || txt.length() < M) {
                return ret;
            }
            long txtHash = hash(txt, M);
            if (txtHash == patternHash && check(txt, 0)) {
                ret.add(0);
            }
            for (int i = M; i < txt.length(); i++) {
                // remove leading char, +Q to avoid overflow
                txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
                // add trailing char
                txtHash = (txtHash * R + txt.charAt(i)) % Q;
                if (txtHash == patternHash && check(txt, i - M + 1)) {
                    ret.add(i - M + 1);
                }
            }
            return ret;
        }

        private boolean check(String txt, int beginIdx) {
            for (int i = 0; i < M; i++) {
                if (txt.charAt(beginIdx + i) != pattern.charAt(i)) {
                    return false;
                }
            }
            return true;
        }

        private long hash(String key, int M) {
            long h = 0;
            for (int i = 0; i < M; i++) {
                h = (h * R + key.charAt(i)) % Q;
            }
            return h;
        }

        private long longRandomPrime() {
            return BigInteger.probablePrime(32, new Random()).longValue();
        }

    }

    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        String text = in.next();
        String pattern = in.next();

        RabinKarp rabinKarp = new RabinKarp(pattern);
        for (int pos : rabinKarp.searchAll(text)) {
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
