class Solution {
    public boolean backspaceCompare(String S, String T) {
        final char[] sourceArray = S.toCharArray();
        final char[] targetArray = T.toCharArray();
        
        final StringBuilder sourceBuilder = new StringBuilder();
        final StringBuilder targetBuilder = new StringBuilder();
        
        int count = 0;
        
        for(int i = sourceArray.length - 1; i >= 0; i--) {
            if(sourceArray[i] == '#') {
                count++;
                continue;
            }
            
            if(count > 0) {
                count--;
                continue;
            }
            
            sourceBuilder.append(sourceArray[i]);
        }
        
        count = 0;
        for(int i = targetArray.length - 1; i >= 0; i--) {
            if(targetArray[i] == '#') {
                count++;
                continue;
            }
            
            if(count > 0) {
                count--;
                continue;
            }
            
            targetBuilder.append(targetArray[i]);
        }
        
        
        return sourceBuilder.toString().equals(targetBuilder.toString());
    }
}
