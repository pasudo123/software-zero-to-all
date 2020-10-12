class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int resultLastIndex = m + n - 1;
        
        int lastIndex1 = m - 1;
        int lastIndex2 = n - 1;
        
        while(lastIndex1 >= 0 && lastIndex2 >= 0) {
            if(nums1[lastIndex1] < nums2[lastIndex2]) {
                nums1[resultLastIndex--] = nums2[lastIndex2--];
            } else {
                nums1[resultLastIndex--] = nums1[lastIndex1--];
            }
        }
        
        while(lastIndex2 >= 0) {
            nums1[resultLastIndex--] = nums2[lastIndex2--];
        }
    }
}