/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christian
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] fractionOfOpenSites;
    private int trials;

    public PercolationStats(int n, int trials) // perform trials independent experiments on an n-by-n grid
    {

        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.fractionOfOpenSites = new double[trials];
        int size = n * n;
        this.trials = trials;

        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);
            double count = 0; 
            for (int j = 0; j < size; j++) {

                int p = StdRandom.uniform(1, n + 1);
                int q = StdRandom.uniform(1, n + 1);

                if (percolation.isOpen(p, q)) {
                    continue;
                } else {
                    count++;
                    percolation.open(p, q);

                    if (percolation.percolates()) {
                        fractionOfOpenSites[i] = count / size;
                        break;
                    }

                }
            }

        }

    }

    public double mean() // sample mean of percolation threshold
    {

        return StdStats.mean(fractionOfOpenSites);

        /* 
       double count = 0.0;
       
       for(int i = 0; i < trials; i++){
           count += fractionOfOpenSites[i];           
       }
       
       return count/trials;
         */
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(fractionOfOpenSites);

        /* 
        double count = 0.0;
       
       for(int i = 0; i < trials; i++){
           
           count += Math.pow(fractionOfOpenSites[i]-mean(), 2);           
       }
       
       return count/(trials)-1; */
    }

    public double confidenceLo() // low  endpoint of 95% confidence interval
    {        
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() // high endpoint of 95% confidence interval
    {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean:              " + stats.mean());
        System.out.println("stddev:            " + stats.stddev());
        // 95% confidence interval = 0.5912745987737567, 0.5947124012262428
        System.out.println("95% confidence interval = " + stats.confidenceLo() + " , " + stats.confidenceHi());

    }
}
