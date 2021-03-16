class Solution {
    public int[] finalPrices(int[] prices) {
        final int[] ret = new int[prices.length];
        
        for(int i = 0; i < prices.length; i++) {
            ret[i] = prices[i];
                
            for(int j = i+1; j < prices.length; j++) {
                if(prices[i] >= prices[j]) {
                    ret[i] = prices[i] - prices[j];
                    break;
                }
            }
        }
        
        return ret;
    }
}
