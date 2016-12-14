
import java.util.Arrays;

public class FastCollinearPoints {

     private LineSegment[] lines;    
    private int numberOfSegments = 0;
    private Point[] alreadyChosenHead;
    private Point[] alreadyChosenTail;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        numberOfSegments = 0;

        if (points == null) {
            throw new NullPointerException();
        }

        // not allowed repeted points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

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

                        boolean insert = true;
                        for (int z = 0; z < numberOfSegments; z++) {

                            Double x = new Double(head.slopeTo(alreadyChosenTail[z]));
                            Double y = new Double(tail.slopeTo(alreadyChosenHead[z]));
                            Double w = new Double(head.slopeTo(alreadyChosenHead[z]));
                            Double h = new Double(tail.slopeTo(alreadyChosenTail[z]));

                            if (x.compareTo(y) == 0 || w.compareTo(h) == 0) {
                                insert = false;
                                break;

                            }

                            /* if (head.slopeTo(alreadyChosenTail[z]) == tail.slopeTo(alreadyChosenHead[z])
                                    || head.slopeTo(alreadyChosenHead[z]) == tail.slopeTo(alreadyChosenTail[z])) {
                                insert = false;
                                break;
                            }*/
                        }
                        if (insert) {
                            redimensionaVetores();
                            alreadyChosenHead[numberOfSegments] = head;
                            alreadyChosenTail[numberOfSegments] = tail;
                            lines[numberOfSegments] = new LineSegment(head, tail);
                            numberOfSegments++;
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
        return lines;
    }

     private void redimensionaVetores() {

        if (numberOfSegments == 0) {
            lines = new LineSegment[1];
            alreadyChosenHead = new Point[1];
            alreadyChosenTail = new Point[1];
        } else {
            LineSegment[] linesAux = new LineSegment[lines.length + 1];
            Point[] alreadyChosenHeadAux = new Point[alreadyChosenHead.length + 1];
            Point[] alreadyChosenTailAux = new Point[alreadyChosenTail.length + 1];

            for (int i = 0; i < lines.length; i++) {
                linesAux[i] = lines[i];
                alreadyChosenHeadAux[i] = alreadyChosenHead[i];
                alreadyChosenTailAux[i] = alreadyChosenTail[i];
            }

            lines = linesAux;
            alreadyChosenHead = alreadyChosenHeadAux;
            alreadyChosenTail = alreadyChosenTailAux;
        }
    }
}
