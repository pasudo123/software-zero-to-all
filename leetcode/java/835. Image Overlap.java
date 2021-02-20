class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        // [0][0] 의 중심점을 지속적으로 이동시킨다.
        // 좌우상하 기준점을 달리해서 이동시킨다.
        // 아래의 코드를 줄여보기.
        
        final int n = img1.length;
        int max = Integer.MIN_VALUE;
        
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                max = Math.max(max, getMaxOverlap(r, c, img1, img2));
            }
        }
        
        return max;
    }
    
    public int getMaxOverlap(int moveR, int moveC, int[][] image1, int[][] image2) {
        
        final int n = image1.length;
        int size = 0;
        int ret = 0;
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                if(r + moveR >= n || c + moveC >= n) {
                    continue;
                }
                
                int value1 = image1[r][c];
                int value2 = image2[r + moveR][c + moveC];
                
                if(value1 == 1 && value1 == value2) {
                    size++;
                }
            }
        }
        
        ret = Math.max(ret, size);
        size = 0;
        
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                if(r + moveR >= n || c - moveC < 0) {
                    continue;
                }
                
                int value1 = image1[r][c];
                int value2 = image2[r + moveR][c - moveC];
                
                if(value1 == 1 && value1 == value2) {
                    size++;
                }
            }
        }
        
        ret = Math.max(ret, size);
        size = 0;
        
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                if(r - moveR < 0 || c + moveC >= n) {
                    continue;
                }
                
                int value1 = image1[r][c];
                int value2 = image2[r - moveR][c + moveC];
                
                if(value1 == 1 && value1 == value2) {
                    size++;
                }
            }
        }
        
        ret = Math.max(ret, size);
        size = 0;
        
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                if(r - moveR < 0 || c - moveC < 0) {
                    continue;
                }
                
                int value1 = image1[r][c];
                int value2 = image2[r - moveR][c - moveC];
                
                if(value1 == 1 && value1 == value2) {
                    size++;
                }
            }
        }
        
        ret = Math.max(ret, size);
        
        return ret;
    }
}