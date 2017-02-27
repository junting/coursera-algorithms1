package assigment5;
import edu.princeton.cs.algs4.*;

public class KdTree {

    private Node root;
    private int size = 0;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        public Node(Point2D p, Node parentNode, boolean xflag){
            this.p = p;
            lb = null;
            rt = null;
            if (parentNode != null)
            {
                if (!xflag){
                    if (p.x() < parentNode.p.x())
                        this.rect = new RectHV( parentNode.rect.xmin(),parentNode.rect.ymin() ,parentNode.p.x(), parentNode.rect.ymax());
                    else
                        this.rect = new RectHV( parentNode.p.x(),parentNode.rect.ymin() ,parentNode.rect.xmax(), parentNode.rect.ymax());
                }
                else
                {
                    if (p.y() < parentNode.p.y())
                    this.rect = new RectHV( parentNode.rect.xmin(),parentNode.rect.ymin() ,parentNode.rect.xmax(), parentNode.p.y());
                    else
                    this.rect = new RectHV( parentNode.rect.xmin(),parentNode.p.y() ,parentNode.rect.xmax(), parentNode.rect.ymax());

                }
            }
            else
            {
                this.rect = new RectHV(0.0, 0.0, 1.0,1.0);
            }
        }
    }

    public KdTree()
    {
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return root == null;
    }

    // number of points in the set
    public int size() {
        //return(size(root));
        return size;
    }

    private int size(Node node) {
        if (node == null) return(0);
        else {
            return(size(node.lb) + 1 + size(node.rt));
        }
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p==null)
            throw new NullPointerException();
        root = insert (root, p, null, true);
    }

    private Node insert(Node x, Point2D p, Node parentNode, boolean xflag)
    {

        if (x == null)
        {
            size ++;
            return new Node(p, parentNode, xflag);
        }

        if (x.p.equals(p))
            return x;

        if (xflag){
            if (p.x() < x.p.x()) { x.lb = insert(x.lb, p, x, false); }
            else  { x.rt = insert(x.rt, p, x, false); }
            // else x.p = p;
            return x;
        }
        else
        {
            if (p.y() < x.p.y()) { x.lb = insert(x.lb, p, x,true); }
            else  { x.rt = insert(x.rt, p, x,true); }
            // else x.p = p;
            return x;
        }
    }


    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p==null)
            throw new NullPointerException();
        return contains(root, p, true);

    }

    private boolean contains(Node x, Point2D p, boolean xflag)
    {
        if (x == null) return false;
        if (x.p.equals(p)) return true;

        if (xflag){
            if (p.x() < x.p.x()) { return contains(x.lb, p, false); }
            else return contains(x.rt, p,false);
        }
        else
        {
            if (p.y() < x.p.y()) { return contains(x.lb, p, true); }
            else return contains(x.rt, p,true);
        }

    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node current, boolean isVertical) {
        if(current == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        current.p.draw();
        if(isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(current.p.x(), current.rect.ymin(), current.p.x(), current.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(current.rect.xmin(), current.p.y(), current.rect.xmax(), current.p.y());
        }
        draw(current.rt, !isVertical);
        draw(current.lb, !isVertical);
    }


    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> pointsInRange = new Stack<Point2D>();
        range (root, rect, pointsInRange);
        return pointsInRange;
    }

    private void range (Node x, RectHV rect, Stack<Point2D> pointsInRange)
    {
        if (x != null) {
            if (rect.contains(x.p))
                pointsInRange.push(x.p);
            if (x.lb != null && rect.intersects(x.lb.rect)) {
                range(x.lb, rect, pointsInRange);
            }
            if (x.rt != null && rect.intersects(x.rt.rect)) {
                range(x.rt, rect, pointsInRange);
            }
        }
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        return nearest(root, p, root.p);
    }

    private Point2D nearest (Node x, Point2D p, Point2D nearestPoint)
    {
        double minDist = p.distanceSquaredTo(nearestPoint);

        if (p.distanceSquaredTo(x.p) < minDist)
        {
            minDist = p.distanceSquaredTo(x.p);
            nearestPoint = x.p;
        }
        if (x.lb != null && x.lb.rect.distanceSquaredTo(p) < minDist)
            nearestPoint = nearest(x.lb, p, nearestPoint);
        if (x.rt != null && x.rt.rect.distanceSquaredTo(p) < minDist)
            nearestPoint = nearest(x.rt, p, nearestPoint);

        return nearestPoint;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args)
    {
//        KdTree kdtree = new KdTree();
//        double x =0.2;
//        double y = 0.3;
//        Point2D p = new Point2D(x, y);
//        // kdtree.insert(p);
//
//        double x1 =0.4;
//        double y1 = 0.5;
//        Point2D p1 = new Point2D(x1, y1);
//        kdtree.insert(p1);
//
//        StdOut.println(kdtree.contains(p));
//        StdOut.println(kdtree.contains(p1));

        String filename = args[0];
        In in = new In(filename);

        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
            StdDraw.clear();
            kdtree.draw();
            StdDraw.show();

            StdOut.print(kdtree.size());
    }
}
