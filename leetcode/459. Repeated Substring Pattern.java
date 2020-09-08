class Solution {
    public boolean repeatedSubstringPattern(String s) {
        
        final int size = s.length();
        String substring = "";
        String newSubstring = "";
        boolean isPossible = true;
        
        for(int i = 0; i < size; i++) {
            substring += s.charAt(i);
            final int length = substring.length();
            
            isPossible = true;
            newSubstring = "";
            
            for(int j = i + 1; j < size; j += length) {
                if(j + length > size) {
                    isPossible = false;
                    break;
                }
                
                newSubstring = s.substring(j, j + length);
                
                if(!substring.equals(newSubstring)) {
                    isPossible = false;
                    break;
                }
            }
            
            if(isPossible && newSubstring.length() != 0) {
                return true;
            }
        }
        
        return false;
    }
}
