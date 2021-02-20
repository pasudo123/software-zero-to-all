class Solution {
    public int[] sortArrayByParity(int[] A) {
        int startIndex = 0;
        int endIndex = A.length - 1;
        
        while(startIndex < endIndex) {
            while(startIndex < A.length && A[startIndex] % 2 == 0) {
                startIndex++;
            }
            
            while(endIndex >= 0 && A[endIndex] % 2 != 0) {
                endIndex--;
            }
            
            // edge case : [0, 2]
            if(startIndex >= A.length || startIndex > endIndex) {
                break;
            }
            
            int temp = A[startIndex];
            A[startIndex] = A[endIndex];
            A[endIndex] = temp;
            
            startIndex++;
            endIndex--;
        }

        return A;
    }
}