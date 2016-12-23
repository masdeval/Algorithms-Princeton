
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
        
    private edu.princeton.cs.algs4.Stack<Board> solution = new edu.princeton.cs.algs4.Stack<Board>();
    private int moves = 0;
        
    public Solver(Board initial){           // find a solution to the initial board (using the A* algorithm)
    
        if (initial == null)
            throw new java.lang.NullPointerException();
        
        MinPQ<Board> minPq = new MinPQ<Board>(new Comparator<Board>(){
            
            public int compare(Board a, Board b)
            {
                if (a.manhattan()> b.manhattan())
                    return 1;
                else if(a.manhattan()< b.manhattan())
                    return -1;
                    
                return 0;
            }
            
        });
        
        minPq.insert(initial);        
        
        while(!minPq.isEmpty()){
            
            Board b = minPq.delMin();
            if(b.isGoal()){
                solution.push(b);
                moves = b.getMoves();
                while(b.getPrevious() != null)
                {
                    solution.push(b.getPrevious());
                    
                    b = b.getPrevious();
                }
                return;
            }
            
            for(Board board : b.neighbors()){
                
                // avoid insert duplicate board
                if(!board.equals(b.getPrevious())){
                    minPq.insert(board);                    
                }
            }
            
        }
        
    
    }
    public boolean isSolvable(){            // is the initial board solvable?
        
        
        return true;
        
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        
        return this.moves;
        
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        
        return solution;        
        
    }
    public static void main(String[] args) {

    // create initial board from file
    //In in = new In(args[0]);
    In in = new In("/home/christian/ProjetosNetBeans/Algorithms Princeton/src/8puzzle/puzzle36.txt");
    int n = in.readInt();
    byte[][] blocks = new byte[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readByte();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
}