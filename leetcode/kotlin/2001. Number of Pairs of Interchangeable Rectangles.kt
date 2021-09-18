class Solution {
    fun interchangeableRectangles(rectangles: Array<IntArray>): Long {
        val group: MutableMap<Double, Long> = mutableMapOf()
        var count = 0L
        val calculateResults = rectangles.map { rectangle ->
            rectangle[0].toDouble() / rectangle[1]
        }

        for (index in calculateResults.indices) {

            val currentValue = calculateResults[index]

            if (group.containsKey(currentValue)) {
                group[currentValue] = group[currentValue]!! + 1L
                continue
            }

            group[currentValue] = 1
        }

        group.forEach { groupElement ->
            val size = groupElement.value
            val optSize = size - 1
            count += (optSize * (optSize + 1) / 2)
        }

        return count
    }
}
