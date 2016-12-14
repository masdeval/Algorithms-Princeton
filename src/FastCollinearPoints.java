
import java.util.Arrays;

public class FastCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] lines;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        numberOfSegments = 0;

        if (points == null) {
            throw new NullPointerException();
        }

        // not allowed repeted points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        this.lines = new LineSegment[points.length / 4 +1];
        Point[] alreadyChosenHead = new Point[points.length / 4 +1];
        Point[] alreadyChosenTail = new Point[points.length / 4 +1];

        for (int i = 0; i <= points.length - 4; i++) {

            Point[] aux = points.clone();
            Arrays.sort(aux, i + 1, aux.length, aux[i].slopeOrder());

            //search for three or more collinear points
            int count = 2;
            Point head;
            Point tail;
            if (aux[i].compareTo(aux[i + 1]) > 0) {
                head = aux[i];
                tail = aux[i + 1];
            } else {
                head = aux[i + 1];
                tail = aux[i];
            }

            //head = tail = points[i];
            for (int j = i + 1; j < aux.length - 1; j++) {

                while ((j < aux.length - 1) && (points[i].slopeTo(aux[j]) == points[i].slopeTo(aux[j + 1]))) {
                    count++;
                    /*  if (aux[j].compareTo(tail) < 0) {
                        tail = aux[j];
                    }
                    if (aux[j].compareTo(head) > 0) {
                        head = aux[j];
                    }*/

                    if (aux[j + 1].compareTo(tail) < 0) {
                        tail = aux[j + 1];
                    }
                    if (aux[j + 1].compareTo(head) > 0) {
                        head = aux[j + 1];
                    }
                    j++;
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

                            if (head.slopeTo(alreadyChosenTail[z]) == tail.slopeTo(alreadyChosenHead[z])
                                    || head.slopeTo(alreadyChosenHead[z]) == tail.slopeTo(alreadyChosenTail[z])) {
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
                if (j == aux.length - 1) {
                    j--;
                }

                count = 2;
                // head = tail = points[i];
                if (aux[i].compareTo(aux[j + 1]) > 0) {
                    head = aux[i];
                    tail = aux[j + 1];
                } else {
                    head = aux[j + 1];
                    tail = aux[i];
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
