
public class BruteCollinearPoints {

    private LineSegment[] lines;
    private int numberOfSegments = 0;

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
        this.lines = new LineSegment[points.length / 4];
        Point[] alreadyChosenHead = new Point[points.length / 4];
        Point[] alreadyChosenTail = new Point[points.length / 4];
        for (int i = 0; i <= points.length - 4; i++) {
            for (int j = i + 1; j < points.length; j++) {

                int count = 2;
                Point head;
                Point tail;

                for (int m = j + 1; m < points.length; m++) {
                    
                    if (points[i].compareTo(points[j]) > 0) {
                        head = points[i];
                        tail = points[j];
                    } else {
                        head = points[j];
                        tail = points[i];
                    }

                    if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) {
                        count = 3;
                        if (points[m].compareTo(tail) < 0) {
                            tail = points[m];
                        }
                        if (points[m].compareTo(head) > 0) {
                            head = points[m];
                        }
                        // search for the fourth point
                        for (int k = m + 1; k < points.length; k++) {

                            if (points[k].slopeTo(head) == points[k].slopeTo(tail)) {
                                count++;
                                if (points[k].compareTo(tail) < 0) {
                                    tail = points[k];
                                }
                                if (points[k].compareTo(head) > 0) {
                                    head = points[k];
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
                                                                       
                                    if (head.slopeTo(alreadyChosenTail[z]) == tail.slopeTo(alreadyChosenHead[z]) ||
                                        head.slopeTo(alreadyChosenHead[z]) == tail.slopeTo(alreadyChosenTail[z])) {
                                        insert = false;
                                        break;
                                    }
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

                }// end search third and fourth point

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
