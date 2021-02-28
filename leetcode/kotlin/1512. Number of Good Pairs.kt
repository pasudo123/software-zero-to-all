class Solution {
    fun numIdenticalPairs(nums: IntArray): Int {
        // return mySolution(nums)
        return mySolution2(nums)
    }
    
    fun mySolution(nums: IntArray): Int {
        
        var count = 0
        
        for(index in 0 until nums.count()) {
            for(jndex in index + 1 until nums.count()) {
                count += if(nums.get(index) == nums.get(jndex)) 1 else 0
            }
        }
        
        return count
    }
    
    fun mySolution2(nums: IntArray): Int {
        
        var count = 0
        val store = mutableMapOf<Int, Int>()
        
        for(index in 0 until nums.count()) {
            
            var partialCount = store.getOrDefault(nums[index], -1)
            
            if(partialCount != -1) {
                count += (partialCount - 1)
                store.put(nums[index], partialCount - 1)
                continue
            }
            
            var subCount = 0
            for(jndex in index + 1 until nums.count()) {    
                subCount += if(nums.get(index) == nums.get(jndex)) 1 else 0
            }
            
            store.put(nums[index], subCount)
            count += subCount
        }
        
        return count
    }
}
