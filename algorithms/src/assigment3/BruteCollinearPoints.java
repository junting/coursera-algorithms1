package assigment3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {

    private LineSegment[] lineSegment;
    private int numSegments;
    private Point min;
    private Point max;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
       if (points == null) { throw new NullPointerException();}
       int N = points.length;
       lineSegment = new LineSegment[N];
       for (int i=0; i< N; i++)
       {
           if (points[i] == null)
               throw new NullPointerException();
       }
       for (int p=0; p<N; p++)
       {
           for (int q=p+1; q<N; q++)
           {
               max = points[p];
               min = points[p];
               if (points[p].compareTo(points[q]) == 0)
               {
                   throw new IllegalArgumentException();
               }

               for (int r=q+1; r<N; r++)
               {
                    if(points[p].slopeOrder().compare(points[q], points[r]) == 0)
                    {

                        for (int s=r+1; s<N; s++)
                        {
                            if(points[p].slopeOrder().compare(points[r], points[s]) == 0)
                            {
                                if (points[q].compareTo(min) == -1)
                                    min = points[q];
                                else
                                    max = points[q];

                                if (points[r].compareTo(min) == -1)
                                    min = points[r];
                                else if (points[r].compareTo(max) == 1)
                                    max = points[r];

                                if (points[s].compareTo(min) == -1)
                                    min = points[s];
                                else if (points[s].compareTo(max) == 1)
                                    max = points[s];

                                lineSegment[numSegments++] = new LineSegment(min, max);
                            }

                        }
                    }
               }
           }
       }

    }
    // the number of line segments
    public int numberOfSegments()
    {
        return numSegments;
    }
    // the line segments
    public LineSegment[] segments()
    {
        LineSegment[] ls = new LineSegment[numSegments];
        for (int i=0; i<numSegments; i++)
        {
            ls[i] = lineSegment[i];
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
