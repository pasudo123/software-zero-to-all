class Solution {

    private val buckets = setOf('a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U')

    fun maxVowels(s: String, k: Int): Int {
        var maxCount = 0
        var subResultCount = 0
        var queueChar = 'q'

        for (index in 0 until k) {
            if (index == 0) {
                queueChar = s[index]
            }

            if(buckets.contains(s[index])) {
                subResultCount++
            }
        }

        maxCount = max(maxCount, subResultCount)
        val size = s.count()
        
        for (index in 0 until size) {
            val lastIndex = index + k
            if (lastIndex >= size) break

            if (buckets.contains(s[lastIndex])) {
               subResultCount++ 
            }

            if (buckets.contains(queueChar)) {
                subResultCount--
            }

            maxCount = max(maxCount, subResultCount)
            queueChar = s[index + 1]
        }

        return maxCount
    }
}
