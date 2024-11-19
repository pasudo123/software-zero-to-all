class Solution {
    fun reverseWords(s: String): String {
        val elements = s.toCharArray()
        var lastIndex = elements.size - 1
        var finalResult = ""

        var subResult = ""
        while (lastIndex >= 0) {
            if (elements[lastIndex] == ' ') {
                lastIndex--

                if (subResult.isNotBlank()) {
                    var currentLastIndex = subResult.count() - 1
                    while (currentLastIndex >= 0) {
                        finalResult += subResult[currentLastIndex]
                        currentLastIndex--
                    }
                    subResult = ""
                    finalResult += " "
                }
                continue
            } else {
                subResult += elements[lastIndex]
            }

            lastIndex--
        }

        if (subResult.isNotBlank()) {
            var currentLastIndex = subResult.count() - 1
            while (currentLastIndex >= 0) {
                finalResult += subResult[currentLastIndex]
                currentLastIndex--
            }
        }

        return finalResult.trim()
    }

    fun reverseWords2(s: String): String {
        val line = s.trim().replace(Regex("\\s+"), " ")
        val elements = line.split(" ")
        var result = ""
        elements.reversed().forEach { element ->
            result += element
            result += " "
        }

        return result.trim()
    }
}
