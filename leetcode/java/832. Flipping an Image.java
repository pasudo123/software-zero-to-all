class Solution {
    public int[][] flipAndInvertImage(int[][] image) {
        final int rows = image.length;
        final int cols = image[0].length;
        
        final int[][] ret = new int[rows][cols];
        final boolean isEvenLen = rows % 2 == 0;
        
        for(int i = 0; i < rows; i++) {    
            
            for(int j = 0; j < cols / 2; j++) {
                ret[i][j] = image[i][cols - 1 - j];
                ret[i][j] = ret[i][j] == 1 ? 0 : 1;
            }
            
            for(int j = (isEvenLen) ? cols / 2 : cols / 2 + 1; j < cols; j++) {
                ret[i][j] = image[i][cols - 1 - j];
                ret[i][j] = ret[i][j] == 1 ? 0 : 1;
            }
            
            if(!isEvenLen) {
                ret[i][cols / 2] = image[i][cols / 2];
                ret[i][cols / 2] = ret[i][cols / 2] == 1 ? 0 : 1;
            }
        }
        
        return ret;
    }
}
