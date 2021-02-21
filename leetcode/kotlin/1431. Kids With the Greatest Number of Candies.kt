class Solution {
    /**
     * ith 배열값에 대해서 아이들은 캔디를 가지고 있다.
     * ith 번째 아이가 extraCadies 를 가짐으로써, 현재 cadies 의 가장 많은 캔디를 보다 같거나 동일한 캔디 수를 가질 수 있는가?
    **/
    fun kidsWithCandies(candies: IntArray, extraCandies: Int): BooleanArray {
        // return mySolution(candies, extraCandies)
        return mySolution2(candies, extraCandies)
    }
    
    private fun mySolution(candies: IntArray, extraCandies: Int): BooleanArray {
        val bools = BooleanArray(candies.count())
        
        val maxCandies = candies.max()
        val maxCandiesMinusOne = maxCandies!!.minus(1)
        
        for(index in 0 until candies.count()) {
            bools[index] = when(candies.get(index) + extraCandies) {
                in 0..maxCandiesMinusOne -> false
                else -> true
            }
        }
        
        return bools
    }
    
    private fun mySolution2(candies: IntArray, extraCandies: Int): BooleanArray {
        val bools = BooleanArray(candies.count())
        
        val maxCandies = candies.max()
        val maxCandiesMinusOne = maxCandies!!.minus(1)
        
        for(index in 0 until candies.count()) {
            val sum = candies.get(index) + extraCandies
            // bools[index] = if(sum >= maxCandies) true else false
            bools[index] = (sum >= maxCandies)
        }
        
        return bools
    }
}
