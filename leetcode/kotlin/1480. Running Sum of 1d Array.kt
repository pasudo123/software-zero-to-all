class Solution {
    fun runningSum(nums: IntArray): IntArray {
        return mySolution1(nums)
    }
    
    fun mySolution1(nums: IntArray): IntArray {
        val ret = IntArray(nums.size)
        // ret[0] = nums[0]
        ret[0] = nums.get(0)
        
        for(i in 1 until nums.size) {
            // ret[i] = ret[i-1] + nums[i]
            ret[i] = ret.get(i-1) + nums.get(i)
        }
        
        return ret
    }
}
