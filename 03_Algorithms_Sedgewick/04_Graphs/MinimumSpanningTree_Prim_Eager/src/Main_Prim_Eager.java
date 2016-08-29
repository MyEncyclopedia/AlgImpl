
/**
 * http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=GRL_2_A
 */
/**
 * Result: AC
 * TC: O(E*log(V))
 * SC: O(V)
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_Prim_Eager {

    public static class MinimalSpanningTree_Prim_Eager {

        Integer[] edgeTo; // index is vertex
        boolean[] vertDone;
        int totalWeight;

        public MinimalSpanningTree_Prim_Eager(WeightedGraph graph) {
            edgeTo = new Integer[graph.numVertex];
            vertDone = new boolean[graph.numVertex];

            KeyValueMinHeap<Integer, VertexInfo> minPQ
                    = new KeyValueMinHeap<Integer, VertexInfo>(new Comparator<VertexInfo>() {
                        @Override
                        public int compare(VertexInfo o1, VertexInfo o2) {
                            return o1.weight - o2.weight;
                        }
                    });
            edgeTo[0] = 0;
            vertDone[0] = true;
            int vertLeft = graph.numVertex - 1;
            Iterator<WeightedGraph.Edge> itEdge = graph.getEdges(0);
            while (itEdge.hasNext()) {
                WeightedGraph.Edge e = itEdge.next();
                VertexInfo vertInfo = minPQ.getValue(e.other(0));
                if (vertInfo == null || e.weight < vertInfo.weight) {
                    minPQ.addOrUpdate(e.other(0), new VertexInfo(0, e.weight));
                }
            }
            while (vertLeft > 0 && !minPQ.isEmpty()) {
                KeyValueMinHeap.Pair<Integer, VertexInfo> pair = minPQ.remove();
                int vert = pair.key;
                VertexInfo vertInfo = pair.value;
                edgeTo[vert] = vertInfo.fromVert;
                vertDone[vert] = true;
                vertLeft--;
                totalWeight += vertInfo.weight;
                itEdge = graph.getEdges(vert);
                while (itEdge.hasNext()) {
                    WeightedGraph.Edge e = itEdge.next();
                    if (!vertDone[e.v1] || !vertDone[e.v2]) {
                        vertInfo = minPQ.getValue(e.other(vert));
                        if (vertInfo == null || e.weight < vertInfo.weight) {
                            minPQ.addOrUpdate(e.other(vert), new VertexInfo(vert, e.weight));
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

        class VertexInfo {

            int fromVert;
            int weight;

            public VertexInfo(int fromVert, int weight) {
                this.fromVert = fromVert;
                this.weight = weight;
            }

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

    public static class KeyValueMinHeap<K, V> {

        Comparator<V> comparator;
        HashMap<K, V> map = new HashMap<K, V>();
        PriorityQueue<Pair<K, V>> minPQ = new PriorityQueue<Pair<K, V>>(new Comparator<Pair<K, V>>() {
            @Override
            public int compare(Pair<K, V> o1, Pair<K, V> o2) {
                return comparator.compare(o1.value, o2.value);
            }
        });

        public KeyValueMinHeap(Comparator<V> comparator) {
            this.comparator = comparator;
        }

        public void addOrUpdate(K key, V value) {
            Pair<K, V> pair = new Pair<K, V>(key, value);
            if (map.containsKey(key)) {
                minPQ.remove(pair);
            }
            map.put(key, value);
            minPQ.add(pair);
        }

        public V getValue(K key) {
            return map.get(key);
        }

        public Pair<K, V> remove() {
            Pair<K, V> pair = minPQ.remove();
            V v = map.remove(pair.key);
            return pair;
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }

        public static class Pair<K, V> {

            K key;
            V value;

            public Pair(K k, V v) {
                key = k;
                value = v;
            }

            public boolean equals(Object obj) {
                if (obj instanceof Pair) {
                    return key.equals(((Pair) obj).key);
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

        MinimalSpanningTree_Prim_Eager mst = new MinimalSpanningTree_Prim_Eager(graph);
        System.out.println(mst.totalWeight());
    }
}
