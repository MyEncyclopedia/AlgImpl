
/**
 * http://hihocoder.com/problemset/problem/1122
 */
/**
 * Result: AC
 * TC: O(VE)
 * SC: O(V)
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static class BipartiteMatch_Hungarian {

        WeightedGraph graph;
        int maxMatch;
        int[] match;

        public BipartiteMatch_Hungarian(WeightedGraph graph) {
            this.graph = graph;

            match = new int[graph.numVertex];
            boolean[] used = new boolean[graph.numVertex];
            Arrays.fill(match, -1);
            for (int vert = 0; vert < graph.numVertex; vert++) {
                if (match[vert] < 0) {
                    Arrays.fill(used, false);
                    if (dfs(vert, used)) {
                        maxMatch++;
                    }
                }
            }
        }

        private boolean dfs(int vert, boolean[] used) {
            used[vert] = true;
            Iterator<WeightedGraph.Edge> edgeIt = graph.getEdges(vert);
            while (edgeIt.hasNext()) {
                WeightedGraph.Edge e = edgeIt.next();
                int pairVert = e.other(vert);
                int ppVert = match[pairVert];
                if (ppVert < 0 || !used[ppVert] && dfs(ppVert, used)) {
                    match[vert] = pairVert;
                    match[pairVert] = vert;
                    return true;
                }
            }
            return false;
        }

        public int getMaxMatch() {
            return maxMatch;
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
        int numVertex = scanner.nextInt();
        int numEdge = scanner.nextInt();
        WeightedGraph graph = new WeightedGraph(numVertex);
        while (numEdge-- > 0) {
            int v1 = scanner.nextInt() - 1;
            int v2 = scanner.nextInt() - 1;
            graph.addEdge(v1, v2, 1);
        }
        BipartiteMatch_Hungarian bipartiteMatch = new BipartiteMatch_Hungarian(graph);
        System.out.println(bipartiteMatch.getMaxMatch());
    }
}
