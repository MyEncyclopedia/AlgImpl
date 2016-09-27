
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_1_C
 */
/**
 * Result: AC
 * TC: O(V^3)
 * SC: O(V^2)
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main_FloydWarshall {

    public static class FloydWarshallShortestPath {

        Integer[][] dist; // index is vertex
        boolean hasNegativeCycle;
        WeightedDigraph graph;

        public FloydWarshallShortestPath(WeightedDigraph graph) {
            this.graph = graph;
            dist = new Integer[graph.numVertex][];
            for (int i = 0; i < graph.numVertex; i++) {
                dist[i] = new Integer[graph.numVertex];
            }

            for (int i = 0; i < graph.numVertex; i++) {
                dist[i][i] = 0;
            }
            for (WeightedDigraph.Edge edge : graph.edges) {
                dist[edge.srcVertex][edge.targetVertex] = edge.weight;
            }
            for (int k = 0; k < graph.numVertex; k++) {
                for (int i = 0; i < graph.numVertex; i++) {
                    for (int j = 0; j < graph.numVertex; j++) {
                        if (dist[i][k] != null && dist[k][j] != null
                                && (dist[i][j] == null || dist[i][k] + dist[k][j] < dist[i][j])) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                        }
                    }
                }
            }

            for (int i = 0; i < graph.numVertex; i++) {
                if (dist[i][i] < 0) {
                    hasNegativeCycle = true;
                    break;
                }
            }
        }

        public boolean hasNegativeCycle() {
            return hasNegativeCycle;
        }

        /**
         *
         * @param srcVertex
         * @param destVertex
         * @return null meaning INF
         */
        public Integer shortestPath(int srcVertex, int destVertex) {
            return dist[srcVertex][destVertex];
        }

    }

    public static class WeightedDigraph {

        int numVertex;
        List<Edge>[] srcEdges;
        List<Edge> edges;

        @SuppressWarnings("unchecked")
        public WeightedDigraph(int numV) {
            this.numVertex = numV;
            srcEdges = new List[numV];
            edges = new ArrayList<Edge>();
            for (int i = 0; i < numV; i++) {
                srcEdges[i] = new ArrayList<Edge>();
            }
        }

        public void addEdge(int srcVertex, int targetVertex, int weight) {
            Edge e = new WeightedDigraph.Edge(srcVertex, targetVertex, weight);
            srcEdges[srcVertex].add(e);
            edges.add(e);
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
        Scanner scanner = new Scanner(System.in);
        int numVertex = scanner.nextInt();
        int numEdge = scanner.nextInt();
        WeightedDigraph graph = new WeightedDigraph(numVertex);
        while (numEdge-- > 0) {
            int srcV = scanner.nextInt();
            int targetV = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(srcV, targetV, weight);
        }
        FloydWarshallShortestPath floydWarshall = new FloydWarshallShortestPath(graph);
        if (floydWarshall.hasNegativeCycle()) {
            System.out.println("NEGATIVE CYCLE");
        } else {
            for (int srcVert = 0; srcVert < graph.numVertex; srcVert++) {
                for (int destVert = 0; destVert < graph.numVertex; destVert++) {
                    Integer d = floydWarshall.shortestPath(srcVert, destVert);
                    System.out.print(d == null ? "INF" : d);
                    if (destVert != graph.numVertex - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println("");
            }
        }
    }

}
