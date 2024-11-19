class Solution {
    fun isSubsequence(s: String, t: String): Boolean {
        val current = s.toCharArray()
        var startIndex = 0

        t.forEach { tElement ->    
            if(startIndex == current.size) {
                return true
            }

            if (current[startIndex] == tElement) {
                startIndex++
            }
        }

        return startIndex == current.size
    }
}
