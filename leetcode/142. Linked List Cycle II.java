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
    
    public ListNode intersection(ListNode head) {
        
        // 처음 할당은 head 값으로 할당할 수 있도록 한다        
        ListNode slow = head;
        ListNode fast = head;
        
        
        // 여기서 fast 는 slow 보다 2배 더 이동한다.
        while(fast != null) {    
            fast = fast.next;
            
            if(fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
            
            if(fast == slow) {
                return slow;
            }
            
            if(fast == null) {
                return null;
            }
        }
        
        return null;
    }
    
    public ListNode detectCycle(ListNode head) {
        
        if(head == null || head.next == null) {
            return null;
        }
        
        ListNode intersect = intersection(head);
        
        if(intersect == null) {
            return null;
        }
        
        ListNode start = head;
        
        while(start != intersect) {
            intersect = intersect.next;
            start = start.next;
        }    
        
        return start;
    }
}