
import edu.princeton.cs.algs4.In;
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
        size = 0;
    }

    public boolean isEmpty() {                      // is the set empty? 

        return root == null;
    }

    public int size() {                         // number of points in the set 

        return this.size;

    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)

        

        if (root == null) {
            Node aux = new Node(p,null);
            aux.vertical = true;
            root = aux;
            size++;
            
        } else {
            Node aux = new Node(p,root);
            insert(root, p);
        }

    }

    private void insert(Node father, Point2D p) {

        if (father.p.equals(p)) {
            return;
        }

        Node chield = new Node(p,father);
        if (father.comparator().compare(father, chield) > 0) {
            if (father.lb != null) {
                insert(father.lb, p);
            } else {
                chield.vertical = !father.vertical;
                father.lb = chield;
                size++;
            }
        } else if (father.comparator().compare(father, chield) < 0) {
            if (father.rt != null) {
                insert(father.rt, p);
            } else {
                chield.vertical = !father.vertical;
                father.rt = chield;
                size++;
            }

        } else if (father.rt != null) {
            insert(father.rt, p);
        } else {
            chield.vertical = !father.vertical;
            father.rt = chield;
            size++;
        }

    }

    public boolean contains(Point2D p) {            // does the set contain point p? 

        Node aux = new Node(p);
        return (this.search(root, aux) != null);

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

    private Point2D search(Node father, Node n) {

        while (true) {

            if (father == null) {
                return null;
            }

            if (father.p.equals(n.p)) {
                return father.p;
            }

            if (father.comparator().compare(father, n) > 0) {
                father = father.lb;
            } else if (father.comparator().compare(father, n) < 0) {
                father = father.rt;
            } else {
                father = father.rt;
            }

        }

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

    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        
        if (root == null)
            return null; 
        
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        range(root, rect, list);

        return list;
    }

    private void range(Node n, RectHV query, List<Point2D> list) {

        if (query.contains(n.p)) {
            list.add(n.p);
        }

        if (n.getRect().intersects(query)) {
            // search both sides    
            if (n.lb != null) {
                range(n.lb, query, list);
            }
            if (n.rt != null) {
                range(n.rt, query, list);
            }
        } else // go to the side where the rect is        
        // go to the left
        {
            //if (n.lb != null && n.rt != null && query.distanceTo(n.lb.p) < query.distanceTo(n.rt.p)) {
            if (n.lb != null && n.rt != null && n.comparator().compare(n, new Node(new Point2D(query.xmax(),query.ymax()))) > 0  ) {
                range(n.lb, query, list);
            } //  go to the right
            else if (n.lb != null && n.rt != null && n.comparator().compare(n, new Node(new Point2D(query.xmax(),query.ymax()))) < 0 ) {
                range(n.rt, query, list);

            } else if (n.lb != null && n.rt != null && n.comparator().compare(n, new Node(new Point2D(query.xmax(),query.ymax()))) == 0 ) {
                range(n.rt, query, list);
                range(n.lb, query, list);

            } else if (n.lb == null && n.rt != null) //  go to the right
            {
                range(n.rt, query, list);

            } else if (n.lb != null && n.rt == null) // go to the left                
            {
                range(n.lb, query, list);
            }
        }
    }

    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 

        Node n = new Node(p);
        return nearest(root, n, root.p);

    }

    /*
    private Point2D nearest(Node n, Point2D query, Point2D nearest) {

        // go to the left
        if (n.lb != null && n.rt != null && n.lb.p.distanceSquaredTo(query) < n.rt.p.distanceSquaredTo(query)) {
            if (n.lb.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
                nearest = nearest(n.lb, query, n.lb.p);
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

        } //  go to the right
        else if (n.lb != null && n.rt != null && n.lb.p.distanceSquaredTo(query) > n.rt.p.distanceSquaredTo(query)) {
            if (n.rt.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }

        }
        else if (n.lb != null && n.rt != null && n.lb.p.distanceSquaredTo(query) == n.rt.p.distanceSquaredTo(query)) {
            if (n.rt.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } 
            
            if (n.lb.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
                nearest = nearest(n.lb, query, nearest);
            }

        }        
        else if (n.lb == null && n.rt != null) //  go to the right
        {
            if (n.rt.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }
        } else if (n.lb != null && n.rt == null) // go to the left
        {

            if (n.lb.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
                nearest = nearest(n.lb, query, n.lb.p);
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

        }

        return nearest;
    }*/
    private Point2D nearest(Node n, Node query, Point2D nearest) {

  /*      if (n.lb != null && n.rt != null) {

            if (n.vertical) { 
                
                // go to the left  first                     
                if (query.p.x()  < n.p.x()) // query point is in the left side of vertical line                
                {
                    //if(n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) 
                    {
                        if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.lb, query, n.lb.p);
                        } else {
                            nearest = nearest(n.lb, query, nearest);
                        }                    
                    }
                    
                    if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p))
                    { 
                        if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.rt, query, n.rt.p);
                        } 
                        else {
                            nearest = nearest(n.rt, query, nearest);
                        }
                    }
                    
                } 
                //go to right first
                if (query.p.x() >= n.p.x()) // query point is in the right side of vertical line
                {
                    
                    //if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p))
                    { 
                        if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.rt, query, n.rt.p);
                        } 
                        else {
                            nearest = nearest(n.rt, query, nearest);
                        }
                    }
                    
                    if(n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) 
                    {
                        if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.lb, query, n.lb.p);
                        } else {
                            nearest = nearest(n.lb, query, nearest);
                        }                    
                    }
                    
               }
              // horizontal  
            } else  { // horizontal

                if (query.p.y()  < n.p.y()){ // query point is bellow horizontal line 
                
                   // if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p))
                    {
                        
                        if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.lb, query, n.lb.p);
                        } else {
                         nearest = nearest(n.lb, query, nearest);
                        }
                    }
                    
                    if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p))
                    {                         
                        if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.rt, query, n.rt.p);
                        } else {
                            nearest = nearest(n.rt, query, nearest);
                        }
                    }
                    
                    
                }
                
                if (query.p.y()  >= n.p.y()){ // query point is above horizontal line 
                    
                    //if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p))
                    {                         
                        if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.rt, query, n.rt.p);
                        } else {
                            nearest = nearest(n.rt, query, nearest);
                        }
                    }
                    
                    if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p))
                    {
                        
                        if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                            nearest = nearest(n.lb, query, n.lb.p);
                        } else {
                         nearest = nearest(n.lb, query, nearest);
                        }
                    }
                    
                }
            }

        } else if (n.rt != null) {

            if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }

        } else if (n.lb != null) {

            if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                nearest = nearest(n.lb, query, n.lb.p);
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

        }*/
  
        /*
       // NOT working 
          // go to left first  
          if (n.lb != null && n.rt != null && n.comparator().compare(n, query) > 0) {           
            
            boolean changeNearest = false;
            if (n.lb.p.distanceSquaredTo(query.p) < nearest.getRect().distanceSquaredTo(query.p)) {
                nearest = nearest(n.lb, query, n.lb);
                changeNearest = true;
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

            if (!changeNearest) {
                if (n.rt.p.distanceTo(query.p) < nearest.getRect().distanceTo(query.p)) {
                    nearest = nearest(n.rt, query, n.rt);
                } else {
                    nearest = nearest(n.rt, query, nearest);
                }
            }

        } //  go to the right first
        else if (n.lb != null && n.rt != null && n.comparator().compare(n, query) < 0) {
            
            boolean changeNearest = false;
            if (n.rt.p.distanceSquaredTo(query.p) < nearest.getRect().distanceSquaredTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt);
                changeNearest = true;
            } else {
                nearest = nearest(n.rt, query, nearest);
            }

            if (!changeNearest) {

                if (n.lb.p.distanceTo(query.p) < nearest.getRect().distanceTo(query.p)) {
                    nearest = nearest(n.lb, query, n.lb);
                } else {
                    nearest = nearest(n.lb, query, nearest);
                }
            }

        } else if (n.lb != null && n.rt != null && n.comparator().compare(n, query) == 0) {

            if (n.rt.p.distanceSquaredTo(query.p) < nearest.getRect().distanceSquaredTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }
        } else if (n.lb == null && n.rt != null) //  go to the right
        {
            if (n.rt.p.distanceSquaredTo(query.p) < nearest.getRect().distanceSquaredTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }
        } else if (n.lb != null && n.rt == null) // go to the left
        {

            if (n.lb.p.distanceSquaredTo(query.p) < nearest.getRect().distanceSquaredTo(query.p)) {
                nearest = nearest(n.lb, query, n.lb);
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

        }
        */
  
      
        if (n.lb != null && n.rt != null && n.comparator().compare(n, query) > 0) {
            
            if (n.lb.p.distanceSquaredTo(query.p) < nearest.distanceSquaredTo(query.p)) {            
                nearest = nearest(n.lb, query, n.lb.p);
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

            if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                if (n.rt.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                    nearest = nearest(n.rt, query, n.rt.p);
                } else {
                    nearest = nearest(n.rt, query, nearest);
                }
            }

        } //  go to the right
        else if (n.lb != null && n.rt != null && n.comparator().compare(n, query) < 0) {
            if (n.rt.p.distanceSquaredTo(query.p) < nearest.distanceSquaredTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }

            if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {

                if (n.lb.p.distanceTo(query.p) < nearest.distanceTo(query.p)) {
                    nearest = nearest(n.lb, query, n.lb.p);
                } else {
                    nearest = nearest(n.lb, query, nearest);
                }
            }

        } else if (n.lb != null && n.rt != null && n.comparator().compare(n, query) == 0) {

            if (n.rt.p.distanceSquaredTo(query.p) < nearest.distanceSquaredTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }
        } else if (n.lb == null && n.rt != null) //  go to the right
        {
            if (n.rt.p.distanceSquaredTo(query.p) < nearest.distanceSquaredTo(query.p)) {
                nearest = nearest(n.rt, query, n.rt.p);
            } else {
                nearest = nearest(n.rt, query, nearest);
            }
        } else if (n.lb != null && n.rt == null) // go to the left
        {

            if (n.lb.p.distanceSquaredTo(query.p) < nearest.distanceSquaredTo(query.p)) {
                nearest = nearest(n.lb, query, n.lb.p);
            } else {
                nearest = nearest(n.lb, query, nearest);
            }

        }
        return nearest;
    }

    private static class Node {

        private Point2D p;      // the point
        private Node father;
        // private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private boolean vertical;

        public Node(Point2D p, Node father) {

            this.father = father;
            this.p = p;
        }
        
        public Node (Point2D p){
            
            this.p = p;
            this.father = null;
        }

        public Comparator<Node> comparator() {

            return new XYOrder();

        }

      /*  public RectHV getRect() {                      

            if (vertical) {

                return new RectHV(p.x(), 0, p.x(), 1);

            } else {
                return new RectHV(0, p.y(), 1, p.y());
            }

        }*/

        public RectHV getRect() { 
            
            //if (father == null) 
            {
                if (vertical) {
                    return new RectHV(p.x(), 0, p.x(), 1);

                } else {
                    return new RectHV(0, p.y(), 1, p.y());
                }
            }
/*
            if (vertical) {

                if (father.comparator().compare(father, this) > 0){ // bellow chield
                    
                    return new RectHV(this.p.x(), 0, this.p.x(), father.p.y());
                    
                }
                else // above chield
                {
                     return new RectHV(this.p.x(), father.p.y(), this.p.x(), 1);
                }

            } else { // horizontal
                
                if (father.comparator().compare(father, this) > 0){ // feft chield
                    
                    return new RectHV(0, this.p.y(), father.p.x(), this.p.y());
                    
                }
                else // right chield
                {
                     return new RectHV(father.p.x(), this.p.y(), 1, this.p.y());
                }
                
                
            }  */

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

    public static void main(String[] args) // unit testing of the methods (optional) 
    {
        In in = new In("/home/christian/ProjetosNetBeans/Algorithms Princeton/src/circle10.txt");

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //brute.insert(p);
        }

        boolean aux = kdtree.contains(new Point2D(0.510000, 0.000000));
        Point2D p = new Point2D(0.5, 0.5);
        Node n = new Node(p);
        n.vertical = true;
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.getRect().draw();
        StdDraw.show();
    } 
}
