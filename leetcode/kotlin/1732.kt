class Solution {
    fun largestAltitude(gain: IntArray): Int {
        var currnetGain = 0
        var maxGain = 0

        for (index in 0 until gain.size) {
            currnetGain += gain[index]
            maxGain = max(maxGain, currnetGain)
        }

        return maxGain
    }
}
