class Solution {
    fun toLowerCase(str: String): String {
        val array = CharArray(str.length)
        var index = 0
        
        for(element in str) {
            if(element >= 'A' && element <= 'Z') {
                array[index++] = (element + 32)
                continue
            }
            
            array[index++] = element
        }
        
        return String(array)
    }
}
