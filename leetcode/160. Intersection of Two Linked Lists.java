/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) {
            return null;
        }
        
        ListNode aNode = headA;
        ListNode bNode = headB;
        int aLen = 0;
        int bLen = 0;
        
        // calculate node count on headA
        while(aNode != null) {
            aNode = aNode.next;
            aLen++;
        }
        
        // calculate node count on headB
        while(bNode != null) {
            bNode = bNode.next;
            bLen++;
        }
        
        // get Math.abs on length
        int moveNodeCount = Math.abs(aLen - bLen);
        aNode = headA;
        bNode = headB;
        
        // 나머지 부분은 탐색을 하지 않는것이 중요하다.
        int count = 0;
        if(aLen > bLen) {
            while(count < moveNodeCount) {
                aNode = aNode.next;
                count++;
            }

            count = 0;
        }
        if(aLen < bLen) {
            while(count < moveNodeCount) {
                bNode = bNode.next;
                count++;
            }
        }
        
        while(aNode != bNode) {
      
            // aLen == bLen
            aNode = aNode.next;
            bNode = bNode.next;    

            if(aNode == null || bNode == null) {
                return null;
            }
        }
        
        return aNode;
    }
}