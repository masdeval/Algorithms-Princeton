/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christian
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private byte[][] grid;
    private int size;
    private WeightedQuickUnionUF quf;
    private int topVirtual;
    private int bottomVirtual;
    private final static int BOTTOM_VIRTUAL = -1;
    private final static int TOP_VIRTUAL = -2;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        size = n;
        quf = new WeightedQuickUnionUF((n * n) + 2); //  plus 2 for virtual sites
        grid = new byte[n][n];
        topVirtual = (size * size);
        bottomVirtual = (size * size) + 1;

    }

    private void connectVirtualSites() {
        //connect top
        for (int i = 0; i < size; i++) {

            if(grid[0][i] == 1)
                quf.union(topVirtual, i);

        }

        //connect botton
        for (int i = 0; i < size; i++) {

            if(grid[size - 1][i] == 1)
                quf.union(bottomVirtual, size * (size - 1) + i);

        }

    }

    public void open(int row, int col) // open site (row, col) if it is not open already
    {

        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IndexOutOfBoundsException();
        }

        int trueRow = row - 1;
        int trueCol = col - 1;

        if (grid[trueRow][trueCol] == 0) // is closed
        {
            grid[trueRow][trueCol] = 1; // open it

            // try connect left
            if (!(trueCol - 1 < 0) && (grid[trueRow][trueCol - 1] == 1)) {
                quf.union(size * trueRow + trueCol, size * trueRow + (trueCol - 1));
            }

            // try connect up
            if (!(trueRow - 1 < 0) && (grid[trueRow - 1][trueCol] == 1)) {
                quf.union(size * trueRow + trueCol, size * (trueRow - 1) + trueCol);
            }

            // try connect right     
            if (!(trueCol + 1 >= size) && (grid[trueRow][trueCol + 1] == 1)) {
                quf.union(size * trueRow + trueCol, size * (trueRow) + trueCol + 1);
            }

            // try connect botton  
            if (!(trueRow + 1 >= size) && (grid[trueRow + 1][trueCol] == 1)) {
                quf.union(size * trueRow + trueCol, size * (trueRow + 1) + trueCol);
            }

        }

    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IndexOutOfBoundsException();
        }

        return grid[row - 1][col - 1] == 1;

    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        if ((row < 1 || row > size || col < 1 || col > size) && !(row == BOTTOM_VIRTUAL) && !(col == TOP_VIRTUAL)) {
            throw new IndexOutOfBoundsException();
        }

        if ((row == BOTTOM_VIRTUAL) && (col == TOP_VIRTUAL)) 
            return quf.connected(bottomVirtual,topVirtual);        

        int trueRow = row - 1;
        int trueCol = col - 1;

        if (isOpen(row, col))
            return quf.connected((size * trueRow + trueCol), topVirtual);
                    
        
        return false;
    }

    public boolean percolates() // does the system percolate?
    {
        connectVirtualSites();
        return (isFull(BOTTOM_VIRTUAL, TOP_VIRTUAL));

    }

}
