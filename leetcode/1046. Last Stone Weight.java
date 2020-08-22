class Solution {
    // 1046. Last Stone Weight
    public int lastStoneWeight(int[] stones) {
        return pqProcess(stones);
    }
    
    
    public int pqProcess(int[] stones) {
        final PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));
        
        for(int stone : stones) {
            pq.add(stone);
        }
        
        while(pq.size() > 1) {
            int y = pq.poll();
            int x = pq.poll();
            
            if(x != y) {
                pq.add(y - x);
            }
        }
        
        return (pq.size() == 0) ? 0 : pq.poll();
    }
}
