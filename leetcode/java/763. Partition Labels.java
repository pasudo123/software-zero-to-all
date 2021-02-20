class Solution {
    public List<Integer> partitionLabels(String S) {
        
        final int[] last = new int['z' - 'a' + 1];
        
        // 마지막 문자의 위치를 헤아린다.
        for (int i = 0; i < S.length(); i++) {
            last[S.charAt(i) - 'a'] = i;
        }
        
        int lastIndex = 0;
        int newCurrentIndex = 0;
        final List<Integer> ret = new ArrayList<>();
        
        for(int i = 0; i < S.length(); i++) {
            lastIndex = Math.max(lastIndex, last[S.charAt(i) - 'a']);
            
            if(i == lastIndex) {
                // 파티션안에 있는 문자의 개수 입력
                ret.add(lastIndex - newCurrentIndex + 1);
                
                // 새롭게 시작 위치를 다듬는다.
                newCurrentIndex = i + 1;
            }
        }
        
        return ret;
    }
}
