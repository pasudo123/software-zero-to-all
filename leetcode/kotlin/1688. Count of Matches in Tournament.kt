class Solution {
    fun numberOfMatches(n: Int): Int {
        
        if (n == 1) {
            return 0
        }
        
        var teams = n
        var totalMatches = 0
        
        while (teams != 1) {
            totalMatches += if (teams % 2 == 0) teams / 2 else (teams - 1) / 2
            teams = if (teams % 2 == 0) teams / 2 else ((teams - 1) / 2 + 1)
        }
        
        return totalMatches
    }
}
