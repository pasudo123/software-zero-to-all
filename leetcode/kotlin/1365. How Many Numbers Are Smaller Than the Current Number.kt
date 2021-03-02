class Solution {
    fun smallerNumbersThanCurrent(nums: IntArray): IntArray {
        val store = mutableMapOf<Int, Int>()
        val copyValues = nums.copyOf()
        
        var currentValue = 0
        var count = 0
        store.put(currentValue, 0)
        copyValues.sort()
        
        copyValues.forEach{value -> 
            if(currentValue != value) {
                store.put(value, count + store.get(currentValue)!!)
                currentValue = value
                count = 0
            }
            
            count++
        }
        
        val ret = IntArray(nums.count())
        for(i in 0 until nums.count()) {
            ret[i] = store.get(nums[i])!!
        }
        
        return ret
    }
}
