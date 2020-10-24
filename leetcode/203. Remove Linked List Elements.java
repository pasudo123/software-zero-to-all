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
        
        while(head != null && head.val == val) {
            head = head.next;
        }
        
        if(head == null) {
            return null;
        }
        
        ListNode pre = head;
        ListNode post = head;
        
        while(post != null) {
            
            if (post.val == val) {  
                pre.next = post.next;
            } else {  
                pre = post;
            }
            
            post = post.next; 
        }
    
        return head;
    }
}