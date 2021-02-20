class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int count = 0;
        int pre = -1;
        int localCount = 0;
        
        for(int num : nums) {
            if(pre == -1 && num == 1) {
                pre = 1;
                localCount = 1;
                continue;
            }
            
            if(num == 0) {
                pre = -1;
                count = Math.max(localCount, count);
                localCount = 0;
                continue;
            }
            
            localCount++;
        }
        
        return Math.max(count, localCount);
    }
}