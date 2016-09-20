
public class Test {

    public static void main(String[] args) {
//        int[] prices = new int[]{1, 2, 3, 0, 2};
//        int[] prices = new int[]{2, 1, 4};
//        int[] prices = new int[]{6, 1, 3, 2, 4, 7};
//        int[] prices = new int[]{1, 2, 3, 0, 2};
        int[] prices = new int[]{2, 1, 2, 1, 0, 1, 2};
        Solution_N s = new Solution_N();
        System.out.println(s.maxProfit(prices));
    }

}
