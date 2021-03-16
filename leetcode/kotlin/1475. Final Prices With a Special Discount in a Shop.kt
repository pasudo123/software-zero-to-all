class Solution {
    fun finalPrices(prices: IntArray): IntArray {
        val ret = IntArray(prices.size) {0}
                
        for(i in 0 until prices.size) {
            
            ret[i] = prices[i]
            
            for(j in (i+1) until prices.size) {
                if(prices[j] <= prices[i]) {
                    ret[i] = prices[i] - prices[j]
                    break
                }
            }
        }
        
        return ret
    }
}
