class Solution {
    public int findNumbers(int[] nums) {
        
        int result = 0;
        
        for(int num : nums)  {
            int digits = getDigit(num);    
            if(digits % 2 == 0) {
                result += 1;
            }
        }
        
        return result;
    }
    
    private int getDigit(int n) {
        
        int count = 0;
        
        while(n > 0) {
            n = n / 10;
            count++;
        }
        
        return count;
    }
}