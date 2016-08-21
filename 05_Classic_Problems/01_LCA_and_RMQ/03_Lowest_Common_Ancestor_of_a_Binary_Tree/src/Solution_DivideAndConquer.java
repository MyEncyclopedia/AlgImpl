
/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 */
/**
 * Result: AC
 * TC: O(N)
 * SC: O(N)
 */
public class Solution_DivideAndConquer {

    /**
     * @return LCA or A or B or null
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null || root == node1 || root == node2) {
            return root;
        }

        // Divide
        TreeNode left = lowestCommonAncestor(root.left, node1, node2);
        TreeNode right = lowestCommonAncestor(root.right, node1, node2);

        // Conquer
        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }
}
