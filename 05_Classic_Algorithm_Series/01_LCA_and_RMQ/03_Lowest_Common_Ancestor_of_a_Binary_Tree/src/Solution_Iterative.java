
/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 */
/**
 * Result: AC
 * TC: O(N)
 * SC: O(N)
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution_Iterative {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pAncestors = new ArrayList<TreeNode>();
        List<TreeNode> qAncestors = new ArrayList<TreeNode>();
        Stack<TreeNode> ancestors = new Stack<TreeNode>();
        Stack<TreeNode> dfsStack = new Stack<TreeNode>();
        dfsStack.push(root);

        while (p != null || q != null) {  // when p or q is found, they are set to null
            if (dfsStack.isEmpty()) {
                break;
            }
            TreeNode current = dfsStack.peek();
            if (ancestors.isEmpty() || ancestors.peek() != current) {
                // current not expanded
                if (current.left != null) {
                    dfsStack.push(current.left);
                }
                if (current.right != null) {
                    dfsStack.push(current.right);
                }
                ancestors.push(current);
            } else {
                if (p == current) {
                    pAncestors.addAll(ancestors);
                    p = null;
                } else if (q == current) {
                    // q and p cannot match same node
                    qAncestors.addAll(ancestors);
                    q = null;
                }
                dfsStack.pop();
                ancestors.pop();
            }
        }

        return findLastCommon(pAncestors, qAncestors);

    }

    public TreeNode findLastCommon(List<TreeNode> pAncestors, List<TreeNode> qAncestors) {
        if (pAncestors.isEmpty() || qAncestors.isEmpty()) {
            return null;
        }
        for (int i = 0; i < pAncestors.size(); i++) {
            if (i + 1 < pAncestors.size() && i + 1 < qAncestors.size()
                    && qAncestors.get(i + 1) == pAncestors.get(i + 1)) {
                continue;
            } else {
                return pAncestors.get(i);
            }
        }
        return null;
    }
}
