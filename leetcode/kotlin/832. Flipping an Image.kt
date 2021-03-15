class Solution {
        
    fun flipAndInvertImage(image: Array<IntArray>): Array<IntArray> {
        val rows = image.size;
        val cols = image[0].size;
        
        // 2차원 배열
        val ret = Array(rows) { IntArray(cols) {0} }
    
        for(i in 0 until rows) {
            
            for(j in 0 until cols / 2) {
                ret[i][j] = image[i][cols - 1 - j]
                ret[i][j] = if(ret[i][j] == 1) 0 else 1
            }
            
            val start = if(cols % 2 == 0) (cols / 2) else (cols / 2 + 1)
            for(j in start until cols) {
                ret[i][j] = image[i][cols - 1 - j]
                ret[i][j] = if(ret[i][j] == 1) 0 else 1
            }
            
            if(cols % 2 != 0) {
                ret[i][cols / 2] = image[i][cols / 2]
                ret[i][cols / 2] = if(ret[i][cols / 2] == 1) 0 else 1
            }
        }
    
        return ret;
    }
}
