class Solution {
    
    private static final Map<String, Object> WATCH_STORE = new HashMap<>();
    
    static {
        for(int h = 0; h <= 23; h++){
            for(int m = 0; m <= 59; m++) {
                WATCH_STORE.put(String.format("%02d:%02d", h, m), new Object());
            }
        }
    }
    
    private static String ret = "";
    private static final boolean[] visited = new boolean[4];
    private final List<String> times = new ArrayList<>();
    public String largestTimeFromDigits(int[] arr) {
        Arrays.sort(arr);
        for(int i = 3; i >= 0; i--) {
            visited[i] = true;
            isFind(arr, i);
            ret = "";
            visited[i] = false;
        }

        return (times.size() >= 1) ? times.get(0) : "";
    }

    private void isFind(int[] arr, int i) {
        ret += arr[i];
        if(ret.length() == 4) {
            String time = ret.substring(0, 2) + ":" + ret.substring(2, 4);
            if(WATCH_STORE.containsKey(time)) {
                times.add(time);
            }
            return;
        }

        for(int subI = 3; subI >= 0; subI--) {
            if(visited[subI]) {
                continue;
            }

            visited[subI] = true;
            isFind(arr, subI);
            ret = ret.substring(0, ret.length() - 1);
            visited[subI] = false;
        }
    }
}
