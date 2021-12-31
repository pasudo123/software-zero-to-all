class Solution {
    companion object {
        private const val PLUS = "++"
        private const val MINUS = "--"
    }
    
    fun finalValueAfterOperations(operations: Array<String>): Int {
        var x = 0

        operations.forEach { operation -> 
            if (operation.contains(PLUS)) {
                x++
            } else {
                x--
            }
        }
        
        return x
    }
}
