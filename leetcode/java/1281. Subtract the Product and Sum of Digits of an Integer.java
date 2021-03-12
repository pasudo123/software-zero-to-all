class Solution {
    public int subtractProductAndSum(int n) {
        int currentN = n;
        int productNum = 1;
        int sumNum = 0;
        
        while(currentN >= 10) {
            productNum *= (currentN % 10);
            sumNum += (currentN % 10);
            currentN = currentN / 10;
        }
        
        productNum *= currentN;
        sumNum += currentN;
        
        return productNum - sumNum;
    }
}
