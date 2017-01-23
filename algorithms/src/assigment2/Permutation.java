package assigment2;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
//        System.setIn(new FileInputStream("/Users/juntingpan/Projects/coursera-algorithms1/algorithms/queues/tinyTale.txt"));
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
