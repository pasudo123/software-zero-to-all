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
    public ListNode rotateRight(ListNode head, int k) {
        ListNode temp = head;
        
        int count = 0;
        while(temp != null) {
            count++;
            temp = temp.next;
        }
        
        if(count == 0) {
            return null;
        }
        
        int rotateCount = k % count;
        int index = count - rotateCount;
        
        if(rotateCount == 0) {
            return head;
        }
        
        temp = head;
        count = 0;
        
        while(count < index) {
            temp = temp.next;
            count++;
        }
        
        ListNode ret = new ListNode();
        ListNode current = new ListNode(temp.val, temp.next);
        ret.next = current;
    
        while(current.next != null) {
            current = current.next;
        }
        
        temp = head;
        count = 0;
        
        while(count < index) {
            current.next = new ListNode(temp.val);
            current = current.next;
            temp = temp.next;
            count++;   
        }
        
        return ret.next;
    }
}