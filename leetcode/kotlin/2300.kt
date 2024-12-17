/**
 * chatGPT 도움을 받아 풀었다.
 * 다시 풀어보는게 필요하다.
 */
class Solution {
    fun successfulPairs(spells: IntArray, potions: IntArray, success: Long): IntArray {
        val spellSize = spells.size
        val potionSize = potions.size

        val spellGroupByValue = spells.mapIndexed { index, value -> value to index }.sortedBy { it.first }
        val sortedPotions = potions.sorted()

        val result = IntArray(spellSize)
        var potionIndex = potionSize - 1

        for (index in 0 until spellSize) {
            val (spellValue, spellIndex) = spellGroupByValue[index]!!

            while (potionIndex >= 0 && spellValue.toLong() * sortedPotions[potionIndex].toLong() >= success) {
                potionIndex--
            }

            result[spellIndex] = potionSize - (potionIndex + 1)
        }

        return result
    }
}
