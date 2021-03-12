class Solution {
    fun numJewelsInStones(jewels: String, stones: String): Int {
        val store = mutableMapOf<Char, Object>();
        
        for(index in 0 until jewels.length) {
            store.put(jewels[index], Object())
        }
        
        var ret = 0
        for(index in 0 until stones.length) {
            ret += if(store.get(stones[index]) == null) 0 else 1
        }
        
        return ret
    }
}
