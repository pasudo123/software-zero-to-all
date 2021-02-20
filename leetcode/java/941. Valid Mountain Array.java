class Solution {
    public boolean validMountainArray(int[] A) {
        // incr , decr
        
        if(A.length < 3) {
            return false;
        }
        
        int currentHeight = A[0];
        int nextHeight = A[1];
        if(currentHeight >= nextHeight) {
            return false;
        }
        
        currentHeight = nextHeight;
        boolean isIncr = true;
        
        for(int i = 2; i < A.length; i++) {
            
            nextHeight = A[i];
            
            if(isIncr) {
                if(currentHeight == nextHeight) {
                    return false;
                }
                
                if(currentHeight > nextHeight) {
                    isIncr = false;
                }
                
                currentHeight = nextHeight;
                continue;
            }
            
            if(currentHeight <= nextHeight) {
                return false;
            }
            
            currentHeight = nextHeight;
        }
        
        return !isIncr;
    }
}