class Solution {
    fun gcdOfStrings(str1: String, str2: String): String {
        val str1Set = mutableSetOf<String>()
        str1.forEach { str1Set.add("$it") }

        val str2Set = mutableSetOf<String>()
        str2.forEach { str2Set.add("$it") }

        if (str1Set != str2Set) {
            return ""
        }

        val minStr = if (str1.count() < str2.count()) {
            str1
        } else {
            str2
        }

        for (index in minStr.count() downTo 0) {
            val part = minStr.substring(0, index)
            if (str1.isDividePossible(part) && str2.isDividePossible(part)) {
                return part
            }
        }

        return ""
    }

    private fun String.isDividePossible(part: String): Boolean {
        if (part.isBlank() || this.length % part.count() != 0) return false

        val size = this.length / part.count()
        var result = ""
        for (i in 1..size) {
            result += part
        }

        return this == result
    }
}
