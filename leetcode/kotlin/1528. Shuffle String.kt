class Solution {
    fun restoreString(s: String, indices: IntArray): String {
        val array = CharArray(indices.count())
        
        for (index in 0 until indices.count()) {
            array[indices[index]] = s.get(index)
        }
        
        return array.joinToString("")
    }
}
