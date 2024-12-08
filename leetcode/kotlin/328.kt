/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 * 
 * 링크드리스트 홀수, 짝수를 구분해서 처리한다.
 */
class Solution {
    fun oddEvenList(head: ListNode?): ListNode? {
        if (head == null || head.next == null) return head

        var odd = head!!
        val fixEven = odd.next
        var even = odd.next

        while(even != null && even.next != null) {
            // 홀
            odd.next = even.next!!
            odd = odd.next

            // 짝
            even.next = odd.next
            even = even.next
        }

        odd?.next = fixEven

        return head
    }
}
