
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;

    public KdTree() {                              // construct an empty set of points 

        root = null;

    }

    public boolean isEmpty() {                      // is the set empty? 

        return root == null;
    }

    public int size() {                         // number of points in the set 

        return this.size;

    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)

        Node aux = new Node();
        aux.p = p;

        if (root == null) {
            aux.vertical = true;
            root = aux;
        } else {
            insert(root, aux);
        }

        size++;
    }

    private void insert(Node father, Node chield) {
        if (father.comparator().compare(father, chield) > 0) {
            if (father.lb != null) {
                insert(father.lb, chield);
            } else {
                chield.vertical = !father.vertical;
                father.lb = chield;
            }
        } else if (father.comparator().compare(father, chield) < 0) {
            if (father.rt != null) {
                insert(father.rt, chield);
            } else {
                chield.vertical = !father.vertical;
                father.rt = chield;
            }

        } else if (father.rt != null) {
            insert(father.rt, chield);
        } else {
            chield.vertical = !father.vertical;
            father.rt = chield;
        }

    }

    public boolean contains(Point2D p) {            // does the set contain point p? 

        ArrayList<Point2D> list = new ArrayList<Point2D>();
        this.breadthFirstTraversal(root, list);
        
        return list.contains(p);
    }

    public void draw() {                         // draw all points to standard draw 

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.p.draw();
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.RED);
        Point2D top = new Point2D(root.p.x(), 1);
        Point2D botton = new Point2D(root.p.x(), 0);
        root.p.drawTo(top);
        root.p.drawTo(botton);

        this.breadthFirstTraversal(root);
    }

    
    private void breadthFirstTraversal(Node n, List<Point2D> list){
        
        if (n == null)
            return;
        
        list.add(n.p);
        breadthFirstTraversal(n.lb, list);
        breadthFirstTraversal(n.rt, list);
        
    }
    
    private void breadthFirstTraversal(Node n) {

        if (n == null) {
            return;
        }

        // left chield        
        if (n.lb != null && n.lb.vertical) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.lb.p.draw();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            Point2D top = new Point2D(n.lb.p.x(), n.p.y());
            Point2D botton = new Point2D(n.lb.p.x(), 0);
            n.lb.p.drawTo(top);
            n.lb.p.drawTo(botton);
        } else if (n.lb != null) { // horizontal
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.lb.p.draw();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D left = new Point2D(0, n.lb.p.y());
            Point2D right = new Point2D(n.p.x(), n.lb.p.y());
            n.lb.p.drawTo(left);
            n.lb.p.drawTo(right);
        }

        // right chield        
        if (n.rt != null && n.rt.vertical) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.rt.p.draw();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            Point2D top = new Point2D(n.rt.p.x(), n.p.y());
            Point2D botton = new Point2D(n.rt.p.x(), 0);
            n.rt.p.drawTo(top);
            n.rt.p.drawTo(botton);
        } else if (n.rt != null) { // horizontal
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.rt.p.draw();
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D left = new Point2D(0, n.rt.p.y());
            Point2D right = new Point2D(n.p.x(), n.rt.p.y());
            n.rt.p.drawTo(left);
            n.rt.p.drawTo(right);
        }

        breadthFirstTraversal(n.lb);
        breadthFirstTraversal(n.rt);

    }

   /* public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 

    }*/

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 

              
       return nearest(root, p, root.p); 
        
    }
    
    private Point2D nearest(Node n, Point2D query, Point2D nearest){

        // go to the left
       if (n.lb != null && n.rt != null && n.lb.p.distanceTo(query) < n.rt.p.distanceTo(query))
       {
           if (n.lb.p.distanceTo(query) < nearest.distanceTo(query))
           {
               nearest = nearest(n.lb,query,n.lb.p);
           }
           else{
               nearest = nearest(n.lb,query,nearest);
           }
           
       } //  go to the right
       else if (n.lb != null && n.rt != null && n.lb.p.distanceTo(query) > n.rt.p.distanceTo(query))
       {
           if (n.rt.p.distanceTo(query) < nearest.distanceTo(query))
           {
               nearest = nearest(n.rt,query,n.rt.p);
           }
           else{
               nearest = nearest(n.rt,query,nearest);
           }          
           
       }      
       else if (n.lb == null && n.rt != null) //  go to the right
       {
           if (n.rt.p.distanceTo(query) < nearest.distanceTo(query))
           {
               nearest = nearest(n.rt,query,n.rt.p);
           }
           else{
               nearest = nearest(n.rt,query,nearest);
           }                                
       }
       else if (n.lb != null && n.rt == null)  // go to the left
       {
           
           if (n.lb.p.distanceTo(query) < nearest.distanceTo(query))
           {
               nearest = nearest(n.lb,query,n.lb.p);
           }
           else{
               nearest = nearest(n.lb,query,nearest);
           }

       }
       
            
       return nearest; 
    }

    private static class Node {

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private boolean vertical;

        public Comparator<Node> comparator() {

            return new XYOrder();

        }

        // compare points according to their x-coordinate or y-coordinate
        private class XYOrder implements Comparator<Node> {

            public int compare(Node p, Node q) {

                if (p.vertical == true) {

                    return Point2D.X_ORDER.compare(p.p, q.p);

                } else {

                    return Point2D.Y_ORDER.compare(p.p, q.p);

                }

            }
        }

    }

    // public static void main(String[] args)                  // unit testing of the methods (optional) 
}
