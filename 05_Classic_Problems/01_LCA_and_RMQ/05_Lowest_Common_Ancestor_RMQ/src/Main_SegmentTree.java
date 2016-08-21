
/**
 * http://hihocoder.com/problemset/problem/1069
 */
/**
 * Result: AC
 * TC: Preprocess: O(N*log(N))
 *     Query:      O(Q*log(N))
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

public class Main_SegmentTree {

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
        SegmentTree rmq;

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

            rmq = new SegmentTree(level);
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

    public static class SegmentTree {

        int[] tree;
        int[] raw;
        int rawLen;

        public SegmentTree(int[] arr) {
            int len = Integer.highestOneBit(arr.length) << 2;
            tree = new int[len];
            raw = arr;
            rawLen = raw.length;
            initTree(1, 0, arr.length - 1);
        }

        /**
         * @param treeNodeIdx of tree[]
         * @param rangeBegin of raw[]
         * @param rangeEnd of raw[]
         */
        private void initTree(int treeNodeIdx, int rangeBegin, int rangeEnd) {
            if (rangeBegin == rangeEnd) {
                tree[treeNodeIdx] = raw[rangeBegin];
            } else {
                initTree(treeNodeIdx * 2, rangeBegin, (rangeEnd + rangeBegin) / 2);
                initTree(treeNodeIdx * 2 + 1, (rangeEnd + rangeBegin) / 2 + 1, rangeEnd);
                tree[treeNodeIdx] = tree[treeNodeIdx * 2] < tree[treeNodeIdx * 2 + 1] ? tree[treeNodeIdx * 2] : tree[treeNodeIdx * 2 + 1];
            }
        }

        /**
         *
         * @param left
         * @param right
         * @return index not value
         */
        public int query(int left, int right) {
            return query(1, 0, rawLen - 1, left, right);
        }

        /**
         * Invariant: rangeBegin <= left <= right <= rangeEnd
         * @
         *
         * param treeNodeIdx treeNodeIdx of tree[]
         * @param rangeBegin
         * @param rangeEnd
         * @param left
         * @param right
         * @return index to raw array
         */
        private int query(int treeNodeIdx, int rangeBegin, int rangeEnd, int left, int right) {
            Integer leftResult = null;
            Integer rightResult = null;
            if (rangeBegin == rangeEnd) {
                return rangeBegin;
            }
            int rangeMid = (rangeBegin + rangeEnd) / 2;
            if (left <= rangeMid) {
                leftResult = query(treeNodeIdx * 2, rangeBegin, rangeMid, left, Math.min(right, rangeMid));
            }
            if (rangeMid + 1 <= right) {
                rightResult = query(treeNodeIdx * 2 + 1, rangeMid + 1, rangeEnd, Math.max(left, rangeMid + 1), right);
            }
            if (leftResult == null) {
                return rightResult;
            } else if (rightResult == null) {
                return leftResult;
            } else {
                return raw[leftResult] < raw[rightResult] ? leftResult : rightResult;
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
