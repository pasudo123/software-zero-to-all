class Solution {
    fun countMatches(items: List<List<String>>, ruleKey: String, ruleValue: String): Int {
        val findIndex = when(ruleKey) {
            "type" -> 0
            "color" -> 1
            else -> 2
        }
        
        var count = 0
        items.forEach {
            count += if(it.get(findIndex) == ruleValue) 1 else 0 
        }
        
        return count
    }
}
