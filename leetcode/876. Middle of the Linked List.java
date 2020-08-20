/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        // return basicApproach(head);
        return slowAndFaster(head);
    }
    
    // fast 는 두 배 더 멀리 간다.
    public ListNode slowAndFaster(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next.next = null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        
        return slow;
    }
    
    public ListNode basicApproach(ListNode head) {
        ListNode moveNode = head.next;
        int count = 1;
        
        while (moveNode != null) {
            count++;
            moveNode = moveNode.next;
        }
        
        final int half = (int) Math.ceil(count / 2);
        count = 0;
        
        while(head != null && count != half) {
            count++;
            head = head.next;
        }
        
        return head;
    }
}
