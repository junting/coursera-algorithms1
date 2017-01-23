package assigment2;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
//        String ss = StdIn.readLine();
//        StdOut.print(ss);
        // read strings from std input:
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
//        StdOut.print("Type a string: ");
//        String s = StdIn.readString();
//        StdOut.println("Your string was: " + s);
//        StdOut.println();
    }
}
