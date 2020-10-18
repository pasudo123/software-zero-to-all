class Solution {
    public int thirdMax(int[] nums) {
        
        // 일단 오름차순 정렬 수행
        Arrays.sort(nums);
        
        // 가장 최초 max 값 설정
        int max = nums[nums.length - 1];
        int ret = max;
        
        int count = 1;
        for(int i = nums.length - 2; i >= 0; i--) {
            if(nums[i] == max) {
                continue;
            }
            
            if(count < 3) {
                max = nums[i];
                count++;
            }
        }
        
        // max 값이 세번째 존재하면 max return, 그렇지 않으면 가장 큰 max 값 리턴
        return (count == 3) ? max : ret;
    }
}