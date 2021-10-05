class Solution {
    // https://leetcode.com/contest/weekly-contest-261/problems/minimum-moves-to-convert-string/
    fun minimumMoves(s: String): Int {
        var result = 0
        val stack = Stack<Char>()

        for (element in s) {
            if (stack.size == 3) {
                stack.clear()
                result++
            }

            if (element == 'X') {
                stack.push(element)
                continue
            }

            if (element == 'O' && stack.size != 0) {
                stack.push(element)
            }
        }

        if (stack.size >= 1) {
            stack.clear()
            result++
        }

        return result
    }
}
