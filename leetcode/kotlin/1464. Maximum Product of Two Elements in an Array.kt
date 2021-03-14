class Solution {
    fun maxProduct(nums: IntArray): Int {
        var max1 = 0
        var max2 = 0
        
        for (num in nums) {
            if(max1 < num) {
                max2 = max1
                max1 = num
                continue
            }
            
            if(max2 < num) {
                max2 = num
            }
        }
        
        return (max1 - 1) * (max2 - 1)
    }
}
