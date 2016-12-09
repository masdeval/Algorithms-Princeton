
import java.util.ArrayList;


public class BruteCollinearPoints {
   
   ArrayList<LineSegment> lines;        
    
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {       
      if (points == null)
           throw new NullPointerException();
       
      this.lines = new ArrayList<LineSegment>();      
         
      for (int i = 0; i <= points.length - 4; i++){
          
          for (int j = i+1; j < points.length; j++)
          {
              for (int m = j+1; m < points.length; m++)
              {
                  for (int k = m+1; m < points.length; k++)
                  {
                      if (points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[m]) && points[i].slopeTo(points[m]) == points[i].slopeTo(points[k])){
                          lines.add(new LineSegment(points[i], points[k]));
                      }
                  }
              }
              
          }
          
      } 
       
   }
           
   public int numberOfSegments()        // the number of line segments
   {
       return lines.size();
   }
           
   public LineSegment[] segments()                // the line segments
   {
       return lines.toArray(new LineSegment[1]);
   }
           
           
}