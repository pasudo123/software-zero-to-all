class Solution {
    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        int findIndex = 0;
        
        switch(ruleKey) {
            case "type":
                findIndex = 0;
                break;
            case "color":
                findIndex = 1;
                break;
            default:
                findIndex = 2;
        }

        int count = 0;

        for(List<String> element : items) {
            if(element.get(findIndex).equals(ruleValue)) {
                count++;
            }
        }

        return count;
    }
}
