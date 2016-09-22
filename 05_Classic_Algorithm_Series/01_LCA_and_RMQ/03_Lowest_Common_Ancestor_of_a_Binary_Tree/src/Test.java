
public class Test {

    public static void main(String[] args) {
        Solution_Recurs s = new Solution_Recurs();
        TreeNode[] nodes = buildTree1();
        TreeNode lca = s.lowestCommonAncestor(nodes[0], nodes[1], nodes[2]);
        System.out.println(lca.val);
    }
    
    static TreeNode[] buildTree1() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        
        return new TreeNode[]{root, root.left, root};
    }

    static TreeNode[] buildTree() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        return new TreeNode[]{root, root.left, root.right};
    }

    static TreeNode[] buildLinearTree() {
        TreeNode root = new TreeNode(-1);
        TreeNode q = null;
        TreeNode p = null;
        TreeNode n = root;
        for (int i = 0; i < 10000; i++) {
            n.left = new TreeNode(i);
            n = n.left;
            if (i == 9998) {
                q = n;
            } else if (i == 9999) {
                p = n;
            }
        }
        return new TreeNode[]{root, p, q};

    }
}
