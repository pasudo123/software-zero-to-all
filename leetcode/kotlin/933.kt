/**
 * queue 형태로 이용한다.
 * pingTimes 에 데이터를 지속적으로 넣으면서 range 에 포함되는지 여부를 확인한다.
 */
class RecentCounter() {

    var currentIndex = 0
    val pingTimes = mutableListOf<Int>()

    fun ping(t: Int): Int {
        val start = t - 3000

        pingTimes.add(t)

        if (pingTimes.size == 1) {
            return 1
        }

        var plusCount = 0

        for (index in currentIndex until pingTimes.size) {
            val currentTime = pingTimes[index]
            if (currentTime < start) {
                plusCount++
            } else {
                break
            }
        }

        currentIndex = currentIndex + plusCount
        return pingTimes.size - currentIndex
    }

}

/**
 * Your RecentCounter object will be instantiated and called as such:
 * var obj = RecentCounter()
 * var param_1 = obj.ping(t)
 */
