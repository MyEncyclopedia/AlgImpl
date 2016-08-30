
public class Test {

    public static void main(String[] args) {
        int n = 4;
        int capacity = 5;
        int[] value = new int[]{4, 5, 2, 8};
        int[] cost = new int[]{2, 2, 1, 3};
        Main_DP_1_Array.Knapsack01 main = new Main_DP_1_Array.Knapsack01(n, capacity, cost, value);
        System.out.println(main.solve());
    }
}
