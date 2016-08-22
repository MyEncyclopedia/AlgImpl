
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_5_C
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
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Main_ReduceToRMQ_SegmentTree {

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
        SegmentTree segTree;

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
            segTree = new SegmentTree(level);
        }

        public Node lca(Node n1, Node n2) {
            int n1VisitIdx = h[n1.id];
            int n2VisitIdx = h[n2.id];
            int leftVisitIdx = Math.min(n1VisitIdx, n2VisitIdx);
            int rightVisitIdx = Math.max(n1VisitIdx, n2VisitIdx);
            int minIdx = segTree.queryIndex(leftVisitIdx, rightVisitIdx);
            int lcaIdx = euler[minIdx];
            return treeHelper.nodeList.get(lcaIdx);
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
        int n;
        int[] rawArray;

        public SegmentTree(int[] rawArray) {
            n = 1;
            while (n < rawArray.length) {
                n <<= 1;
            }
            tree = new int[2 * n - 1];
            this.rawArray = rawArray;
            initTree(0, 0, n - 1, rawArray);
        }

        /**
         * @param idx of tree[]
         * @param rangeBegin of raw[]
         * @param rangeEnd of raw[]
         */
        private void initTree(int idx, int rangeBegin, int rangeEnd, int[] rawArray) {
            if (rangeBegin >= rawArray.length) {
                return;
            }
            if (rangeBegin == rangeEnd) {
                tree[idx] = rangeBegin;
            } else {
                initTree(idx * 2 + 1, rangeBegin, (rangeEnd + rangeBegin) / 2, rawArray);
                initTree(idx * 2 + 2, (rangeEnd + rangeBegin) / 2 + 1, rangeEnd, rawArray);
                tree[idx] = rawArray[tree[idx * 2 + 1]] < rawArray[tree[idx * 2 + 2]] ? tree[idx * 2 + 1] : tree[idx * 2 + 2];
            }
        }

        /**
         *
         * @param left
         * @param right
         * @return index of min
         */
        public int queryIndex(int left, int right) {
            return queryIndex(0, 0, n - 1, left, right);
        }

        public void update(int idx, int newValue) {
            rawArray[idx] = newValue;
            int treeIdx = idx + n - 1;
            tree[treeIdx] = idx;
            while (treeIdx > 0) {
                treeIdx = (treeIdx - 1) / 2;
                tree[treeIdx] = rawArray[tree[treeIdx * 2 + 1]] < rawArray[tree[treeIdx * 2 + 2]]
                        ? tree[treeIdx * 2 + 1] : tree[treeIdx * 2 + 2];
            }
        }

        /**
         * Invariant: rangeBegin <= left <= right <= rangeEnd
         */
        private int queryIndex(int idx, int rangeBegin, int rangeEnd, int left, int right) {
            int leftMinIdx = -1;
            int rightMinIdx = -1;
            if (rangeBegin == rangeEnd) {
                return tree[idx];
            }
            if (left == rangeBegin && right == rangeEnd) {
                return tree[idx];
            }
            int rangeMid = (rangeBegin + rangeEnd) / 2;
            if (left <= rangeMid) {
                leftMinIdx = queryIndex(idx * 2 + 1, rangeBegin, rangeMid, left, Math.min(right, rangeMid));
            }
            if (rangeMid + 1 <= right) {
                rightMinIdx = queryIndex(idx * 2 + 2, rangeMid + 1, rangeEnd, Math.max(left, rangeMid + 1), right);
            }

            if (leftMinIdx == -1) {
                return rightMinIdx;
            } else if (rightMinIdx == -1) {
                return leftMinIdx;
            } else {
                return rawArray[leftMinIdx] < rawArray[rightMinIdx] ? leftMinIdx : rightMinIdx;
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
