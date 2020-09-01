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

/**
해당 문제는 들어오는 배열값이 인오더 형태로 들어오고 있고, 이를 바이너리 서치로 변경하는 것이다.
재귀 탐색을 해야하는데, 바이너리 서치로 트리가 구축되어야 하기 때문에 root 부터 새로운 값에 대해서 
재탐색 및 위치조정을 해야한다. 따라서 for 구문으로 하나하나의 값에 대해서 바이너리 서치 트리를 구축할 수 있도록 하였다.

이후에 createTree() 메소드를 통해서 해당 값에 대해 null 존재여부에 따라 서브트리를 구축한다.
**/
class Solution {
    public TreeNode bstFromPreorder(int[] preorder) {
        final TreeNode root = new TreeNode(preorder[0]);
        for(int i = 1; i < preorder.length; i++) {
            createTree(root, preorder[i]);   
        }
        return root;
    }
    
    private void createTree(final TreeNode root, final int value) {
        if(root.val > value) {
            // 좌
            if(root.left == null) {
                root.left = new TreeNode(value);
                return;
            }
            createTree(root.left, value);
            return;
        }
        
        if(root.right == null) {
                root.right = new TreeNode(value);
                return;
            }
        
        createTree(root.right, value);
    }
}
