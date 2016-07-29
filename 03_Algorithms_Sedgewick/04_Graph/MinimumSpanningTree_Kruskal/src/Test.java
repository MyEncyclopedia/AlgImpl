
public class Test {

    public static void main(String[] args) {
        Main_Kruskal.WeightedGraph graph = new Main_Kruskal.WeightedGraph(4);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 0, 1);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 5);

        Main_Kruskal.MinimalSpanningTree_Kruskal mst = new Main_Kruskal.MinimalSpanningTree_Kruskal(graph);
        System.out.println(mst.totalWeight());
    }
}
