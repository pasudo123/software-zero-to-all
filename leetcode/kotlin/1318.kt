/**
 * 비트연산 문제.
 * 비트를 쉽게 처리하는 방법이 중요하다. 내 코드는 그렇지 않다.
 */
class Solution {
    fun minFlips(a: Int, b: Int, c: Int): Int {
        var aCharArray = a.getBits()
        var bCharArray = b.getBits()
        var cCharArray = c.getBits()
        val maxSize = max(aCharArray.size, max(bCharArray.size, cCharArray.size))

        aCharArray = aCharArray.getNewBitsArrayBy(maxSize)
        bCharArray = bCharArray.getNewBitsArrayBy(maxSize)
        cCharArray = cCharArray.getNewBitsArrayBy(maxSize)

        var count = 0

        for (index in aCharArray.indices) {
            val aBit = aCharArray[index] == '1'
            val bBit = bCharArray[index] == '1'
            val cBit = cCharArray[index] == '1'

            val result = aBit or bBit
            if (cBit == result) continue

            if (cBit) {
                count++
            } else {
                if (aBit) count++
                if (bBit) count++
            }
        }

        return count
    }

    private fun CharArray.getNewBitsArrayBy(size: Int): CharArray {
        return if (this.size < size) {
            val newArray = CharArray(size) { '0' }
            this.copyInto(newArray, destinationOffset = size - this.size)
            newArray
        } else this
    }

    private fun Int.getBits(): CharArray {
        return this.toString(2).toCharArray()
    }
}
