class Solution {
    public boolean wordPattern(String pattern, String s) {
        Map<Character, String> store = new HashMap<>();
        Map<String, Character> reverseStore = new HashMap<>();
        
        final String[] words = s.split(" ");
        final int size = pattern.length();
        
        if(words.length != size) {
            return false;
        }
        
        for(int i = 0; i < size; i++) {
            final char c = pattern.charAt(i);
            
            if(store.containsKey(c)) {
                if(words[i].equals(store.get(c)) && reverseStore.get(words[i]) == c){
                    continue;
                }
                
                return false;
            } 
            
            if(reverseStore.containsKey(words[i])) {
                return false;
            }
            
            reverseStore.put(words[i], c);
            store.put(c, words[i]);
        }
        
        return true;
    }
}