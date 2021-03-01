class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        final Map<Character, Object> store = new HashMap<>();
        
        for(int i = 0; i < jewels.length(); i++) {
            store.put(jewels.charAt(i), new Object());
        }
        
        int ret = 0;
        for(int i = 0; i < stones.length(); i++) {
            final char stone = stones.charAt(i);
            
            ret += (store.get(stone) == null) ? 0 : 1;
        }
        
        return ret;
    }
}
