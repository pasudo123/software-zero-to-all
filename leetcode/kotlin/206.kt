/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 * 
 * 데이터를 더 적게 쓰는 방식으로 처리가 필요하다.
 * reverseListV2 의 경우에는 O(2n) 복잡도를 가지고 공간복잡도도 N 만큼 처리.
 */
class Solution {
    fun reverseList(head: ListNode?): ListNode? {
        if (head == null || head.next == null) return head

        var prev: ListNode? = null
        var current = head

        while (current != null) {
            val next = current.next
            current.next = prev
            prev = current    
            current = next
        }

        return prev
    }

    fun reverseListV2(head: ListNode?): ListNode? {
        if (head == null || head.next == null) return head

        val values = mutableListOf<Int>()
        
        var current = head
        while (current != null) {
            values.add(current.`val`)
            current = current.next
        }

        val newHead = ListNode(values.last())
        current  = newHead
        for (index in values.size - 2 downTo 0) {
            current?.next = ListNode(values.get(index))
            current = current?.next
        }

        return  newHead
    }
}
