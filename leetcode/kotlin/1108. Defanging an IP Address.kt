class Solution {
    fun defangIPaddr(address: String): String {
        // return mySolution1(address)
        // return mySolution2(address)
        return mySolution3(address)
    }
    
    // 스트링에 존재하는 함수를 이용
    private fun mySolution1(address: String): String {
        val numbers = address.split(".")
        return numbers.joinToString("[.]")
    }
    
    // for 구문을 통함
    private fun mySolution2(address: String): String {
        var ret = ""
        
        for(index in 0 until address.count()) {
            ret += when(address.get(index)) {
                '.' -> "[.]"
                else -> address.get(index)
            }
        }
        
        return ret
    }
    
    // 다른 방식의 for 구문을 통해서 접근
    private fun mySolution3(address: String): String {
        var ret = ""
        
        for(element in address) {
            ret += if(element == '.') "[.]" else element
        }
        
        return ret
    }
}
