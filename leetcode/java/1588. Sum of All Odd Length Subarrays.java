class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        
        int sum = 0;
        
        for(int i = 0; i < arr.length; i++) {
            sum += arr[i];
            sum += sumByOddLength(i, i + 2, arr);
        }
        
        return sum;
    }
    
    private int sumByOddLength(final int start, final int end, final int[] arr) {
        
        int sum = 0;
        
        if(end >= arr.length) {
            return sum;
        }
        
        for(int i = start; i <= end; i++) {
            sum += arr[i];    
        }
        
        sum += sumByOddLength(start, end + 2, arr);
        return sum;
    }
}
