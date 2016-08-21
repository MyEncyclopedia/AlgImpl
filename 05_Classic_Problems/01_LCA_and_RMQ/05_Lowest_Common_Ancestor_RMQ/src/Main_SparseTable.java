
/**
 * http://hihocoder.com/problemset/problem/1069
 */
/**
 * Result: AC
 * TC: Preprocess: O(N*log(N))
 *     Query:      O(Q)
 * SC: O(N*log(N))
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Main_SparseTable {

    static class Node {

        String name;
        Node parent;
        List<Node> children;

        public Node(String name, Node parent) {
            this.name = name;
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
        Map<String, Integer> nameToId;
        static int nextNodeId = 0;
        int n;

        public TreeHelper(int n) {
            this.n = n;
            nameToId = new HashMap<String, Integer>();
            nodeList = new ArrayList<Node>();
        }

        public void createChild(String parentName, String childName) {
            if (root == null) {
                root = new Node(parentName, null);
                nameToId.put(parentName, nextNodeId++);
                nodeList.add(root);
            }
            Node parent = nodeList.get(nameToId.get(parentName));
            Node child = new Node(childName, parent);
            nameToId.put(childName, nextNodeId++);
            nodeList.add(child);
        }

        public int getNodeIdx(String name) {
            return nameToId.get(name);
        }

        public Node getNodeOf(String name) {
            Integer id = nameToId.get(name);
            return nodeList.get(id);
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
                    int childIdx = treeHelper.getNodeIdx(child.name);
                    euler[visitIdx] = childIdx;
                    level[visitIdx] = depth;
                    h[childIdx] = visitIdx;
                } else {
                    depth--;
                    dfsStack.pop();
                    if (!dfsStack.isEmpty()) {
                        visitIdx++;
                        euler[visitIdx] = treeHelper.getNodeIdx(dfsStack.peek().node.name);
                        level[visitIdx] = depth;
                    }
                }
            }

            rmq = new RMQ_SparseTable(level);
        }

        public Node lca(Node n1, Node n2) {
            int n1Idx = h[treeHelper.getNodeIdx(n1.name)];
            int n2Idx = h[treeHelper.getNodeIdx(n2.name)];
            int leftVisitIdx = Math.min(n1Idx, n2Idx);
            int rightVisitIdx = Math.max(n1Idx, n2Idx);

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
         *
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
        TreeHelper treeHelper = new TreeHelper(n + 1);
        while (n-- > 0) {
            String father = nextString();
            String son = nextString();
            treeHelper.createChild(father, son);
        }
        ReductionToRMQ rmqSolver = new ReductionToRMQ(treeHelper);
        int q = nextInt();
        while (q-- > 0) {
            String name1 = nextString();
            String name2 = nextString();
            Node n1 = treeHelper.getNodeOf(name1);
            Node n2 = treeHelper.getNodeOf(name2);
            out.println(rmqSolver.lca(n1, n2).name);
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
