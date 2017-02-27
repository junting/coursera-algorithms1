package assigment3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {


    private LineSegment[] seg;
    private int num;
    private int N;

    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new NullPointerException();

        N = points.length;
        seg = new LineSegment[N];
        Point[] aux  = new Point[N];
        boolean repeated;
        int num_adjacent = 0;
        int same = 0;
        Point current;
        Point max;

        for (int i = 0; i < N; i++) {
            if (points[i] == null)
                throw new NullPointerException();
            for (int j = i+1; j < N; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
            aux[i] = points[i];
        }

        for (int i = 0, j = 0; i < N; i++, j = 0) {
            current = points[i];
            Arrays.sort(aux, current.slopeOrder());

            // The indice goes until two position before the last one.
            while (j < N - 2) {

                //Make the computation faster, if the one after next does not have the same slop as the next then skip!
                if (current.slopeTo(aux[j]) != current.slopeTo(aux[j+2]))
                    j++;

                else {
                    /*
                    repeated controls that only current == min, the segment is accepted
                    same controls if the same points is scaned too
                    The loop continue while the j+num_adjacent is smaller than N and the slope matches.
                     */
                    repeated = false;
                    max = aux[j];
                    for (num_adjacent = 0; j+num_adjacent < N && current.slopeTo(aux[j]) == current.slopeTo(aux[j+num_adjacent]); num_adjacent++)
                    {
                        if (aux[j+num_adjacent].compareTo(max) > 0)
                            max = aux[j+num_adjacent];
                        if (current.compareTo(aux[j+num_adjacent]) > 0)
                            repeated = true;
                        if (current.compareTo(aux[j+num_adjacent]) == 0)
                            same = 1;
                    }
                    j += num_adjacent;
                    if (!repeated && num_adjacent-same >= 2) {
                        seg[num++] = new LineSegment(current, max);
                        if (seg[seg.length-1] != null) {
                            changeSize(seg.length, seg.length * 2);
                        }
                    }
                }
                same = 0;
            }
        }

    }

    private void changeSize(int oldSize, int newSize) {
        LineSegment[] newseg = new LineSegment[newSize];
        for (int i = 0; i < oldSize; i++) {
            newseg[i] = seg[i];
        }
        seg = newseg;
    }

    public int numberOfSegments()
    {
        return num;
    }

    public LineSegment[] segments()
    {
        LineSegment[] ls = new LineSegment[num];
        for (int i=0; i<num; i++)
        {
            ls[i] = seg[i];
        }
        return ls;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
