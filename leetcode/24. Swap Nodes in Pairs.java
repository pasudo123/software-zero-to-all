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
    public ListNode swapPairs(ListNode head) {
        
        if(head == null) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        process(head);
        
        return dummy.next;
    }
    
    private void process(ListNode node) {

        if(node == null || node.next == null) {
            return;
        }
        
        int temp = node.val;
        node.val = node.next.val;
        node.next.val = temp;
        
        node = node.next;
        
        process(node.next);
    }
}