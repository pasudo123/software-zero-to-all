class Solution {
    public int[] runningSum(int[] nums) {
        // return mySolution(nums);
        return mySolution2(nums);
    }
    
    private int[] mySolution(final int[] nums) {
        final int[] ret = new int[nums.length];
            
        for(int i = 0; i< nums.length; i++) {
            int sum = 0;
            for(int j = 0; j <= i; j++) {
                ret[i] += nums[j];
            }
        }
        
        return ret;
    }
    
    private int[] mySolution2(final int[] nums) {
        final int[] ret = new int[nums.length];
        
        ret[0] = nums[0];
        for(int i = 1; i < nums.length; i++) {
            ret[i] = ret[i - 1] + nums[i];
        }
        
        return ret;
    }
}
