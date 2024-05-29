
import java.util.ArrayDeque;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christian
 */
public class Solution {
    
    
    public static void main (String[] args){
        
        Solution s = new Solution();
        
        int[] dd = {8, 8, 5, 7, 9, 8, 7, 4, 8};
        
        System.out.println(s.solution(dd));
        
    }
    
        public int solution(int[] H) {
            // write your code in Java SE 8

            int blocks = 1;
            for (int i = 1; i < H.length; i++){

                for (int j = i-1; j > -1; j--){
                    if (H[j] < H[i]){
                        blocks++;
                        break;
                    }
                    else if (H[j] > H[i]){
                        int k;
                        for (k = j-1; k > -1; k--){

                            if (H[i] > H[k]){blocks++;
                                break;
                            }
                            if (H[i] == H[k])
                            {
                                
                                break;
                            }

                        }
                        if (k == -1)
                            blocks++;
                        
                        break;

                    }
                    else if (H[j] == H[i]){
                        break;
                    }
                    else{

                        continue;
                    }
                }

            }
            return blocks;
        }

    
    public int solutionX(String S) {     
        // write your code in Java SE 8
        
       if (S.isEmpty())
           return 1;
        
       ArrayDeque<Character> stack = new ArrayDeque<Character>();
       Character aux;
       for (int i = 0; i < S.length(); i++)
       {
           
           aux = S.charAt(i);
           if (aux == '(' || aux == '{' || aux == '['){
               stack.push(aux);continue;
           }
           
           if (aux == ')' || aux == '}' || aux == ']'){
               if (!stack.isEmpty()){
                   Character poped = stack.pop();
                   if (poped == '(' && aux == ')')
                       continue;
                   else if (poped == '{' && aux == '}')
                       continue;
                   else if (poped == '[' && aux == ']')
                       continue;
                   else
                       return 0;
               }
               else
                   return 0;
           }
           
       }
        
       if (!stack.isEmpty())
           return 0;
       
       return 1;
    }
    
}
