/**
 * 직접 구현한 Stack 클래스.
 * 거기에 따른 연산처리.
 *
**/
class Solution {
    fun removeStars(s: String): String {
        val stack = Stack(s.count())

        s.forEach { element ->
            if (element == '*') {
                stack.pop()
                return@forEach
            }

            stack.push(element)
        }

        return stack.toResult()
    }
}

class Stack(
    val size: Int
) {
    var currentIndex = 0
    val array = CharArray(size)

    fun pop() {
        if (currentIndex <= 0) {
            currentIndex = 0
            return
        }
        array[--currentIndex] = ' '
    }

    fun push(element: Char) {
        array[currentIndex++] = element
    }

    fun toResult(): String {
        var result = ""
        array.forEachIndexed { index, element ->
            if (element == ' ') {}
            else result += element

            if (currentIndex - 1 <= index) {
                return result
            }
        }

        return result.trim()
    }
}
