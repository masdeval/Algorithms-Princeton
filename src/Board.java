
import java.util.ArrayList;

public class Board {

    private byte[][] board;
    private Board previous;
    private int moves;
    private int manhattan = -1;
    private int hamming = -1;

    public Board(byte[][] blocks) {           // construct a board from an n-by-n array of blocks

        /* for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                blocks[i][j] = blocks.length*i+j+1;
            }
        }
         */
        board = blocks;
        previous = null;
        moves = 0;
        hamming();
        manhattan();
    }
    // (where blocks[i][j] = block in row i, column j)

    public Board getPrevious() {
        return previous;
    }

    public int getMoves() {
        return moves;
    }

    public int dimension() {                // board dimension n

        return board.length;
    }

    public int hamming() {                 // number of blocks out of place

        if (hamming == -1) {
            int count = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {

                    if (board[i][j] == 0) {
                        continue;
                    } else if (i == board.length - 1 && j == board.length - 1 && board[i][j] != 0) {
                        count++;
                    } else if (board[i][j] != board.length * i + (j + 1)) {
                        count++;
                    }
                }

            }
            hamming = count;
        }

        return hamming + moves;
    }

    public int manhattan() {             // sum of Manhattan distances between blocks and goal

        if (this.manhattan == -1) {
            int count = 0;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {

                    if (board[i][j] == 0) {
                        continue;
                    }
                    int element = board[i][j];
                    int atual_position_x = i + 1;
                    int atual_position_y = j + 1;
                    int ideal_position_x;
                    int ideal_position_y;
                    // calculating ideal position for x (line)
                    if (element % board.length == 0) {
                        ideal_position_x = element / board.length;
                    } else {
                        ideal_position_x = element / board.length + 1;
                    }
                    // calculating ideal position for y (column)
                    ideal_position_y = element - (board.length * (ideal_position_x - 1));

                    count += Math.abs(atual_position_x - ideal_position_x) + Math.abs(atual_position_y - ideal_position_y);
                }

            }
            this.manhattan = count;
        }

        return manhattan + moves;
    }

    public boolean isGoal() {               // is this board the goal board?

        return this.hamming == 0;

    }
    //public Board twin()                    // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {       // does this board equal y?

        if (y == null) {
            return false;
        }

        if (!(y instanceof Board)) {
            return false;
        }

        Board aux = (Board) y;

        if (aux.dimension() != this.dimension()) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                if (board[i][j] != aux.board[i][j]) {
                    return false;
                }

            }

        }

        return true;

    }

    public Iterable<Board> neighbors() { // all neighboring boards

        ArrayList<Board> list = new ArrayList<Board>();

        int x = 0;
        int y = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        int new_x;
        int new_y;

        // first neighbor
        new_x = x - 1;
        new_y = y;
        if (new_x >= 0) {
            Board b = new Board(exchange(x, y, new_x, new_y));
            b.previous = this;
            b.moves = this.moves + 1;
            list.add(b);
        }
        // second neighbor
        new_x = x + 1;
        new_y = y;
        if (new_x < board.length) {
            Board b = new Board(exchange(x, y, new_x, new_y));
            b.previous = this;
            b.moves = this.moves + 1;
            list.add(b);
        }
        // third neighbor
        new_x = x;
        new_y = y - 1;
        if (new_y >= 0) {
            Board b = new Board(exchange(x, y, new_x, new_y));
            b.previous = this;
            b.moves = this.moves + 1;
            list.add(b);
        }
        // fourth neighbor
        new_x = x;
        new_y = y + 1;
        if (new_y < board.length) {
            Board b = new Board(exchange(x, y, new_x, new_y));
            b.previous = this;
            b.moves = this.moves + 1;
            list.add(b);
        }

        return list;

    }

    public String toString() { // string representation of this board (in the output format specified below)

        StringBuilder s = new StringBuilder();

        s.append(board.length + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                s.append(" " + board[i][j]);
            }
            s.append("\n");
        }

        return s.toString();
    }

    // public static void main(String[] args) // unit tests (not graded)
    private byte[][] exchange(int x, int y, int new_x, int new_y) {

        byte[][] newBoard = deepCopyIntMatrix(board);
        byte temp = board[new_x][new_y];
        newBoard[x][y] = temp;
        newBoard[new_x][new_y] = 0;
        return newBoard;

    }

    private byte[][] deepCopyIntMatrix(byte[][] input) {
        if (input == null) {
            return null;
        }
        byte[][] result = new byte[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
}
