
import java.util.Comparator;

public class Test {

    public static void main(String[] args) {
        Main.Treap<Integer> treap = new Main.Treap<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        
        treap.insert(2);
        treap.insert(4);
        System.out.println(treap.contains(4));
        System.out.println(treap.floor(5));
        treap.remove(2);
        System.out.println(treap.contains(2));
    }
}
