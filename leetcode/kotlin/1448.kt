/**
재귀함수로 왼쪽, 오른쪽 순회하면서 현재 들어온 값과 루트값을 비교하여 더 크면 + 1 을 수행하도록 한다.
그렇지 않다면 +1 을 하지않고 순회한다.
**/

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class Solution {
    fun goodNodes(root: TreeNode?): Int {
        if (root == null) return 0

        val rootValue = root.`val`
        return search(root, rootValue)
    }

    private fun search(root: TreeNode?, maxValue: Int): Int {
        if (root == null) return 0

        val rootValue = root.`val`
        val newMaxValue = max(rootValue, maxValue)

        if (newMaxValue <= rootValue) {
            return 1 + search(root.left, newMaxValue) + search(root.right, newMaxValue)
        }

        return search(root.left, newMaxValue) + search(root.right, newMaxValue)
    }
}
