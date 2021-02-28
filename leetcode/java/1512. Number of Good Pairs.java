class Solution {
    public int numIdenticalPairs(int[] nums) {
        
        int i = 0;
        int j = 0;
        int ret = 0;
        
        for(i = 0; i < nums.length; i++) {
            for(j = i + 1; j < nums.length; j++) {
                ret += (nums[i] == nums[j]) ? 1 : 0;
            }
        }
        
        return ret;
    }
}
