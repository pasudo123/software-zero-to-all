class Solution {
    public String toLowerCase(String str) {
        final char[] array = new char[str.length()];
        final int size = str.length();
        
        for(int i = 0; i < size; i++) {
            final char c = str.charAt(i);
            array[i] = c;
            
            if(array[i] >= 'A' && array[i] <= 'Z') {
                array[i] += 32;
            }
        }
        
        return new String(array);
    }
}
