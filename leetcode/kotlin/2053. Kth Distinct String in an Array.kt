class Solution {
    fun kthDistinct(arr: Array<String>, k: Int): String {
        val map: MutableMap<String, Boolean> = LinkedHashMap()

        for (element in arr) {
            if (map.containsKey(element)) {
                map[element] = false
                continue
            }

            map[element] = true
        }

        val ret = map.filter { it.value }
            .toList()
            .map { it.first }
        
        if (ret.size < k) {
            return ""
        }
        
        return ret.take(k).last()
    }
}
