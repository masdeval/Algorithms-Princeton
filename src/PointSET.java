
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET() {                              // construct an empty set of points 

        points = new TreeSet<Point2D>();

    }

    public boolean isEmpty() {                      // is the set empty? 

        return points.isEmpty();
    }

    public int size() {                         // number of points in the set 

        return points.size();

    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)

        points.add(p);

    }

    public boolean contains(Point2D p) {            // does the set contain point p? 

        return points.contains(p);
    }

    public void draw() {                         // draw all points to standard draw 

        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 

        ArrayList<Point2D> points = new ArrayList<Point2D>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }

        return points;
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        
        if (points == null)
            return null;
        
        Point2D[] a = new Point2D[1];
        Arrays.sort(points.toArray(a), p.distanceToOrder());
        
        return points.first();

    }
    

    
    // public static void main(String[] args)                  // unit testing of the methods (optional) 
}

