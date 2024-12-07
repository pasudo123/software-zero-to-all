/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 * 
 * map 을 이용해서 해결. 순차적으로 확인해서 제거한다.
 */
class Solution {

    val nodeGroups = mutableMapOf<Int, ListNode?>()

    fun deleteMiddle(head: ListNode?): ListNode? {
        var temp = head
        
        var count = 0
        nodeGroups[count] = temp

        while(temp != null) {
            count++
            temp = temp.next
            nodeGroups[count] = temp
        }

        val middle = count / 2
        if (middle == 0) return null

        nodeGroups.remove(middle)
        val prev = nodeGroups[middle - 1]!!
        prev.next = null
        
        val next = nodeGroups[middle + 1] ?: return prev
        prev.next = next

        return head
    }
}
