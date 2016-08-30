
public class Test {

    public static void main(String[] args) {
        int n = 4;
        int capacity = 8;
        int[] cost = new int[]{2, 2, 1, 3};
        int[] value = new int[]{4, 5, 2, 8};
        Main_DP_NW_2_Arrays.KnapsackComplete main = new Main_DP_NW_2_Arrays.KnapsackComplete(n, capacity, cost, value);

        System.out.println(main.solve());
    }
}
