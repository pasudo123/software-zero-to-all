class Solution {
    fun numberOfSteps (num: Int): Int {
        
        var step = 0
        var currentNum = num
        while(currentNum != 0) {
            currentNum = if(currentNum % 2 == 0) (currentNum / 2) else (currentNum - 1)
            step++
        }
        
        return step
    }
}
