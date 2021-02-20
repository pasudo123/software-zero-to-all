/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {    
    
    public int firstBadVersion(int n) {
        // 바이너리 서치로 해결하는데, upper_bound 개념이 필요함
        int start = 1;
        int end = n;
        
        while(start < end) {
            // 나는 처음에 (start + end) / 2 로 수행했는데
            // 가만 생각하니 start 값과 end 값을 더했을 때 Integer 범위를 초과할 수도 있겠다는 생각이 든다.
            // 그렇게 따지면, start + (end - start) / 2 로 가는게 바람직한듯하다.
            int mid = start + (end - start) / 2;
            
            if(isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        
        return start;
    }
}
