
/**
 * http://hihocoder.com/problemset/problem/1325
 */
/**
 * Result: AC
 * TC: 
 *   insert: avg O(log(N)), worst O(N)
 *   search: avg O(log(N)), worst O(N)
 *   delete: avg O(log(N)), worst O(N)
 * SC: O(N)
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Comparator;
import java.util.Random;

public class Main {

    public static class Treap<T> {

        private static final Random rand = new Random();
        private Node<T> root;
        Comparator<T> comparator;

        public Treap(Comparator<T> comparator) {
            this.comparator = comparator;
        }

        class Node<T> {

            private T data;
            Node<T> left;
            Node<T> right;
            int priority = rand.nextInt();

            public Node(T data) {
                this.data = data;
            }
        }

        public void insert(T obj) {
            root = insertAt(root, new Node<T>(obj));
        }

        private Node<T> insertAt(Node<T> node, Node<T> newNode) {
            if (node == null) {
                return newNode;
            }
            int comp = comparator.compare(newNode.data, node.data);
            if (comp == 0) {
                return node;
            } else if (comp < 0) {
                node.left = insertAt(node.left, newNode);
                if (newNode.priority > node.priority) {
                    return rotateRight(node);
                }
            } else {
                node.right = insertAt(node.right, newNode);
                if (newNode.priority > node.priority) {
                    return rotateLeft(node);
                }
            }
            return node;
        }

        private Node<T> rotateRight(Node<T> node) {
            Node<T> leftNode = node.left;
            node.left = leftNode.right;
            leftNode.right = node;
            return leftNode;
        }

        private Node<T> rotateLeft(Node<T> node) {
            Node<T> rightNode = node.right;
            node.right = rightNode.left;
            rightNode.left = node;
            return rightNode;
        }

        public boolean contains(T obj) {
            Node<T> node = root;
            while (node != null) {
                int comp = comparator.compare(obj, node.data);
                if (comp == 0) {
                    return true;
                } else if (comp < 0) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            return false;
        }

        public void remove(T data) {
            root = remove(root, data);
        }

        private Node<T> remove(Node<T> node, T data) {
            if (node != null) {
                int compare = comparator.compare(data, node.data);
                if (compare < 0) {
                    node.left = remove(node.left, data);
                } else if (compare > 0) {
                    node.right = remove(node.right, data);
                } else if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                } else {
                    node.data = first(node.right);
                    node.right = remove(node.right, node.data);
                }
            }
            return node;
        }

        private T first(Node<T> searchNode) {
            Node<T> node = searchNode;
            while (node.left != null) {
                node = node.left;
            }
            return node.data;
        }

        public T floor(T obj) {
            Node<T> node = root;
            T candidate = null;
            while (node != null) {
                int comp = comparator.compare(obj, node.data);
                if (comp == 0) {
                    return node.data;
                } else if (comp < 0) {
                    node = node.left;
                } else {
                    candidate = node.data;
                    node = node.right;
                }
            }
            return candidate;
        }

    }

    public static void main(String[] args) throws IOException {
        Treap<Integer> treap = new Treap<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        int n = nextInt();
        while (n-- > 0) {
            String op = nextString();
            int v = nextInt();
            if ("I".equals(op)) {
                treap.insert(v);
            } else if ("Q".equals(op)) {
                out.println(treap.floor(v));
            }
        }
        out.flush();
    }

    private static final StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static int nextInt() throws IOException {
        in.nextToken();
        return (int) in.nval;
    }

    public static String nextString() throws IOException {
        in.nextToken();
        return in.sval;
    }
}
