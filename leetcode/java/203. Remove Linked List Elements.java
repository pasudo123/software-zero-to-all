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

// solution 보고 푼 것.
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
                // post 가 이전 단계
                pre = post;
            }
            
            // post 를 다음단계로 보낸다.
            // pre 랑 post 랑 한 단계 차이를 만든다.
            post = post.next; 
        }
    
        return head;
    }
}

// solution 보지 않고 직접 푼 것.
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        
        while(head != null && head.val == val) {
            head = head.next;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode post = head;
        
        while(post != null) {
            if(post.val == val) {
                pre.next = post.next;
            } else {
                pre = pre.next;
            }
            
            post = post.next;
        }
    
        return head;
    }
}