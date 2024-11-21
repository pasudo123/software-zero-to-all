/**
피보나치 수열처럼 로직 작성.
메모이제이션 느낌이 강한다. 기존에 메모리에 저장하고 그걸 다시 이용하는..
**/
class Solution {
    fun tribonacci(n: Int): Int {
        val numbers = IntArray(n + 3) { 0 }
        numbers[0] = 0
        numbers[1] = 1
        numbers[2] = 1

        if (n <= 2) {
            return numbers[n]
        }

        for (index in 3..n) {
            numbers[index] = numbers[index - 3] + numbers[index - 2] + numbers[index - 1]
        }

        return numbers[n]
    }
}
