class Solution {
    public int singleNumberFirst(int[] nums) {
        final Map<Integer, Object> map = new HashMap<>();

        for(int num : nums) {
            if(map.containsKey(num)) {
                map.remove(num);
                continue;
            }

            map.put(num, new Object());
        }

        return (int) map.keySet().iterator().next();
    }

    public int singleNumberSecond(int[] nums) {
        int preSum = 0;
        int postSum = 0;

        final Map<Integer, Object> map = new HashMap<>();

        for(int num : nums) {
            if(!map.containsKey(num)) {
                map.put(num, new Object());
                preSum += num;
            }

            postSum += num;
        }

        // single number = (2 * ( a + b + c )) - ( a + a + b + b + c) = `c`
        // `c` is single number
        return (2 * preSum) - (postSum);
    }
}
