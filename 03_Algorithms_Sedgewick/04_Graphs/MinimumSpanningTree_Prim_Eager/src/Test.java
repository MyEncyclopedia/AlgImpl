
public class Test {

    public static void main(String[] args) {
        Main_Prim_Eager.WeightedGraph graph = new Main_Prim_Eager.WeightedGraph(4);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 0, 1);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 5);

        Main_Prim_Eager.MinimalSpanningTree_Prim_Eager mst = new Main_Prim_Eager.MinimalSpanningTree_Prim_Eager(graph);
        System.out.println(mst.totalWeight());
    }
}
