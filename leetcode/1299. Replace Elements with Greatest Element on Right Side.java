class Solution {
    public int[] replaceElements(int[] arr) {
        
        int back = arr.length - 1;
        int max = arr[back];
        arr[back] = -1;
        int currentValue = 0;
        
        for(int i = back - 1; i >= 0; i--) {
            currentValue = arr[i];     
            arr[i] = max;
            max = Math.max(max, currentValue);
        }
        
        return arr;
    }
}