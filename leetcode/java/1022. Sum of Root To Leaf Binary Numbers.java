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
    public int sumRootToLeaf(TreeNode root) {
        return calculateTreeLeaf(root, "");
    }
    
    private int calculateTreeLeaf(final TreeNode node, final String currentString) {
        
        if(node != null && node.left == null && node.right == null) {
            int ret = getValue(currentString + node.val);
            return ret;
        }
        
         if(node == null) {
            return 0;
        }
        
        return calculateTreeLeaf(node.left, currentString + node.val) + calculateTreeLeaf(node.right, currentString + node.val);
    }
    
    private int getValue(final String currentString) {
        final int size = currentString.length();
        int value = 0;
        int baseValue = 1;
        for(int i = size - 1; i >= 0; i--) {
            value += (currentString.charAt(i) - '0') * baseValue;
            baseValue *= 2;
        }
        
        return value;
    }
}
