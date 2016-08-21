
/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 */
/**
 * Result: Runtime Error, StackOverflowError
 * TC: O(N)
 * SC: O(N)
 */
/**
 * Note, this approach has the same stack depth as Solution_DivideAndConquer version.
 * But LeetCode passes latter and fail Solution_Recurs.
 * In my test case {@link Test.buildLinearTree}, both throw StackOverflowError.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution_Recurs {

    List<TreeNode> pAncestors = new ArrayList<TreeNode>();
    List<TreeNode> qAncestors = new ArrayList<TreeNode>();
    Stack<TreeNode> ancestors = new Stack<TreeNode>();
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return findLastCommon(pAncestors, qAncestors);
    }

    private boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
        ancestors.push(node);
        if (p == node) {
            pAncestors.addAll(ancestors);
            p = null;
        }
        if (q == node) {
            qAncestors.addAll(ancestors);
            q = null;
        }
        if (p == null && q == null) {
            return true;
        }
        if (node.left != null && dfs(node.left, p, q)) {
            return true;
        }
        if (node.right != null && dfs(node.right, p, q)) {
            return true;
        }
        ancestors.pop();
        return p == null && q == null;
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
