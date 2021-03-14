class Solution {
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int ret = 0;
        
        for(int i = 0; i < startTime.length; i++) {
            if(startTime[i] <= queryTime && queryTime <= endTime[i]) {
                ret++;
            }
        }
        
        return ret;
    }
}
