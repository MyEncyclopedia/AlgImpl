
public class Test {

    public static void main(String[] args) {
        Solution_Generative s = new Solution_Generative();
        for (Integer intV : s.grayCode(3)) {
            System.out.println(Integer.toBinaryString(intV));
        }
    }
}
