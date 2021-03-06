class Solution {
    
    private static final String G = "G";
    private static final String O = "\\(\\)";
    private static final String AL = "\\(al\\)";

    public String interpret(String command) {
        // return mySolution1(command);
        return mySolution2(command);
    }
    
    private String mySolution1(final String command) {
        return command.replaceAll(O, "o")
            .replaceAll(AL, "al");
    }
    
    private String mySolution2(final String command) {
        final char[] array = command.toCharArray();
        String ret = "";
        
        for(int i = 0; i < array.length; i++) {
            if(array[i] == 'G') {
                ret += "G";
                continue;
            }
            
            if(array[i] == '(' && array[i+1] == ')') {
                ret += "o";
                i++;
                continue;
            }
            
            ret += "al";
            i += 3;
        }
        
        return ret;
    }
}
