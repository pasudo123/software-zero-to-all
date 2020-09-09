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
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        // (1) 각각 바이너리 서치를 각각의 오름차순 배열로 만든다.
        // (2) 생성된 두 개의 오름차순 배열을 하나의 오름차순 배열로 만든다.
        
        final List<Integer> elements1 = getElementsByTreeNode(root1);
        final List<Integer> elements2 = getElementsByTreeNode(root2);
        
        // System.out.println(Arrays.toString(elements1.toArray()));
        // System.out.println(Arrays.toString(elements2.toArray()));
        
        final List<Integer> newElements = new ArrayList<>();
        final int size1 = elements1.size();
        final int size2 = elements2.size();
        
        int i = 0;
        int j = 0;
        
        while(size1 != 0 && size2 != 0) {
            final int value1 = elements1.get(i);    
            final int value2 = elements2.get(j);
            if(value1 < value2) {
                newElements.add(value1);
                i++;
            } else {
                newElements.add(value2);
                j++;
            }
            
            if(i >= size1 || j >= size2) {
                break;
            }
        }
        
        while(i < size1) {
            newElements.add(elements1.get(i++));
        }
        
        while(j < size2) {
            newElements.add(elements2.get(j++));
        }
        
        return newElements;
    }
    
    public List<Integer> getElementsByTreeNode(final TreeNode node) {
        
        if(node == null) {
            return new ArrayList<>();
        }
        
        List<Integer> list = new ArrayList<>();
        
        list.addAll(getElementsByTreeNode(node.left));
        list.add(node.val);
        list.addAll(getElementsByTreeNode(node.right));
        
        return list;
    }
}
