class Solution {
    fun areNumbersAscending(s: String): Boolean {
        val regex = Regex("\\d+")
        val words = s.split(" ")
        val numbers = words.filter {
            regex.containsMatchIn(it)
        }.map {
            it.toInt()
        }

        var currentNumber = numbers.first()
        for (index in 1 until numbers.size) {
            if (numbers[index] <= currentNumber) {
                return false
            }

            currentNumber = numbers[index]
        }

        return true
    }
}
