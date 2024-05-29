
//import edu.princeton.cs.algs4.StdIn;
import java.io.BufferedInputStream;
import java.util.Iterator;
import java.util.Scanner;

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
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        //String[] input = {"A","B","C","D","E"};
        
        //while (!StdIn.isEmpty) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        while (scanner.hasNext()) {
            // for(String s : input)
                // queue.enqueue(s);
                queue.enqueue(scanner.next());
            
        }        
        
        Iterator<String> it = queue.iterator();
        for (int i = 0; i < k; i++)
        {
            System.out.println(it.next());
        }
        
    }
    
    
}
