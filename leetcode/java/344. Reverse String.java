class Solution {
    public void reverseString(char[] s) {
        recursive(s, 0, s.length - 1);
    }
    
    private void recursive(char[] s, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        
        char temp = s[startIndex];
        s[startIndex] = s[endIndex];
        s[endIndex] = temp;
        
        recursive(s, startIndex + 1, endIndex - 1);
    }
}