/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

import kotlin.math.min

class Solution {
    fun nodesBetweenCriticalPoints(head: ListNode?): IntArray {
        
        if (head == null) {
            return intArrayOf(-1, -1)
        }
        
        var criticalPoints: MutableList<Int> = mutableListOf()
        var prev = head
        var current = head.next
        var index = 2
        while (current != null && prev != null) {
        
            if (current.next != null) {
                if (prev.`val` < current.`val` && current.`val` > current.next!!.`val`) {
                    criticalPoints.add(index)
                }
                if (prev.`val` > current.`val` && current.`val` < current.next!!.`val`) {
                    criticalPoints.add(index)
                }
            }
            
            prev = prev.next
            current = current.next
            index++
        }
        
        if (criticalPoints.isEmpty() || criticalPoints.size == 1) {
            return intArrayOf(-1, -1)
        }
        
        criticalPoints = criticalPoints.sorted().toMutableList()
        var min = Integer.MAX_VALUE
        criticalPoints.forEachIndexed { index, element -> 
            if (index == 0) {
                return@forEachIndexed
            }
            
            min = min(min, element - criticalPoints[index - 1])
        }
        val max = criticalPoints.last() - criticalPoints.first()
        
        return intArrayOf(min, max)
    }
}
