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
    public TreeNode searchBST(TreeNode root, int val) {
        return process(root, val);
    }
    
    private TreeNode process(TreeNode node, int val) {
        if(node == null) {
            return null;
        }
        
        if(node.val == val) {
            return node;
        }
        
        if(node.val < val) {
            return process(node.right, val);
        }
        
        return process(node.left, val);
    }
}