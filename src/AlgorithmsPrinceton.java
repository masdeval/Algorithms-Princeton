/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author christian
 */
public class AlgorithmsPrinceton {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("/home/christian/coursera/Algorithms_Part_I_Princeton/Programming Assigment 1/percolation/input2.txt")), 1);
            Percolation percolation;

            String line = br.readLine();
            percolation = new Percolation(Integer.parseInt(line));
            line = br.readLine();
            while (line != null) {                
                
                line = line.trim();
                String[] coord = line.split("\\s+");
                percolation.open(Integer.parseInt(coord[0]),Integer.parseInt(coord[1]));                
                line = br.readLine();               
                
            }
            
            System.out.println("The system percolates? Answer: " + percolation.percolates());

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e1) {
            System.out.println(e1);
        }

    }

}
