
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_5_C
 */
/**
 * Result: AC
 * TC: O(N+Q)
 * SC: O(N+Q)
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class Main_NonRecurse {

    static class TarjanLCA {

        Node root;
        List<Node> nodeList;
        boolean[] checked;
        int[] ancestor;
        LinkedList<Query>[] queryArray;
        int[] results;
        UnionFind uf;
        static int nextQueryId = 0;

        @SuppressWarnings("unchecked")
        public TarjanLCA(int n) {
            nodeList = new ArrayList<Node>();
            for (int i = 0; i < n; i++) {
                nodeList.add(new Node(i));
            }
            root = nodeList.get(0);
            queryArray = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                queryArray[i] = new LinkedList<Query>();
            }
            checked = new boolean[n];
            uf = new UnionFind(n);
            ancestor = new int[n];
        }

        public void createChild(int parentId, int[] children) {
            Node parent = nodeList.get(parentId);
            for (int childId : children) {
                Node child = nodeList.get(childId);
                parent.children.add(child);
                child.parent = parent;
            }
        }

        public void addQuery(int node1, int node2) {
            Query query = new Query(node2, nextQueryId++);
            queryArray[node1].add(query);
            if (node1 != node2) {
                query = new Query(node1, query.order);
                queryArray[node2].add(query);
            }
        }

        public int[] computeAnswers() {
            results = new int[nextQueryId];
            boolean[] expanded = new boolean[nodeList.size()];
            Stack<Node> dfsStack = new Stack<Node>();
            dfsStack.push(root);

            while (!dfsStack.isEmpty()) {
                Node current = dfsStack.peek();
                if (!expanded[current.id]) {
                    ancestor[current.id] = current.id;
                    for (Node child : current.children) {
                        dfsStack.push(child);
                    }
                    expanded[current.id] = true;
                } else {
                    checked[current.id] = true;
                    ListIterator<Query> it = queryArray[current.id].listIterator();
                    while (it.hasNext()) {
                        Query q = it.next();
                        if (checked[q.idOther]) {
                            it.remove();
                            results[q.order] = nodeList.get(ancestor[uf.find(q.idOther)]).id;
                        }
                    }
                    
                    if (current.parent != null) {
                        int childId = current.id;
                        int parentId = current.parent.id;
                        uf.union(parentId, childId);
                        ancestor[uf.find(childId)] = parentId;
                    }

                    dfsStack.pop();
                }
            }

            return results;
        }

        static class Query {

            final int idOther;
            final int order;

            public Query(int idOther, int order) {
                this.idOther = idOther;
                this.order = order;
            }
        }

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
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        TarjanLCA tarjan = new TarjanLCA(n);
        for (int parentIdx = 0; parentIdx < n; parentIdx++) {
            int childNum = nextInt();
            int[] children = new int[childNum];
            for (int childIdx = 0; childIdx < childNum; childIdx++) {
                children[childIdx] = nextInt();
            }
            tarjan.createChild(parentIdx, children);
        }
        int q = nextInt();
        while (q-- > 0) {
            int node1 = nextInt();
            int node2 = nextInt();
            tarjan.addQuery(node1, node2);
        }
        for (int lca : tarjan.computeAnswers()) {
            out.println(lca);
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

    public static class UnionFind {

        int[] id;
        int count;
        int[] weight;  // size indexed by root id

        public UnionFind(int n) {
            id = new int[n];
            weight = new int[n];
            count = n;
            for (int idx = 0; idx < id.length; idx++) {
                id[idx] = idx;
                weight[idx] = 1;
            }
        }

        public void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) {
                return;
            }
            // make smaller root point to larger one
            if (weight[pRoot] < weight[qRoot]) {
                id[pRoot] = qRoot;
                weight[qRoot] += weight[pRoot];
            } else {
                id[qRoot] = pRoot;
                weight[pRoot] += weight[qRoot];
            }
            count--;
        }

        // path compression
        public int find(int p) {
            if (id[p] != p) {
                id[p] = find(id[p]);
            }
            return id[p];
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public int count() {
            return count;
        }
    }
}
