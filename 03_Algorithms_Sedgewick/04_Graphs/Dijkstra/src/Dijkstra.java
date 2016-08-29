
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_1_A
 */
/**
 * Result: AC
 * TC: O(E*log(V))
 * SC: O(V)
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {

    public static class DijkstraShortestPath {

        Integer[] distTo; // index is vertex
        Integer[] edgeTo;

        public DijkstraShortestPath(WeightedDigraph graph, int sourceVertex) {
            distTo = new Integer[graph.numVertex];
            edgeTo = new Integer[graph.numVertex];

            distTo[sourceVertex] = 0;
            edgeTo[sourceVertex] = sourceVertex;
            MinPriorityQ<PathWithV> minPQ = new MinPriorityQ<PathWithV>(new Comparator<PathWithV>() {
                @Override
                public int compare(PathWithV o1, PathWithV o2) {
                    return o1.path - o2.path;
                }
            });
            PathWithV p = new PathWithV(sourceVertex, 0);
            minPQ.add(p);
            while (!minPQ.isEmpty()) {
                int vertex = minPQ.remove().vertex;
                for (WeightedDigraph.Edge edge : graph.srcEdges[vertex]) {
                    Integer targetVertexPath = distTo[edge.targetVertex];
                    if (targetVertexPath == null || distTo[vertex] + edge.weight < targetVertexPath) {
                        distTo[edge.targetVertex] = distTo[vertex] + edge.weight;
                        edgeTo[edge.targetVertex] = vertex;
                        p = new PathWithV(edge.targetVertex, distTo[edge.targetVertex]);
                        minPQ.addOrUpdateKey(p);
                    }
                }
            }
        }

        class PathWithV {

            int path;
            int vertex;

            public PathWithV(int vertex, int path) {
                this.path = path;
                this.vertex = vertex;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof PathWithV) {
                    final PathWithV other = (PathWithV) obj;
                    if (this.vertex != other.vertex) {
                        return false;
                    }
                }

                return false;
            }

        }

        /**
         *
         * @param vertex
         * @return null meaning INF
         */
        public Integer shortestPath(int vertex) {
            return distTo[vertex];
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

    /**
     * Mimic raising key value for a PriorityQueue.
     * Introducing a HashSet lookup to speed up addOrUpdateKey to lg(N).
     * Otherwise, it would have cost 2*lg(N).
     */
    public static class MinPriorityQ<E> extends PriorityQueue<E> {

        public MinPriorityQ(Comparator<? super E> comparator) {
            super(comparator);
        }
        HashSet<E> set = new HashSet<E>();

        public boolean add(E e) {
            set.add(e);
            return super.add(e);
        }

        public void addOrUpdateKey(E e) {
            if (set.contains(e)) {
                super.remove(e);
                add(e);
            } else {
                set.add(e);
                add(e);
            }
        }

        public boolean remove(Object o) {
            if (set.remove(o)) {
                return super.remove(o);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numVertex = scanner.nextInt();
        int numEdge = scanner.nextInt();
        WeightedDigraph graph = new WeightedDigraph(numVertex, numEdge);
        int srcVertex = scanner.nextInt();
        while (numEdge-- > 0) {
            int srcV = scanner.nextInt();
            int targetV = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(srcV, targetV, weight);
        }

        DijkstraShortestPath dijkstra = new DijkstraShortestPath(graph, srcVertex);
        for (int i = 0; i < graph.numVertex; i++) {
            Integer v = dijkstra.shortestPath(i);
            System.out.println(v == null ? "INF" : v);
        }
    }
}
