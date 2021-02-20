class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {

        // nums[i] 의 값을 인덱스로 이용한다.
        for(int i = 0; i < nums.length; i++) {
            if(nums[Math.abs(nums[i]) - 1] > 0) {
                nums[Math.abs(nums[i]) - 1] *= -1;
            }
        }
        
        // 나왔던 값들은 음수로 값을 변경한다.
        final List<Integer> list = new ArrayList<>();

        // 양수인 값은 현재 인덱스가 나오지 않은 값들로 판단해서 i + 1 로 값을 삽입한다.
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] >= 0) {
                list.add(i+1);
            }
        }
        
        return list;
    }
}