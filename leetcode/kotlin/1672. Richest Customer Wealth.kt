class Solution {
    fun maximumWealth(accounts: Array<IntArray>): Int {
        
        // leetcode 내 kotlin 버전이 낮아서 그런건지 해당 함수는 작동 안 됨
        // return accounts.map {
        //     it.sum()
        // }.maxOrNull() ?: 0
        
        val sums = accounts.map {
            it.sum()
        }
        
        var ret = -1
        sums.forEach {
            ret = if(it > ret) it else ret
        }
        
        return ret
    }
}
