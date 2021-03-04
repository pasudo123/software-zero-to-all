class Solution {
    public String restoreString(String s, int[] indices) {
        final char[] array = new char[indices.length];
        
        for(int index : indices) {
            array[indices[index]] = s.charAt(index);
        }
        
        return String.valueOf(array);
    }
}
