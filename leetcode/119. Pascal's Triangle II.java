class Solution {
    public List<Integer> getRow(int rowIndex) {
        if(rowIndex == 0) {
            return Arrays.asList(1);
        }
        
        if(rowIndex == 1) {
            return Arrays.asList(1, 1);
        }
        
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(1);
        return process(rowIndex, 2, list);
    }
    
    private List<Integer> process(final int targetIndex, final int currentIndex, List<Integer> ret) {
        if(currentIndex == targetIndex) {
            return ret;
        }
        
        ret.add(1);
        int len = ret.size() - 1;
        
        List<Integer> newRet = new ArrayList<>();
        newRet.add(1);
        for(int i = 1; i < len; i++) {
            int sum = ret.get(i - 1) + ret.get(i);
            newRet.add(sum);
        }
        newRet.add(1);

        return process(targetIndex, currentIndex + 1, newRet);
    }
}