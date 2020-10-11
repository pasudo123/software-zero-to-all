class Solution {
    // in-place operation 문제 중에 훌륭한 문제
    public void duplicateZerosBasic(int[] arr) {
        
        int start = 0;
        int end = arr.length;
        
        for(int i = 0; i < arr.length; i++) {
            
            if(arr[i] != 0) {
                continue;
            }
            
            start = i + 1;
            end = arr.length;
            if(start >= end) {
                break;
            }
            
            int val = arr[start];
            int temp = 0;
            
            for(int j = start; j < end; j++) {
                if(j + 1 >= end) {
                    break;
                }
                
                temp = arr[j + 1];
                arr[j + 1] = val;
                val = temp;
            }
            
            arr[start] = 0;
            i++;
        }
    }
    
    // 최적화
    public void duplicateZeros(int[] arr) {
        
        int zeroCount = 0;
        int last = arr.length - 1;
        
        // 몇번째 인덱스까지 0을 확인할 것인가.
        for(int i = 0; i < arr.length - zeroCount; i++) {
            if(arr[i] != 0) {
                continue;
            }
            
            if(i == last - zeroCount) {
                arr[last] = 0;
                last--;
                break;
            }
            zeroCount++;
        }
        
        last = last - zeroCount;
        
        for(int i = last; i >= 0; i--) {
            if(arr[i] == 0) {
                arr[i + zeroCount] = 0;
                zeroCount--;  
                arr[i + zeroCount] = 0;
            } else {
                arr[i + zeroCount] = arr[i];
            }
            
            // System.out.println(Arrays.toString(arr));
        }
    }
}