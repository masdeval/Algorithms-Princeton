
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
        
    private edu.princeton.cs.algs4.Stack<Board> solution = new edu.princeton.cs.algs4.Stack<Board>();
            
    public Solver(Board initial){           // find a solution to the initial board (using the A* algorithm)
    
        if (initial == null)
            throw new java.lang.NullPointerException();
        
        MinPQ<SearchNode> minPq = new MinPQ<SearchNode>(new Comparator<SearchNode>(){
            
            public int compare(SearchNode a, SearchNode b)
            {
                if (a.node.manhattan()+a.moves > b.node.manhattan()+b.moves)
                    return 1;
                else if (a.node.manhattan()+b.moves < b.node.manhattan()+b.moves)
                    return -1;
                    
                return 0;
            }
            
        });
        
        minPq.insert(new SearchNode(initial));        
        
        while(!minPq.isEmpty()){
            
            SearchNode b = minPq.delMin();
            if(b.node.isGoal()){
                
                solution.push(b.node);                
                while(b.previous != null)
                {
                    solution.push(b.previous.node);                    
                    b = b.previous;
                }
                return;
            }
            
            for(Board board : b.node.neighbors()){
                
                // avoid insert duplicate board
                if(b.previous == null || !board.equals(b.previous.node)){
                    SearchNode sn = new SearchNode(board);
                    sn.previous = b;
                    sn.moves = b.moves+1;
                    minPq.insert(sn); 
                    
                }
            }
            
        }
        
    
    }
    public boolean isSolvable(){            // is the initial board solvable?
        
        
        return true;
        
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        
        return this.solution.size()-1;
        
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        
        return solution;        
        
    }
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    // In in = new In("/home/christian/ProjetosNetBeans/Algorithms Princeton/src/8puzzle/puzzle36.txt");
    // In in = new In("/home/christian/NetBeansProjects/Algorithms-Princeton/src/8puzzle/puzzle04.txt");
    int n = in.readInt();
    int[][] blocks = new int[n][n];
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
    
    private class SearchNode{
                        
        private Board node;
        private SearchNode previous;
        private int moves;
        
        public SearchNode(Board b){
            
            node = b;
            previous = null;
            moves = 0;
        }
        
    }
}