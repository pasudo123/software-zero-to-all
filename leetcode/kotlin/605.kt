class Solution {
    fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
        var count = n
        
        flowerbed.forEachIndexed { index, element ->
            if (element == 1) return@forEachIndexed
            
            val left = if (index - 1 >= 0) flowerbed[index-1] == 0 else true
            val right = if (index + 1 < flowerbed.size) flowerbed[index+1] == 0 else true

            if (left && right) {
                flowerbed[index] = 1
                count--

                if (count == 0) return true
            }
        }

        return count <= 0
    }
}
