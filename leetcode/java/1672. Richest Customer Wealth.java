class Solution {
    public int maximumWealth(int[][] accounts) {
        
        final int rows = accounts.length;
        final int cols = accounts[0].length;
        
        int ret = 0;
        int sum = 0;
        for(int i = 0; i < rows; i++) {
            
            sum = 0;
            
            for(int j = 0; j < cols; j++) {
                sum += accounts[i][j];
            }
            
            ret = (sum > ret) ? sum : ret;
        }
        
        return ret;
    }
}
