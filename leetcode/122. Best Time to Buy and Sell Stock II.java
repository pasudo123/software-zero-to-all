class Solution {
    public int maxProfit(int[] prices) {
        // return bruteforce(prices, 0);
        // return valleyApproach(prices);
        return simpleOnePass(prices);
    }
    
    public int simpleOnePass(final int[] prices) {
        int maxProfit = 0;
        
        for(int i = 1; i < prices.length; i++) {
            if(prices[i] > prices[i-1]) {
                maxProfit += (prices[i] - prices[i-1]);
            }
        }
        
        return maxProfit;
    }
    
    public int valleyApproach(int[] prices) {
        int start = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxProfit = 0;
        
        while (start < prices.length - 1) {
            while(start < prices.length - 1 && prices[start] >= prices[start + 1]) {
                start++;
            }
            
            valley = prices[start];
            
            while(start < prices.length - 1 && prices[start] <= prices[start + 1]) {
                start++;
            }
            
            peak = prices[start];
            
            maxProfit += peak - valley;
        }
        
        return maxProfit;
    }
    
    // 시간초과 
    public int bruteforce(int[] prices, int index) {
        if (index >= prices.length) {
            return 0;
        }        
        int result = 0;
        
        for(int start = index; start < prices.length; start++) {
            int maxProfit = 0;
            
            for(int subStart = start + 1; subStart < prices.length; subStart++) {
                if(prices[start] < prices[subStart]) {
                    // calculate 가 하나의 트랜잭션 단위.
                    int profit = bruteforce(prices, subStart + 1) + (prices[subStart] - prices[start]);
                    maxProfit = Math.max(profit, maxProfit);
                }
            }
            
            result = Math.max(maxProfit, result);
        }
        return result;
    }
}
