
/**
 * http://hihocoder.com/problemset/problem/1067
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Main_Recurse {

    static class TarjanLCA {

        Node root;
        Map<String, Integer> nameToId;
        List<Node> nodeList;
        boolean[] checked;
        int[] ancestor;
        LinkedList<Query>[] queryArray;
        String[] results;
        static int nextNodeId = 0;
        static int nextQueryId = 0;
        UnionFind uf;

        @SuppressWarnings("unchecked")
        public TarjanLCA(int n) {
            nameToId = new HashMap<String, Integer>();
            nodeList = new ArrayList<Node>();
            queryArray = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                queryArray[i] = new LinkedList<Query>();
            }
            checked = new boolean[n];
            uf = new UnionFind(n);
            ancestor = new int[n];
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

        public void addQuery(String node1, String node2) {
            Query query = new Query(node2, nextQueryId++);
            queryArray[nameToId.get(node1)].add(query);
            if (!node1.equals(node2)) {
                query = new Query(node1, query.order);
                queryArray[nameToId.get(node2)].add(query);
            }
        }

        public String[] computeAnswers() {
            results = new String[nextQueryId];

            LCA(root);

            return results;
        }

        private void LCA(Node node) {
            int id = nameToId.get(node.name);
            ancestor[id] = id;
            for (Node child : node.children) {
                LCA(child);
                int childId = nameToId.get(child.name);
                uf.union(id, childId);
                ancestor[uf.find(childId)] = id;
            }
            checked[id] = true;

            ListIterator<Query> it = queryArray[id].listIterator();
            while (it.hasNext()) {
                Query q = it.next();
                int idOther = nameToId.get(q.nameOther);
                if (checked[idOther]) {
                    it.remove();
                    results[q.order] = nodeList.get(ancestor[uf.find(idOther)]).name;
                }
            }
        }

        static class Query {

            final String nameOther;
            int order;

            public Query(String name1, int order) {
                this.nameOther = name1;
                this.order = order;
            }
        }

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
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        TarjanLCA tarjan = new TarjanLCA(n + 1);
        while (n-- > 0) {
            String father = nextString();
            String son = nextString();
            tarjan.createChild(father, son);
        }
        int q = nextInt();
        while (q-- > 0) {
            String node1 = nextString();
            String node2 = nextString();
            tarjan.addQuery(node1, node2);
        }
        for (String lca : tarjan.computeAnswers()) {
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
