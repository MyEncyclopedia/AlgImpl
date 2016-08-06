
public class Test {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 5, 7, 9, 11};
        NumArray numArray = new NumArray(nums);
        System.out.println(numArray.sumRange(0, 2));
//        numArray.update(0, 0);
        System.out.println(numArray.sumRange(0, 2));
//        numArray.update(1, 2);
        System.out.println(numArray.sumRange(0, 2));

    }
}
