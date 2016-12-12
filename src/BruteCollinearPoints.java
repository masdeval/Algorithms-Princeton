
public class BruteCollinearPoints {

    LineSegment[] lines;
    int numberOfSegments = 0;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new NullPointerException();
        }

        if (points.length < 4) {
            return;
        }

        // not allowed repeted points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }
            }
        }
        this.lines = new LineSegment[points.length/2];
        Point[] alreadyChosenHead = new Point[points.length/2];
        Point[] alreadyChosenTail = new Point[points.length/2];
        for (int i = 0; i <= points.length - 4; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int m = j + 1; m < points.length; m++) {
                    int count = 2;
                    Point head = points[i];
                    Point tail = points[j];
                    if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) {
                        count++;
                        if (tail.distance(points[m]) > head.distance(tail)) {
                            head = points[m];
                        }
                        if (head.distance(points[m]) > head.distance(tail)) {
                            tail = points[m];
                        }
                        // search for the fourth point
                        for (int k = m + 1; k < points.length; k++) {

                            if (points[k].slopeTo(head) == points[k].slopeTo(tail)) {
                                count++;
                                if (tail.distance(points[k]) > head.distance(tail)) {
                                    head = points[k];
                                }
                                if (head.distance(points[k]) > head.distance(tail)) {
                                    tail = points[k];
                                }
                            }
                        }
                        if (count >= 4) {
                            
                            if (numberOfSegments == 0) {
                                alreadyChosenHead[numberOfSegments] = head;
                                alreadyChosenTail[numberOfSegments] = tail;
                                lines[numberOfSegments] = new LineSegment(head, tail);
                                numberOfSegments++;

                            } else {
                                boolean insert = true;
                                for (int z = 0; z < numberOfSegments; z++) {
                                    if (head.slopeTo(alreadyChosenHead[z]) == head.slopeTo(alreadyChosenTail[z])) {//&& head.distance(tail) < alreadyChosenHead[z].distance(alreadyChosenTail[z])) {
                                        insert = false;
                                        break;
                                    }/* else if (head.slopeTo(alreadyChosenHead[z]) == head.slopeTo(alreadyChosenTail[z])
                                            && head.distance(tail) > alreadyChosenHead[z].distance(alreadyChosenTail[z])) {
                                        alreadyChosenHead[z] = head;
                                        alreadyChosenTail[z] = tail;
                                        lines[z] = new LineSegment(head, tail);
                                    }
                                    else{*/
                                    //}
                                }
                                if (insert) {
                                    alreadyChosenHead[numberOfSegments] = head;
                                    alreadyChosenTail[numberOfSegments] = tail;
                                    lines[numberOfSegments] = new LineSegment(head, tail);
                                    numberOfSegments++;
                                }

                            }
                        }

                    }

                }

            }
        }

    }

    public int numberOfSegments() // the number of line segments
    {
        return numberOfSegments;
    }

    public LineSegment[] segments() // the line segments
    {
        LineSegment[] aux = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            aux[i] = lines[i];
        }
        return aux;
    }

}
