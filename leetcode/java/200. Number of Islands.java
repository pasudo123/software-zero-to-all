class Solution {
    public int numIslands(char[][] grid) {
        
        if(grid.length == 0){
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int nums = 0;
        
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(grid[row][col] == '0') {
                    continue;
                }
                
                nums += bfs(grid, row, col);
            }
        }
        
        return nums;
    }
    
    private static int[] move1 = {0, 0, 1, -1};
    private static int[] move2 = {1, -1, 0, 0};
    
    public int bfs(char[][] grid, final int startRow, final int startCol) {
        
        final Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        grid[startRow][startCol] = '0';
        
        while(!queue.isEmpty()) {
            final int[] point = queue.poll();
            
            for(int i = 0; i < 4; i++) {
                final int moveRow = point[0] + move1[i];
                final int moveCol = point[1] + move2[i];
                
                if(moveRow < 0 || moveCol < 0 
                   || moveRow >= grid.length || moveCol >= grid[0].length) {
                    continue;
                }
                
                if(grid[moveRow][moveCol] == '0') {
                    continue;
                }
                
                queue.add(new int[]{moveRow, moveCol});
                grid[moveRow][moveCol] = '0';
            }
        }
        
        return 1;
    }
}
