class Solution {
    fun reversePrefix(word: String, ch: Char): String {

        val stack = Stack<Char>()
        val builder = StringBuilder()
        var newIndex = -1

        for (index in word.indices) {
            stack.push(word[index])

            if (stack.peek() == ch) {
                while (stack.isNotEmpty()) {
                    builder.append(stack.pop())
                }

                newIndex = index + 1
                break
            }
        }

        if (newIndex == -1) {
            return word
        }

        for (index in newIndex until word.length) {
            builder.append(word[index])
        }

        return builder.toString()
    }
}
