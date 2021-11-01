class Solution {
    companion object {
        val REGEXP_0 = Regex("^[a-z]+\$")
        val REGEXP_1 = Regex("^([a-z]+)-([a-z]+)([\\.\\!\\,])?\$")
        val REGEXP_2 = Regex("^([a-z]+)([\\.\\!\\,])\$")
        val REGEXP_3 = Regex("^([\\.\\!\\,])\$")
    }

    fun countValidWords(sentence: String): Int {
        val words = sentence.split(" ")
            .filter {
                REGEXP_0.containsMatchIn(it) 
                        || REGEXP_1.containsMatchIn(it) 
                        || REGEXP_2.containsMatchIn(it)
                        || REGEXP_3.containsMatchIn(it)
            }

        return words.size
    }
}
