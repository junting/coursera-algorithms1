package assigment4;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int[][] board;
    private int n;
    private int blank;
    public Board(int[][] blocks)
    {
        n = blocks.length;
        board = new int[n][n];
        for (int i=0; i < n; i++)
            for (int j=0; j < n; j++) {
                board[i][j] = blocks[i][j];
                if (board[i][j] == 0)
                    blank = i * n + j;
            }
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of blocks out of place
    public int hamming()
    {
        int distance = 0;
        for (int i=0; i < n; i++)
            for (int j=0; j < n; j++)
                if (board[i][j] != i*n+j+1)
                    distance ++;

        return distance - 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int distance = 0;
        for (int i=0; i < n; i++)
            for (int j=0; j < n; j++)
                if (board[i][j] != 0)
                    distance += Math.abs((board[i][j]-1) / n - i) + Math.abs((board[i][j]-1) % n - j);
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    private void swap(int[][] block, int i, int j) {
        int temp;
        temp = block[i / n][i % n];
        block[i / n][i % n] = block[j / n][j % n];
        block[j / n][j % n] = temp;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {

        int[][] twinBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinBoard[i][j] = board[i][j];
            }
        }

        if (blank / n == 1) {
            swap(twinBoard, 0, 1);
        } else {
            swap(twinBoard, n, n+1);
        }
        return new Board(twinBoard);
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (y == this)
            return true;

        if (y == null)
            return false;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        if (that.dimension() != this.dimension())
            return false;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            {
                if (that.board[i][j] != this.board[i][j])
                    return false;
            }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()  {
        Stack<Board> neighbor = new Stack<Board>();


        int x = blank / n;
        int y = blank % n;

        if (x-1 >= 0)
        {
            int[][] aux1 = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    aux1[i][j] = board[i][j];
                }
            }
            swap(aux1, blank-n, blank);
            neighbor.push(new Board(aux1));
            //swap(aux, blank-n, blank);
        }
        if (x+1 < n)
        {
            int[][] aux2 = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    aux2[i][j] = board[i][j];
                }
            }
            swap(aux2, blank+n, blank);
            neighbor.push(new Board(aux2));
            //swap(aux, blank+n, blank);
        }
        if (y-1 >= 0)
        {
            int[][] aux3 = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    aux3[i][j] = board[i][j];
                }
            }
            swap(aux3, blank-1, blank);
            neighbor.push(new Board(aux3));
            //swap(aux, blank-1, blank);
        }
        if (y+1 < n)
        {
            int[][] aux4 = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    aux4[i][j] = board[i][j];
                }
            }

            swap(aux4, blank+1, blank);
            neighbor.push(new Board(aux4));
            //swap(aux, blank+1, blank);
        }

        return neighbor;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.isGoal());
        //StdOut.println(initial.twin());

        for (Board board: initial.neighbors())
            StdOut.println(board);
        // StdOut.println(initial);
    }
}
