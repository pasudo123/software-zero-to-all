class Solution {
    public int numberOfMatches(int n) {
        int teams = n;
        
        if(teams == 1) {
            return 0;
        }
        
        int total = 0;
        while(teams != 1) {
            total += (teams % 2 == 0) 
                ? (teams / 2)
                : (teams - 1) / 2;
            
            teams = (teams % 2 == 0) 
                ? (teams / 2)
                : (teams - 1) / 2 + 1;
        }
        
        return total;
    }
}
