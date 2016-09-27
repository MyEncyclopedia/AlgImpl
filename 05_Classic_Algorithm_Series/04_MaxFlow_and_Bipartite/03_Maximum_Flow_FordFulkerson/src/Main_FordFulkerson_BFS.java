
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_6_A
 */
/**
 * Result: AC
 * TC: O(F*(V+E))
 * SC: O(V+E)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main_FordFulkerson_BFS {

    public static class FordFulkerson_BFS {

        final ResidualNetwork residualG;
        final int srcVert;
        final int termVert;

        public FordFulkerson_BFS(WeightedDigraph graph, int srcVert, int termVert) {
            this.residualG = new ResidualNetwork(graph);
            this.srcVert = srcVert;
            this.termVert = termVert;
        }

        public int computeMaxFlow() {
            int maxFlow = 0;
            int flow;
            while ((flow = findAugmentPath()) > 0) {
                maxFlow += flow;
            }
            return maxFlow;
        }

        private int findAugmentPath() {
            boolean[] used = new boolean[residualG.numVertex];
            int[] minFlow = new int[residualG.numVertex];
            HashMap<WeightedDigraph.Edge, WeightedDigraph.Edge> edgeLink
                    = new HashMap<WeightedDigraph.Edge, WeightedDigraph.Edge>();
            Queue<WeightedDigraph.Edge> edgeQueue = new LinkedList<WeightedDigraph.Edge>();
            used[srcVert] = true;
            minFlow[termVert] = Integer.MAX_VALUE;
            minFlow[srcVert] = Integer.MAX_VALUE;
            
            WeightedDigraph.Edge currentEdge = null;
            int vert = srcVert;
            do {
                for (WeightedDigraph.Edge e : residualG.srcEdges[vert]) {
                    if (e.weight > 0 && !used[e.targetVertex]) {
                        minFlow[e.targetVertex] = Math.min(minFlow[e.srcVertex], e.weight);
                        used[e.targetVertex] = true;
                        edgeQueue.add(e);
                        edgeLink.put(e, currentEdge);
                    }
                }
                if (edgeQueue.isEmpty()) {
                    break;
                }
                currentEdge = edgeQueue.poll();
                vert = currentEdge.targetVertex;
                if (currentEdge.targetVertex == termVert) {
                    updateResidualGraph(minFlow[termVert], currentEdge, edgeLink);
                    return minFlow[termVert];
                }
            } while (true);

            return 0;
        }

        private void updateResidualGraph(int flow, WeightedDigraph.Edge lastEdge, HashMap<WeightedDigraph.Edge, WeightedDigraph.Edge> edgeLink) {
            while (lastEdge != null) {
                lastEdge.weight -= flow;
                residualG.edgeMap.get(lastEdge).weight += flow;
                lastEdge = edgeLink.get(lastEdge);
            }
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

        FordFulkerson_BFS flowNet = new FordFulkerson_BFS(graph, 0, numVertex - 1);
        System.out.println(flowNet.computeMaxFlow());

    }

}
