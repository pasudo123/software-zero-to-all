class Solution {
    
    companion object {
        private const val O: String = "()"
        private const val AL: String = "(al)"
    }
    
    fun interpret(command: String): String {
        // return mySolution1(command)
        return mySolution2(command)
    }
    
    fun mySolution1(command: String): String {
        return command.replace(O, "o").replace(AL, "al")
    }
    
    fun mySolution2(command: String): String {
        
        var index = 0;
        var ret = ""
        
        while(index < command.count()) {
            if(command[index] == 'G') {
                ret += "G"
                index++
                continue;
            }
            
            if(command[index] == '(' && command[index + 1] == ')') {
                ret += "o"
                index += 2
                continue;
            }
            
            ret += "al"
            index += 4
        }
        
        return ret
    }
}
