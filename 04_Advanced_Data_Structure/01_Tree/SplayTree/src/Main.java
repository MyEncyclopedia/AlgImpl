
/**
 * http://hihocoder.com/problemset/problem/1329
 */
/**
 * Result: AC
 * TC:
 *   Average
 *     Search  O(log(N))
 *     Insert  O(log(N))
 *     Delete  O(log(N))
 * SC: O(N)
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Comparator;

public class Main {

    public static class SplayTree<T> {

        private Node<T> root;
        Comparator<T> comparator;

        public SplayTree(Comparator<T> comparator) {
            this.comparator = comparator;
        }

        class Node<T> {

            private T data;
            Node<T> left;
            Node<T> right;
            Node<T> father;

            public Node(T data, Node<T> father) {
                this.data = data;
                this.father = father;
            }

            public T getValue() {
                return data;
            }
        }

        public void insert(T obj) {
            Node<T> node = insertBST(obj);
            splay(node, null);
        }

        public Node<T> find(T obj) {
            Node<T> node = findBST(obj);
            if (node != null) {
                splay(node, null);
            }
            return node;
        }

        public boolean delete(T key) {
            Node<T> node = find(key);
            if (node == null) {
                return false;
            }
            Node<T> prev = findPrev(key);
            if (prev == null) {
                root = node.right;
                root.father = null;
                node.father = null;
                return true;
            }
            Node<T> next = findNext(key);
            if (next == null) {
                root = node.left;
                root.father = null;
                node.father = null;
                return true;
            }
            splay(prev, null);
            splay(next, prev);
            next.left = null;
            node.father = null;
            return true;
        }

        public void deleteInterval(T a, T b) {
            Node<T> nodeA = find(a);
            if (nodeA == null) {
                insert(a);
                nodeA = root;
            }
            Node<T> prev = findPrev(a);
            Node<T> nodeB = find(b);
            if (nodeB == null) {
                insert(b);
                nodeB = root;
            }
            Node<T> next = findNext(b);
            if (prev == null && next == null) {
                root = null;
            } else if (prev == null) {
                splay(next, null);
                next.left = null;
            } else if (next == null) {
                splay(prev, null);
                prev.right = null;
            } else {
                splay(prev, null);
                splay(next, prev);
                next.left = null;
            }
            nodeA.father = null;
            nodeB.father = null;

        }

        public Node<T> findPrev(T key) {
            Node<T> node = find(key);
            if (node == null) {
                return null;
            }
            splay(node, null);
            if (root.left == null) {
                return null;
            }
            node = root.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        public Node<T> findNext(T key) {
            Node<T> node = find(key);
            if (node == null) {
                return null;
            }
            splay(node, null);
            if (root.right == null) {
                return null;
            }
            node = root.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        private void splay(Node<T> node, Node<T> ancestor) {
            while (node.father != ancestor) {
                Node<T> p = node.father;
                if (p.father == ancestor) {
                    // because p's father is ancestor, only Zig node to make node's father become ancestor
                    if (p.left == node) {
                        rightRotate(node);
                    } else {
                        leftRotate(node);
                    }
                } else {
                    Node<T> g = p.father;
                    if (g.left == p) {
                        if (p.left == node) {
                            // node and p are left children, Zig-Zig
                            rightRotate(p);
                            rightRotate(node);
                        } else {
                            // p left，node right，Zig-Zag
                            leftRotate(node);
                            rightRotate(node);
                        }
                    } else if (p.right == node) {
                        // node and p are right children，Zig-Zig
                        leftRotate(p);
                        leftRotate(node);
                    } else {
                        // p right，node left，Zig-Zag
                        rightRotate(node);
                        leftRotate(node);
                    }
                }
            }
        }

        private void rightRotate(Node<T> node) {
            Node<T> p = node.father;
            node.father = p.father;
            if (p.father != null) {
                if (p.father.left == p) {
                    p.father.left = node;
                } else {
                    p.father.right = node;
                }
            } else {
                root = node;
            }
            p.left = node.right;
            if (node.right != null) {
                node.right.father = p;
            }
            node.right = p;
            p.father = node;
        }

        private void leftRotate(Node<T> node) {
            Node<T> p = node.father;
            node.father = p.father;
            if (p.father != null) {
                if (p.father.left == p) {
                    p.father.left = node;
                } else {
                    p.father.right = node;
                }
            } else {
                root = node;
            }
            p.right = node.left;
            if (node.left != null) {
                node.left.father = p;
            }
            node.left = p;
            p.father = node;
        }

        private Node<T> insertBST(T key) {
            if (root == null) {
                root = new Node<T>(key, null);
                return root;
            }
            Node<T> p = root;
            while (true) {
                int compare = comparator.compare(key, p.data);
                if (compare == 0) {
                    return p;
                } else if (compare < 0) {
                    if (p.left == null) {
                        p.left = new Node<T>(key, p);
                        return p.left;
                    } else {
                        p = p.left;
                    }
                } else if (p.right == null) {
                    p.right = new Node<T>(key, p);
                    return p.right;
                } else {
                    p = p.right;
                }
            }
        }

        private Node<T> findBST(T key) {
            Node<T> n = root;
            while (n != null) {
                int compare = comparator.compare(key, n.data);
                if (compare == 0) {
                    return n;
                } else if (compare < 0) {
                    n = n.left;
                } else {
                    n = n.right;
                }
            }
            return null;
        }

    }

    public static void main(String[] args) throws IOException {
        SplayTree<Integer> splayTree = new SplayTree<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        int n = nextInt();
        while (n-- > 0) {
            String op = nextString();
            if ("I".equals(op)) {
                int k = nextInt();
                splayTree.insert(k);
            } else if ("Q".equals(op)) {
                int k = nextInt();
                if (splayTree.find(k) != null) {
                    out.println(k);
                } else {
                    splayTree.insert(k);
                    out.println(splayTree.findPrev(k).data);
                    splayTree.delete(k);
                }
            } else if ("D".equals(op)) {
                int start = nextInt();
                int end = nextInt();
                if (start <= end) {
                    splayTree.deleteInterval(start, end);
                } else {
                    splayTree.deleteInterval(end, start);
                }
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
