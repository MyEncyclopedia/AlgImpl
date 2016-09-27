
/**
 * http://hihocoder.com/problemset/problem/1121
 */
/**
 * Result: AC
 * TC: O(N)
 * SC: O(N)
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static class BipartiteChecker {

        final int COLOR_BLACK = 1;
        final int COLOR_WHITE = -1;

        int[] vertColor;
        boolean isBipartite = true;
        int nextUncoloredVert;
        WeightedGraph graph;

        public BipartiteChecker(WeightedGraph graph) {
            this.graph = graph;
            vertColor = new int[graph.numVertex];

            int uncoloredVert;
            try {
                while ((uncoloredVert = nextUncoloredVert()) != -1) {
                    dfsColor(uncoloredVert, COLOR_BLACK);
                }
            } catch (Exception ex) {
                isBipartite = false;
            }
        }

        int nextUncoloredVert() {
            for (; nextUncoloredVert <= graph.numVertex; nextUncoloredVert++) {
                if (nextUncoloredVert == graph.numVertex) {
                    return -1;
                } else if (vertColor[nextUncoloredVert] == 0) {
                    return nextUncoloredVert;
                }
            }
            return nextUncoloredVert;
        }

        void dfsColor(int vert, int color) throws Exception {
            vertColor[vert] = color;
            Iterator<WeightedGraph.Edge> edgeIt = graph.getEdges(vert);
            while (edgeIt.hasNext()) {
                WeightedGraph.Edge e = edgeIt.next();
                int otherV = e.other(vert);
                if (vertColor[otherV] == 0) {
                    dfsColor(otherV, color * -1);
                } else if (vertColor[otherV] != color * -1) {
                    throw new Exception("Colored");
                }
            }
        }

        public boolean isBipartite() {
            return isBipartite;
        }

    }

    public static class WeightedGraph {

        int numVertex;
        int numEdge;

        List<Edge>[] srcEdges;

        @SuppressWarnings("unchecked")
        public WeightedGraph(int numV) {
            this.numVertex = numV;
            srcEdges = new List[numV];
            for (int i = 0; i < numV; i++) {
                srcEdges[i] = new ArrayList<Edge>();
            }
        }

        public void addEdge(int v1, int v2, int weight) {
            Edge e = new WeightedGraph.Edge(v1, v2, weight);
            srcEdges[v1].add(e);
            srcEdges[v2].add(e);
            numEdge++;
        }

        public Iterator<Edge> getEdges(int vert) {
            return srcEdges[vert].iterator();
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cases = scanner.nextInt();
        while (cases-- > 0) {
            int numVertex = scanner.nextInt();
            int numEdge = scanner.nextInt();
            WeightedGraph graph = new WeightedGraph(numVertex);
            while (numEdge-- > 0) {
                int srcV = scanner.nextInt() - 1;
                int targetV = scanner.nextInt() - 1;
                graph.addEdge(srcV, targetV, 1);
            }
            BipartiteChecker bipartite = new BipartiteChecker(graph);
            System.out.println(bipartite.isBipartite() ? "Correct" : "Wrong");

        }
    }
}
