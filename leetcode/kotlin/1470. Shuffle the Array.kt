class Solution {
    fun shuffle(nums: IntArray, n: Int): IntArray {
        // return mySolution1(nums, n)
        return leetCodeSolution(nums, n)
    }
    
    fun mySolution1(nums: IntArray, n: Int): IntArray {
                
        val list = mutableListOf<Int>()
        for (index in 0 until n) {
            list.add(nums.get(index))
            list.add(nums.get(index+n))
        }
        
        val ret = IntArray(nums.count())
        var index = 0
        list.forEach {
            ret.set(index++, it)
        }
        
        return ret
    }
    
    fun leetCodeSolution(nums: IntArray, n: Int): IntArray {
        
        val ret = IntArray(nums.count())
        var retIndex = 0
        var index = 0
        var nIndex = n
        while (index < n) {
            ret[retIndex++] = nums.get(index++)
            ret[retIndex++] = nums.get(nIndex++)
        }
        
        return ret
    }
}
