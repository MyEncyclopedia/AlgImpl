
public class Test {

    public static void main(String[] args) {
        int[] a = new int[]{7334, 1556, 8286, 1640, 2699, 4807, 8068, 981, 4120, 2179};
        Main.RMQ_SparseTable rmqST = new Main.RMQ_SparseTable(a);
//        System.out.println(rmqST.query(2, 8));
        System.out.println(rmqST.query(7, 10));

    }

}
