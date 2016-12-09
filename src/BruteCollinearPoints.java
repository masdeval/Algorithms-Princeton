
import java.util.ArrayList;


public class BruteCollinearPoints {
   
   ArrayList<LineSegment> lines;        
    
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
      this.lines = new ArrayList<LineSegment>();      
         
      for (int i = 0; i <= points.length - 4; i++){
          
          for (int j = i+1; j < points.length; j++)
          {
              for (int m = )
              
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