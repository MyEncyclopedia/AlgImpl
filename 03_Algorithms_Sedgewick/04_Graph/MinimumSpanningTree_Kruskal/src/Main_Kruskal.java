
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_2_A
 */
/**
 * Result: AC
 * TC: O(E*log(E))
 * SC: O(E)
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main_Kruskal {

    public static class MinimalSpanningTree_Kruskal {

        int totalWeight;

        public MinimalSpanningTree_Kruskal(WeightedGraph graph) {
            // for performance reason, sort in original list
            List<WeightedGraph.Edge> edges = graph.getEdges();
            Collections.sort(edges, new Comparator<WeightedGraph.Edge>() {
                @Override
                public int compare(WeightedGraph.Edge o1, WeightedGraph.Edge o2) {
                    return o1.weight - o2.weight;
                }
            });

            UnionFind uf = new UnionFind(graph.numVertex);
            int edgeLeft = graph.numVertex - 1;
            for (WeightedGraph.Edge edge : edges) {
                int v1 = edge.v1;
                int v2 = edge.v2;
                if (!uf.connected(v1, v2)) {
                    totalWeight += edge.weight;
                    edgeLeft--;
                    uf.union(v1, v2);
                    if (edgeLeft == 0) {
                        break;
                    }
                }
            }
        }

        public Integer totalWeight() {
            return totalWeight;
        }

    }

    public static class WeightedGraph {

        int numVertex;
        int numEdge;

        List<Edge> edges;

        @SuppressWarnings("unchecked")
        public WeightedGraph(int numV) {
            this.numVertex = numV;
            edges = new ArrayList<Edge>();
        }

        public void addEdge(int v1, int v2, int weight) {
            Edge e = new WeightedGraph.Edge(v1, v2, weight);
            edges.add(e);
            numEdge++;
        }

        public List<Edge> getEdges() {
            return edges;
        }

        public static class Edge {

            int v1;
            int v2;
            int weight;

            public Edge(int v1, int v2, int weight) {
                this.v1 = v1;
                this.v2 = v2;
                this.weight = weight;
            }

            public int other(int vertex) {
                return v1 == vertex ? v2 : v1;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Edge) {
                    final Edge other = (Edge) obj;
                    if (this.weight == other.weight) {
                        if ((v1 == other.v1 && v2 == other.v2) || (v2 == other.v1 && v1 == other.v2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numVertex = scanner.nextInt();
        int numEdge = scanner.nextInt();
        WeightedGraph graph = new WeightedGraph(numVertex);
        while (numEdge-- > 0) {
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(v1, v2, weight);
        }

        MinimalSpanningTree_Kruskal mst = new MinimalSpanningTree_Kruskal(graph);
        System.out.println(mst.totalWeight());
    }
}
