/**
 * 운석충돌시 stack 구현.
 * stack 구현이 중요한게 아니라 그걸 바탕으로 돌아가는 비즈니스 로직이 복잡하다.
 *
**/
class Solution {
    fun asteroidCollision(asteroids: IntArray): IntArray {
        val stack = Stack(asteroids.size)

        asteroids.forEach { element ->
            var current = stack.peek()

            if (current == 0 || element > 0) {
                stack.push(element)
                return@forEach
            }

            if (element < 0) {
                while(current > 0 && abs(element) > current) {
                    stack.pop()
                    if (stack.lasted() || stack.peek() == 0) {
                        stack.push(element)
                        return@forEach
                    }
                    current = stack.peek()
                }

                if (abs(element) < current) {
                    return@forEach
                }

                if (abs(element) == current) {
                    stack.pop()
                    return@forEach
                }
            }

            stack.push(element)
        }

        return stack.toResult()
    }
}

class Stack(
    size: Int
) {
    private var index = 0
    private val array = IntArray(size)

    fun push(element: Int) {
        array[index++] = element
    }

    fun pop() {
        array[--index] = 0
    }

    fun peek(): Int {
        if (index == 0) return 0
        return array[index - 1]
    }


    fun lasted(): Boolean { 
        return index == 0
    }

    fun toResult(): IntArray {
        return array.filterIndexed { currentIndex, _ ->
            currentIndex < index
        }.toIntArray()
    }
}
