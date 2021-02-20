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
    
    // 좀 더 최적화를 하였다.
    // 기존에 BigInteger 를 사용해서 작업하던 것을
    // 단순 자릿수의 값으로 int 형태로 취하여 값을 더하였고, 올림수는 upper 이란 변수를 사용하였다.
    // 여기서 while 하고 안에 if 와 삼항연산자를 이용해서 해결할 수 있었다.
    private ListNode optProcess(ListNode l1, ListNode l2) {
        
        boolean isUpper = false;
        
        ListNode ret = new ListNode(0);
        ListNode current = ret;
        
        int sum = 0;
        
        while(l1 != null || l2 != null) {
            
            sum = (isUpper) ? sum + 1 : sum;
            
            if(l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            
            if(l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            
            isUpper = (sum >= 10) ? true : false; 
            
            current.next = new ListNode(sum % 10);
            current = current.next;
            sum = 0;
        }

        if(isUpper) {
            current.next = new ListNode(1);
        }
        
        return ret.next;
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