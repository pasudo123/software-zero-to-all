class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 우선 정렬 수행.
        final Map<String, List<String>> store = new HashMap<>();
        
        for(String name : strs) {
            char[] array = name.toCharArray();    
            Arrays.sort(array);
            List<String> groups = store.getOrDefault(new String(array), new ArrayList<>());
            groups.add(name);
            store.put(new String(array), groups);
        }
        
        final List<List<String>> result = new ArrayList<>();
        
        for(String key : store.keySet()) {
            result.add(store.get(key));
        }
        
        return result;
    }
}
