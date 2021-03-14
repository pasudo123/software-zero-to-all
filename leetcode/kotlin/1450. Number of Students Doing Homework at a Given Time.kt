class Solution {
    fun busyStudent(startTime: IntArray, endTime: IntArray, queryTime: Int): Int {
        
        var ret = 0
        
        for (index in 0 until startTime.size) {
            if(startTime[index] <= queryTime && queryTime <= endTime[index]) {
                ret++        
            }
        }
        
        return ret
    }
}
