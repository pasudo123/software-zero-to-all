class Solution {
    public String defangIPaddr(String address) {
        String ret = "";
            
        for(int i = 0; i < address.length(); i++) {
            ret += (address.charAt(i) == '.') ? "[.]" : address.charAt(i);
        }
        
        return ret;
    }
}
