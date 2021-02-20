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
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) {
            return false;
        }
        
        return isPossible(root, 0, sum);
    }
    
    public boolean isPossible(TreeNode node, int currentSum, final int sum) {
        if(node.left == null && node.right == null) {
            return (currentSum + node.val == sum);
        } 
        
        boolean left = false;
        boolean right = false;
        
        if(node.left != null) {
            left = isPossible(node.left, currentSum + node.val, sum);
        }
        
        if(node.right != null) {
            right = isPossible(node.right, currentSum + node.val, sum);
        }
        
        return (left || right);
    }
}