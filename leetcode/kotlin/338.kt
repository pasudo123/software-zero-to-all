/**
 * 나머지 연산자로 처리
 */
class Solution {
    fun countBits(n: Int): IntArray {
        if (n == 0) return intArrayOf(0)
        if (n == 1) return intArrayOf(0, 1)
        if (n == 2) return intArrayOf(0, 1, 1)

        val ret = IntArray(n + 1)
        ret[0] = 0
        ret[1] = 1
        ret[2] = 1

        for (index in 3..n) {
            ret[index] = index.count()
        }

        return ret
    }

    private fun Int.count(): Int {  
        var current = this
        var count = 0

        while(true) {
            val mod = current % 2
            if (mod == 1) count++
            current = current / 2

            if (current == 1) {
                count += 1
                break
            }
        }

        return count
    }
}
