// https://leetcode.com/problems/merge-strings-alternately/description/
class Solution {
    fun mergeAlternately(word1: String, word2: String): String {
        val minSize = Math.min(word1.count(), word2.count())
        var text = ""

        for(index in 0..(minSize - 1)) {
            text += word1[index]
            text += word2[index]
        }

        if (word1.count() != minSize) {
            text += word1.substring(minSize, word1.count())
        }

        if (word2.count() != minSize) {
            text += word2.substring(minSize, word2.count())
        }

        return text
    }
}
