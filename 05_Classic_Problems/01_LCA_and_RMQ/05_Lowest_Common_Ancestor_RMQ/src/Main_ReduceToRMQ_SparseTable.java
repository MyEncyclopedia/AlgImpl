
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_5_C
 */
/**
 * Result: AC
 * TC: Preprocess: O(2N*log(2N))
 *     Query:      O(Q*1)
 * SC: O(2N*log(2N))
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Main_ReduceToRMQ_SparseTable {

    static class Node {

        int id;
        Node parent;
        List<Node> children;

        public Node(int id) {
            children = new ArrayList<Node>();
            this.id = id;
        }

        public Node(int id, Node parent) {
            this.id = id;
            this.parent = parent;
            children = new ArrayList<Node>();
            if (parent != null) {
                parent.children.add(this);
            }
        }
    }

    static class TreeHelper {

        Node root;
        List<Node> nodeList;
        int n;

        public TreeHelper(int n) {
            nodeList = new ArrayList<Node>();
            for (int i = 0; i < n; i++) {
                nodeList.add(new Node(i));
            }
            root = nodeList.get(0);
            this.n = n;
        }

        public void createChild(int parentId, int[] children) {
            Node parent = nodeList.get(parentId);
            for (int childId : children) {
                Node child = nodeList.get(childId);
                parent.children.add(child);
                child.parent = parent;
            }
        }

        public Node getNodeAt(int idx) {
            return nodeList.get(idx);
        }
    }

    static class ReductionToRMQ {

        int[] euler;
        int[] level;
        int[] h;
        TreeHelper treeHelper;
        RMQ_SparseTable rmq;

        public ReductionToRMQ(TreeHelper treeHelper) {
            this.treeHelper = treeHelper;
            int n = treeHelper.n;
            euler = new int[2 * n - 1];
            level = new int[2 * n - 1];
            h = new int[n];
            int depth = 0;
            int visitIdx = 0;

            Stack<NodeWithIterator> dfsStack = new Stack<NodeWithIterator>();
            dfsStack.push(new NodeWithIterator(treeHelper.root));
            while (!dfsStack.isEmpty()) {
                Iterator<Node> it = dfsStack.peek().it;
                if (it.hasNext()) {
                    Node child = it.next();
                    dfsStack.push(new NodeWithIterator(child));
                    depth++;
                    visitIdx++;
                    euler[visitIdx] = child.id;
                    level[visitIdx] = depth;
                    h[child.id] = visitIdx;
                } else {
                    dfsStack.pop();
                    depth--;
                    if (!dfsStack.isEmpty()) {
                        visitIdx++;
                        euler[visitIdx] = dfsStack.peek().node.id;
                        level[visitIdx] = depth;
                    }
                }
            }
            rmq = new RMQ_SparseTable(level);
        }

        public Node lca(Node n1, Node n2) {
            int n1VisitIdx = h[n1.id];
            int n2VisitIdx = h[n2.id];
            int leftVisitIdx = Math.min(n1VisitIdx, n2VisitIdx);
            int rightVisitIdx = Math.max(n1VisitIdx, n2VisitIdx);

            int minVisitIdx = rmq.query(leftVisitIdx, rightVisitIdx);
            int lcaNodeIdx = euler[minVisitIdx];
            return treeHelper.nodeList.get(lcaNodeIdx);
        }

        class NodeWithIterator {

            final Node node;
            final Iterator<Node> it;

            public NodeWithIterator(Node node) {
                this.node = node;
                it = node.children.iterator();
            }
        }
    }

    public static class RMQ_SparseTable {

        private final int cache[][];
        private final int[] rawArray;

        public RMQ_SparseTable(int[] a) {
            rawArray = a;
            cache = new int[a.length][];
            for (int i = 0; i < a.length; i++) {
                int len = a.length - i;
                len = (int) (Math.log(len) / Math.log(2)) + 1;
                cache[i] = new int[len];
                cache[i][0] = i;
            }

            for (int power = 1; power < cache[0].length; power++) {
                for (int idx = 0; idx < a.length && power < cache[idx].length; idx++) {
                    if (a[cache[idx][power - 1]] < a[cache[idx + (1 << (power - 1))][power - 1]]) {
                        cache[idx][power] = cache[idx][power - 1];
                    } else {
                        cache[idx][power] = cache[idx + (1 << (power - 1))][power - 1];
                    }
                }
            }

        }

        /**
         * left <= right
         * @param left zero-based
         * @param right zero-based
         * @return index to input array
         */
        public int query(int left, int right) {
            int maxPower = (int) (Math.log(right - left + 1) / Math.log(2));
            if (rawArray[cache[left][maxPower]] < rawArray[cache[right - (1 << maxPower) + 1][maxPower]]) {
                return cache[left][maxPower];
            } else {
                return cache[right - (1 << maxPower) + 1][maxPower];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        TreeHelper treeHelper = new TreeHelper(n);
        for (int parentIdx = 0; parentIdx < n; parentIdx++) {
            int childNum = nextInt();
            int[] children = new int[childNum];
            for (int childIdx = 0; childIdx < childNum; childIdx++) {
                children[childIdx] = nextInt();
            }
            treeHelper.createChild(parentIdx, children);
        }
        ReductionToRMQ rmqSolver = new ReductionToRMQ(treeHelper);

        int q = nextInt();
        while (q-- > 0) {
            int id1 = nextInt();
            int id2 = nextInt();
            out.println(rmqSolver.lca(treeHelper.getNodeAt(id1), treeHelper.getNodeAt(id2)).id);
        }

        out.flush();
    }

    private static final StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    public static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }

}
