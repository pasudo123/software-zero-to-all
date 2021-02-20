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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // n + 1 을 수행한 이유는 dummy 가 head 이전의 node 이기 때문이다.
        for(int i = 1; i <= n + 1; i++) {
            fast = fast.next;
        }
        
        // fast 는 reverse 역행순서로 위치하고 있다.
        // slow 는 일반적인 순서로 위치하고 있다.
        while(fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        slow.next = slow.next.next;
        
        return dummy.next;
    }
}