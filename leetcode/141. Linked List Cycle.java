/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast= head.next;
        
        while(slow != fast) {
            
            // two pointer fast 가 먼저 끝에 도달한 경우는 recycle 이 아니다.
            if(fast == null || fast.next == null) {
                return false;
            }
            
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return true;
    }
}