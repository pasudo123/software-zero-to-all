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
import java.math.BigInteger; 

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return optProcess(l1, l2);
        // return oldProcess(l1, l2);
    }
    
    private ListNost optProcess(ListNode l1, ListNode l2) {
        return null;
    }
    
    private ListNode oldProcess(ListNode l1, ListNode l2) {
        BigInteger val1 = getNumberOfListNode(l1);
        BigInteger val2 = getNumberOfListNode(l2);
        
        BigInteger sum = val1.add(val2);
        BigInteger zero = BigInteger.ZERO;
        
        if(sum.equals(zero)) {
            return new ListNode(0);
        }
        
        ListNode newNode = new ListNode(0);
        ListNode ret = newNode;
        
        while(!sum.equals(zero)) {
            newNode.next = new ListNode(sum.mod(BigInteger.TEN).intValue());
            sum = sum.divide(BigInteger.TEN);
            newNode = newNode.next;
        }
        
        return ret.next;
    }
    
    private BigInteger getNumberOfListNode(ListNode travel) {
        BigInteger ret = BigInteger.valueOf(0L);
        BigInteger exp = BigInteger.valueOf(1L);
        
        while(travel != null) {
            ret = ret.add(exp.multiply(BigInteger.valueOf(travel.val)));
            // ret += (travel.val * exp);
            // exp *= 10;
            exp = exp.multiply(BigInteger.valueOf(10L));
            travel = travel.next;
        }
        
        return ret;
    }
}