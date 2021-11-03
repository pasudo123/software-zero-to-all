class Solution {
    fun smallestEqual(nums: IntArray): Int {

       var ret = -1

        nums.forEachIndexed{ index, num ->
            val remain = index % 10
            if(remain == num && ret == -1) {
                ret = index
            }
        }

        return ret
    }
}
