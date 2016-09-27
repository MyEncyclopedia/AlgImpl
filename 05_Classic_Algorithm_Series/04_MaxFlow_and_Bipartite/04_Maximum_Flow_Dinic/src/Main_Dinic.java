
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_6_A
 */
/**
 * Result: AC
 * TC: O(E*V^2)
 * SC: O(V+E)
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main_Dinic {

    public static class Dinic {

        final ResidualNetwork residualG;
        final int srcVert;
        final int termVert;
        int[] level;
        int[] iter;

        public Dinic(WeightedDigraph graph, int srcVert, int termVert) {
            this.residualG = new ResidualNetwork(graph);
            this.srcVert = srcVert;
            this.termVert = termVert;
        }

        public int computeMaxFlow() {
            level = new int[residualG.numVertex];
            iter = new int[residualG.numVertex];
            int maxFlow = 0;
            for (;;) {
                bfs();
                if (level[termVert] < 0) {
                    return maxFlow;
                }
                Arrays.fill(iter, 0);
                int flow = 0;
                while ((flow = dfs(srcVert, termVert, Integer.MAX_VALUE)) > 0) {
                    maxFlow += flow;
                }
            }
        }

        private void bfs() {
            Arrays.fill(level, -1);
            Queue<Integer> queue = new LinkedList<Integer>();
            level[srcVert] = 0;
            queue.add(srcVert);
            while (!queue.isEmpty()) {
                int v = queue.remove();
                for (WeightedDigraph.Edge edge : residualG.srcEdges[v]) {
                    if (edge.weight > 0 && level[edge.targetVertex] < 0) {
                        level[edge.targetVertex] = level[v] + 1;
                        queue.add(edge.targetVertex);
                    }
                }
            }
        }

        private int dfs(int v, int t, int flow) {
            if (v == t) {
                return flow;
            }
            for (WeightedDigraph.Edge edge : residualG.srcEdges[v]) {
                if (edge.weight > 0 && level[v] < level[edge.targetVertex]) {
                    int minFlow = dfs(edge.targetVertex, t, Math.min(flow, edge.weight));
                    if (minFlow > 0) {
                        edge.weight -= minFlow;
                        residualG.edgeMap.get(edge).weight += minFlow;
                        return minFlow;
                    }
                }
            }
            return 0;
        }
    }

    public static class ResidualNetwork extends WeightedDigraph {

        HashMap<Edge, Edge> edgeMap;

        public ResidualNetwork(WeightedDigraph graph) {
            super(graph.numVertex);
            edgeMap = new HashMap<Edge, Edge>();
            for (int i = 0; i < graph.numVertex; i++) {
                for (WeightedDigraph.Edge edge : graph.srcEdges[i]) {
                    this.addEdge(edge.srcVertex, edge.targetVertex, edge.weight);
                }
            }
        }

        @Override
        public void addEdge(int srcVertex, int targetVertex, int weight) {
            Edge e = new WeightedDigraph.Edge(srcVertex, targetVertex, weight);
            srcEdges[srcVertex].add(e);
            Edge backE = new WeightedDigraph.Edge(targetVertex, srcVertex, 0);
            srcEdges[targetVertex].add(backE);
            edgeMap.put(e, backE);
            edgeMap.put(backE, e);
        }
    }

    public static class WeightedDigraph {

        int numVertex;
        List<Edge>[] srcEdges;

        @SuppressWarnings("unchecked")
        public WeightedDigraph(int numV) {
            this.numVertex = numV;
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

            @Override
            public int hashCode() {
                int hash = 3;
                hash = 59 * hash + this.srcVertex;
                hash = 59 * hash + this.targetVertex;
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final Edge other = (Edge) obj;
                if (this.srcVertex != other.srcVertex) {
                    return false;
                }
                if (this.targetVertex != other.targetVertex) {
                    return false;
                }
                return true;
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

        Dinic flowNet = new Dinic(graph, 0, numVertex - 1);
        System.out.println(flowNet.computeMaxFlow());

    }

}
