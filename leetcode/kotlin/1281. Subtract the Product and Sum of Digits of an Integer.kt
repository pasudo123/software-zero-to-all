class Solution {
    fun subtractProductAndSum(n: Int): Int {
        var currentN = n
        var productNum = 1
        var sumNum = 0
        
        while (currentN >= 10) {
            productNum *= (currentN % 10)
            sumNum += (currentN % 10)
            currentN /= 10
        }
        
        productNum *= currentN
        sumNum += currentN
        
        return productNum - sumNum
    }
}
