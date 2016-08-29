
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_4_B
 */
/**
 * Result: AC
 * TC: O(V+E)
 * SC: O(V)
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Main_Kahn {

    public static class TopologicalSort_Kahn {

        boolean hasCycle;
        LinkedList<Integer> sortedOrder;

        public TopologicalSort_Kahn(WeightedDigraph graph) {
            sortedOrder = new LinkedList<Integer>();
            TreeSet<Integer> zeroInVerts = new TreeSet<Integer>();
            int[] inEdge = new int[graph.numVertex];
            for (List<WeightedDigraph.Edge> edges : graph.srcEdges) {
                for (WeightedDigraph.Edge edge : edges) {
                    inEdge[edge.targetVertex]++;
                }
            }
            for (int v = 0; v < graph.numVertex; v++) {
                if (inEdge[v] == 0) {
                    zeroInVerts.add(v);
                }
            }

            while (!zeroInVerts.isEmpty()) {
                int vert = zeroInVerts.pollFirst();
                sortedOrder.add(vert);
                List<WeightedDigraph.Edge> edges = graph.srcEdges[vert];
                for (WeightedDigraph.Edge edge : edges) {
                    inEdge[edge.targetVertex]--;
                    if (inEdge[edge.targetVertex] == 0) {
                        zeroInVerts.add(edge.targetVertex);
                    }
                }
            }

            for (int v = 0; v < graph.numVertex; v++) {
                if (inEdge[v] != 0) {
                    sortedOrder.clear();
                    hasCycle = true;
                    return;
                }
            }

        }

        public boolean hasCycle() {
            return hasCycle;
        }

        public Iterator<Integer> sortedOrder() {
            return sortedOrder.iterator();
        }
    }

    public static class WeightedDigraph {

        int numVertex;
        int numEdge;

        List<Edge>[] srcEdges;

        @SuppressWarnings("unchecked")
        public WeightedDigraph(int numV, int numE) {
            this.numVertex = numV;
            this.numEdge = numE;
            srcEdges = new List[numV];

            for (int i = 0; i < numV; i++) {
                srcEdges[i] = new ArrayList<Edge>();
            }
        }

        public void addEdge(int srcVertex, int targetVertex, int weight) {
            Edge e = new WeightedDigraph.Edge(srcVertex, targetVertex, weight);
            srcEdges[srcVertex].add(e);
        }

        public static class Edge {

            int srcVertex;
            int targetVertex;
            int weight;

            public Edge(int srcVertex, int targetVertex, int weight) {
                this.srcVertex = srcVertex;
                this.targetVertex = targetVertex;
                this.weight = weight;
            }
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int v = in.nextInt();
        int e = in.nextInt();
        WeightedDigraph graph = new WeightedDigraph(v, e);
        while (e-- > 0) {
            graph.addEdge(in.nextInt(), in.nextInt(), 1);
        }
        TopologicalSort_Kahn topSort = new TopologicalSort_Kahn(graph);
        Iterator<Integer> it = topSort.sortedOrder();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
