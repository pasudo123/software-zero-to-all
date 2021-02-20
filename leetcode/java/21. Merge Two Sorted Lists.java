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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        
        ListNode newNode = new ListNode(0);
        ListNode retNode = newNode;
        
        while(l1 != null && l2 != null) {
            if(l1.val > l2.val) {
                newNode.next = new ListNode(l2.val);
                l2 = l2.next;
            } else {
                newNode.next = new ListNode(l1.val);
                l1 = l1.next;   
            }
            
            newNode = newNode.next;
        }
        
        while(l1 != null) {
            newNode.next = new ListNode(l1.val);
            newNode = newNode.next;
            l1 = l1.next;   
        }
        
        while(l2 != null) {
            newNode.next = new ListNode(l2.val);
            newNode = newNode.next;
            l2 = l2.next;   
        }
        
        return retNode.next;
    }
}