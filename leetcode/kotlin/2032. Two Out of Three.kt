class Solution {
    fun twoOutOfThree(nums1: IntArray, nums2: IntArray, nums3: IntArray): List<Int> {
        val result: MutableList<Int> = mutableListOf()

        val map: MutableMap<Int, Boolean> = mutableMapOf()

        nums1.distinct().forEach {
            map[it] = true
        }

        nums2.distinct().forEach {
            if(map.containsKey(it)) {
                result.add(it)
            }
            map[it] = true
        }

        nums3.distinct().forEach {
            if(map.containsKey(it)) {
                result.add(it)
            }
            map[it] = true
        }

        return result.distinct().toList()
    }
}
