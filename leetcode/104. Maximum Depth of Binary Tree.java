/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int maxDepth(TreeNode root) {
        return recursive(1, root);
    }
    
    private int recursive(final int currentHeight, final TreeNode node) {
        if(node == null) {
            return currentHeight - 1;
        }
        
        return Math.max(recursive(currentHeight + 1, node.left), 
                        recursive(currentHeight + 1, node.right));
    }
}