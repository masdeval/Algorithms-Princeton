
import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christian
 */
public class Subset {
    
    public static void main(String[] args)                      
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        //String[] input = {"A","B","C","D","E"};
        
        while (!StdIn.isEmpty()) {
            // for(String s : input)
                // queue.enqueue(s);
                queue.enqueue(StdIn.readString());
            
        }        
        
        Iterator<String> it = queue.iterator();
        for (int i = 0; i < k; i++)
        {
            System.out.println(it.next());
        }
        
    }
    
    
}
