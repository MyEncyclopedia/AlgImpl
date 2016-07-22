
public class Test {

    public static void main(String[] args) {
        Main.WeightedDigraph graph = new Main.WeightedDigraph(4, 5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(1, 3, 5);
        Main.DijkstraShortestPath dijkstra = new Main.DijkstraShortestPath(graph, 0);
        for (int i = 0; i < graph.numVertex; i++) {
            Integer v = dijkstra.shortestPath(i);
            System.out.println(v);
        }
    }
}
