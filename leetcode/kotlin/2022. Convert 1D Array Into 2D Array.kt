class Solution {
    fun construct2DArray(original: IntArray, m: Int, n: Int): Array<IntArray> {

        if (m * n != original.size) {
            return emptyArray()
        }

        val result = MutableList(m) { IntArray(n) { 0 } }
        var currentIndex = 0
        for (row in 0 until m) {
            for (col in 0 until n) {
                result[row][col] = original[currentIndex]
                currentIndex++
            }
        }

        return result.toTypedArray()
    }
}
