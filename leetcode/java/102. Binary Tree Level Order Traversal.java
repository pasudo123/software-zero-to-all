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

import java.util.*;

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) {
            return Collections.emptyList();
        }
        
        final List<List<Integer>> ret = new ArrayList<>();
        recursive(root, 0, ret);
        return ret;
    }
    
    private void recursive(final TreeNode node, final int currentIndex, final List<List<Integer>> ret) {
        if(node == null) {
            return;
        }
        
        if(ret.size() <= currentIndex) {
            ret.add(new ArrayList<>());
        }
        
        List<Integer> list = ret.get(currentIndex);
        list.add(node.val);
        ret.set(currentIndex, list);
        
        recursive(node.left, currentIndex + 1, ret);
        recursive(node.right, currentIndex + 1, ret);
    }
}