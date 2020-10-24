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
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        
        while(head != null) {
            if(head.val != val) {
                break;
            }
            
            head = head.next;
        }
        
        if(head == null) {
            return null;
        }
        
        dummy.next = head;
        ListNode pre = head;
        ListNode post = head.next;
        int count = 0;
        
        while(post != null) {
            
            while(post != null && post.val == val) {
                post = post.next;
                count++;
            }
            
            if(count == 0) {
                pre = pre.next;
                post = post.next;
                continue;
            }
            
            if(post == null) {
                pre.next = null;
                break;
            }
            
            pre.next = post;
            pre = pre.next;
            post = pre.next;
            count = 0;
        }
    
        return dummy.next;
    }
}