import java.util.*;

class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        final List<Boolean> bools = new ArrayList<>();
        int max = -1;
        
        for(int i = 0; i < candies.length; i++) {
            max = Math.max(max, candies[i]);
        }
        
        for(int value : candies) {
            // bools.add((value + extraCandies >= max) ? true : false);
            // 삼항연산자로 하지 않고 한번에 줄일 수 있다...
            bools.add(value + extraCandies >= max);
        }
        
        return bools;
    }
}
