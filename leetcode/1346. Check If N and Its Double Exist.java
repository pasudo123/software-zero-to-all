class Solution {
    public boolean checkIfExist(int[] arr) {   
        
        Map<Integer, Integer> store = new HashMap<>();
        
        for(int i = 0; i < arr.length; i++) {
            store.put(arr[i] * 2, i);
        }
        
        for(int i = 0; i < arr.length; i++) {
            if(store.containsKey(arr[i]) && store.get(arr[i]) != i){
                return true;
            }
        }
                
        return false;
    }
}