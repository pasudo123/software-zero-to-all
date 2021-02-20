class Solution {
    public int minPathSum(int[][] grid) {
        
        if(grid.length == 0) {
            return 0;
        }
        
        final int rows = grid.length;
        final int cols = grid[0].length;
        
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(row == 0) {
                    grid[0][col] += (col - 1 < 0) ? 0 : grid[0][col - 1];
                    continue;
                }
                
                if(col == 0) {
                    grid[row][0] += (row -1 < 0) ? 0 : grid[row - 1][0];
                    continue;
                }
                
                grid[row][col] += Math.min(grid[row-1][col], grid[row][col-1]);
            }
        }
        
        return grid[rows - 1][cols - 1];
    }
}
