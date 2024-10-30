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

class Solution {
    fun reverseVowels(s: String): String {
        val vowels = setOf("a", "A", "e", "E", "i", "I", "o", "O", "u", "U")
        val charArray = s.toCharArray()
        var start = 0
        var end = charArray.size - 1

        while (start < end) {

            while (start < end && (charArray[start].toString() in vowels).not()) {
                start++
            }
            while (start < end && (charArray[end].toString() in vowels).not()) {
                end--
            }

            val temp = charArray[start]
            charArray[start] = charArray[end]
            charArray[end] = temp

            start++
            end--
        }

        return String(charArray)
    }
}
