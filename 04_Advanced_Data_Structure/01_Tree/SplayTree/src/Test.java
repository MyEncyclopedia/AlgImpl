
import java.util.Comparator;

public class Test {

    public static void main(String[] args) {
        Main.SplayTree<Integer> splayTree = new Main.SplayTree<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        splayTree.insert(1);
        splayTree.insert(2);
        splayTree.insert(3);
        splayTree.delete(3);
        System.out.println(splayTree.find(3));
        System.out.println(splayTree.find(2));
        System.out.println(splayTree.find(3));

    }
}
