/**
노드를 Left -> Right 재귀형태로 탐색하면서 수행.
root1, root2 의 끝 노드의 값의 순서쌍이 동일한지만 판별하면 된다. equal (==) 이용.
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
    fun leafSimilar(root1: TreeNode?, root2: TreeNode?): Boolean {
        var root1Values = mutableListOf<Int>()
        var root2Values = mutableListOf<Int>()

        makeValues(root1, root1Values)
        makeValues(root2, root2Values)

        return root1Values == root2Values
    }

    private fun makeValues(root: TreeNode?, rootValues: MutableList<Int>) {
        if (root == null) {
            return
        }
        
        if (root.left == null && root.right == null) {
            rootValues.add(root.`val`)
            return
        }

        makeValues(root.left, rootValues)
        makeValues(root.right, rootValues)
    }
}
