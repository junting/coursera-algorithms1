package assigment5;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {

    private SET<Point2D> points;
    // construct an empty set of points
    public PointSET()
    {
        points  = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return points.isEmpty();
    }

    // number of points in the set
    public int size()
    {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p==null)
            throw new NullPointerException();
        if (!points.contains(p))
            points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p==null)
            throw new NullPointerException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw()
    {
        for (Point2D p: points){
           p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {

        Stack<Point2D> pointsInRange = new Stack<>();

        if (rect==null)
            throw new NullPointerException();

        if (isEmpty())
            return pointsInRange;

        for (Point2D p: points){
            if (rect.contains(p))
                pointsInRange.push(p);
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p==null)
            throw new NullPointerException();

        if (points.isEmpty())
            return null;

        double minDistance = points.min().distanceSquaredTo(p);
        Point2D nearestPoint = points.min();


        for (Point2D point:points)
        {
            if (point.distanceSquaredTo(p) < minDistance)
            {
                minDistance = point.distanceSquaredTo(p);
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)
    {

    }
}
