class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        
        final List<Integer> list = new ArrayList<>();
        for(int element : nums) {
            list.add(element);
        }
        
        Collections.sort(list);
        
        final Map<Integer, Integer> store = new HashMap<>();
        
        int currentValue = list.get(0);
        int count = 0;
        
        store.put(currentValue, 0);
        for(int element : list) {
            if(element != currentValue) {
                // currentValue 보다 작은 값들의 합도 계속 누적시켜 합한다.
                store.put(element, count + store.get(currentValue));
                currentValue = element;
                count = 0;
            }
            
            count++;
        }
        
        final int[] ret = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            ret[i] = store.get(nums[i]);
        }
        
        return ret;
    }
}
