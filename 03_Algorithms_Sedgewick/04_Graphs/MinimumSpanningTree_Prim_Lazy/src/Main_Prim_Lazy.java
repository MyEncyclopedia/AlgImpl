
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_2_A
 */
/**
 * Result: AC
 * TC: O(E*log(E))
 * SC: O(E)
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_Prim_Lazy {

    public static class MinimalSpanningTree_Prim_Lazy {

        Integer[] edgeTo; // index is vertex
        boolean[] vertDone;
        int totalWeight;

        public MinimalSpanningTree_Prim_Lazy(WeightedGraph graph) {
            edgeTo = new Integer[graph.numVertex];
            vertDone = new boolean[graph.numVertex];

            PriorityQueue<WeightedGraph.Edge> minPQ = new PriorityQueue<WeightedGraph.Edge>(
                    new Comparator<WeightedGraph.Edge>() {
                @Override
                public int compare(WeightedGraph.Edge e1, WeightedGraph.Edge e2) {
                    return e1.weight - e2.weight;
                }

            });
            HashSet<WeightedGraph.Edge> edgeSet = new HashSet<WeightedGraph.Edge>();
            edgeTo[0] = 0;
            vertDone[0] = true;
            int vertLeft = graph.numVertex - 1;
            Iterator<WeightedGraph.Edge> itEdge = graph.getEdges(0);
            while (itEdge.hasNext()) {
                WeightedGraph.Edge e = itEdge.next();
                minPQ.add(e);
                edgeSet.add(e);
            }
            while (vertLeft > 0 && !minPQ.isEmpty()) {
                WeightedGraph.Edge e = minPQ.remove();
                if (vertDone[e.v1] == !vertDone[e.v2]) {
                    totalWeight += e.weight;
                    int vertInTree = vertDone[e.v1] ? e.v1 : e.v2;
                    int vertNotIn = e.other(vertInTree);
                    vertLeft--;
                    vertDone[vertNotIn] = true;
                    edgeTo[vertNotIn] = vertInTree;
                    itEdge = graph.getEdges(vertNotIn);
                    while (itEdge.hasNext()) {
                        e = itEdge.next();
                        if (!edgeSet.contains(e)) {
                            minPQ.add(e);
                            edgeSet.add(e);
                        }
                    }
                }
            }
        }

        public Integer totalWeight() {
            return totalWeight;
        }

        public List<Integer> paths(int vertex) {
            LinkedList<Integer> paths = new LinkedList<Integer>();
            while (edgeTo[vertex] != null) {
                paths.addFirst(edgeTo[vertex]);
                if (edgeTo[vertex] != vertex) {
                    vertex = edgeTo[vertex];
                } else {
                    break;
                }
            }
            return paths;
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
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(v1, v2, weight);
        }

        MinimalSpanningTree_Prim_Lazy mst = new MinimalSpanningTree_Prim_Lazy(graph);
        System.out.println(mst.totalWeight());
    }
}
