class Solution {
    public int subarraySum(int[] nums, int k) {
        return secondSolution(nums, k);
    }
    
    // n^3 으로 해결한다.
    // 시작과 끝구간을 선정해서 해당 구간 사이에 값을 순차적으로 더해준다.
    // 그리고 k 값과 일치되는 것들에 한해서 count 변수를 증가시켜준다.
    private int firstSolution(int[] nums, int k) {
        int count = 0;
        for(int start = 0; start < nums.length; start++) {
            for(int end = start + 1; end <= nums.length; end++) {
                int sum = 0;
                
                for(int i = start; i < end; i++) {
                    sum += nums[i];
                }
                
                if(sum == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    // 처음에 누적합을 지속적으로 구한다.
    // 부분배열을 구하는 대신에 누적합을 통해 부분배열 합을 유추할 수 있다.
    private int secondSolution(int[] nums, int k) {
        int count = 0;
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        
        for(int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        
        for(int start = 0; start < nums.length; start++) {
            for(int end = start + 1; end <= nums.length; end++) {
                if(sum[end] - sum[start] == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
