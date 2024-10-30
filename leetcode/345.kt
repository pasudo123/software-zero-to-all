class Solution {
    fun reverseVowels(s: String): String {
        val vowels = setOf("a", "A", "e", "E", "i", "I", "o", "O", "u", "U")
        val stack = ArrayDeque<String>()

        s.forEach { element -> 
            if (vowels.contains("$element")) {
                stack.addLast("$element")
            }
        }

        var result = ""
        s.forEach { element ->
            if (vowels.contains("$element")) {
                result = "$result${stack.removeLast()}"
            } else {
                result = "$result$element"
            }
        }

        return result
    }
}
