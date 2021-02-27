class Solution {
    public int[] shuffle(int[] nums, int n) {
        // return mySolution(nums, n);
        return leetCodeSolution(nums, n);
    }
    
    private int[] mySolution(int[] nums, int n) {
        final List<int[]> list = new ArrayList<>();
        
        for(int i = 0; i < n; i++) {
            list.add(new int[]{nums[i], nums[i + n]});
        }
        
        final int[] ret = new int[nums.length];
        
        int index = 0;
        for(int[] array : list) {
            ret[index++] = array[0];
            ret[index++] = array[1];
        }
        
        return ret;
    }
    
    private int[] leetCodeSolution(int[] nums, int n) {
        int[] ret = new int[nums.length];
        
        int index = 0;
        int retIndex = 0;
        while(retIndex < nums.length) {
            ret[retIndex++] = nums[index++];
            ret[retIndex++] = nums[n++];
        }
        
        return ret;
    }
}
