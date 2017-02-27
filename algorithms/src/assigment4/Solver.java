package assigment4;


import edu.princeton.cs.algs4.*;

public class Solver {

    private MinPQ<SearchNode> steps;
    private MinPQ<SearchNode> stepsTwin;
    private int moves;
    private boolean solvable = true;
    private Stack<Board> boardStack = new Stack<Board>();


    private class SearchNode implements Comparable<SearchNode>
    {
        SearchNode prev = null;
        private Board currentBoard;
        private int priority = 0;
        private int moves = 0;

        public SearchNode(Board board, SearchNode prev)
        {
            currentBoard = board;
            this.prev = prev;
            if (prev != null){
                priority = board.manhattan() + prev.moves;
                this.moves = prev.moves + 1;
            }
        }

        @Override
        public int compareTo(SearchNode o) {
            if (o == null)
                throw new NullPointerException();
            if (this.priority > o.priority)
                return 1;
            else if (this.priority < o.priority)
                return -1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)  {

        Board last = initial;
        Board lastTwin = initial.twin();

        SearchNode currentNode = new SearchNode(last, null);
        SearchNode currentNodeTwin = new SearchNode(lastTwin, null);

        stepsTwin = new MinPQ<SearchNode>();
        steps = new MinPQ<SearchNode>();

        steps.insert(currentNode);
        stepsTwin.insert(currentNodeTwin);

        while (!steps.isEmpty() && !stepsTwin.isEmpty())
        {
            currentNode = steps.delMin();
            last = currentNode.currentBoard;

            currentNodeTwin = stepsTwin.delMin();
            lastTwin = currentNodeTwin.currentBoard;

            if (last.isGoal())
            {
                moves = currentNode.moves;
                while(currentNode != null)
                {
                    boardStack.push(currentNode.currentBoard);
                    currentNode = currentNode.prev;
                }
                break;
            }

            if (lastTwin.isGoal())
            {
                solvable = false;
                moves = -1;
                break;
            }
            for (Board b : last.neighbors())
                if ( currentNode.prev == null || !currentNode.prev.currentBoard.equals(b))
                    steps.insert(new SearchNode(b, currentNode));

            for (Board b : lastTwin.neighbors())
                if (currentNodeTwin.prev == null || !currentNodeTwin.prev.currentBoard.equals(b))
                    stepsTwin.insert(new SearchNode(b, currentNodeTwin));
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()   {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()   {
        if (!solvable)
            return null;
        return boardStack;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
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