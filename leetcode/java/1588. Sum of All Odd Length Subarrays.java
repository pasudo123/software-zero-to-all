class Solution {
    fun sumOddLengthSubarrays(arr: IntArray): Int {
        var sum = 0
        
        for(index in 0 until arr.count()) {
            sum += arr[index]
            sum += sumByOddLength(index, index + 2, arr)
        }
        
        return sum
    }
    
    fun sumByOddLength(start: Int, end: Int, arr:IntArray): Int {
        
        if(end >= arr.count()) {
            return 0
        }
        
        var sum = 0
        
        for(index in start..end) {
            sum += arr[index]
        }
        
        sum += sumByOddLength(start, end + 2, arr)
        return sum
    }
}
