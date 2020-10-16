class Solution {
    /**
     * 0 을 이동시킨다는 개념보단, 0이 아닌 숫자를 이동시키고
     * 나머지 인덱스를 0으로 채운다는 개념으로 접근 
     */
    public void moveZeroes(int[] nums) {
        
        int currentIndex = 0;
        
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                nums[currentIndex++] = nums[i];
            }
        }
        
        for(int i = currentIndex; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}