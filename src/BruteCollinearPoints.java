
public class BruteCollinearPoints {

    private LineSegment[] lines;
    private int numberOfSegments = 0;
    private Point[] alreadyChosenHead;
    private Point[] alreadyChosenTail;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
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
        for (int i = 0; i <= points.length-4; i++) {
            for (int j = i + 1; j < points.length; j++) {

                int count = 2;
                Point head;
                Point tail;

                if (points[i].compareTo(points[j]) > 0) {
                    head = points[i];
                    tail = points[j];
                } else {
                    head = points[j];
                    tail = points[i];
                }

                for (int m = j + 1; m < points.length; m++) {

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

                            boolean insert = true;
                            for (int z = 0; z < numberOfSegments; z++) {

                                Double x = new Double(head.slopeTo(alreadyChosenTail[z]));
                                Double y = new Double(tail.slopeTo(alreadyChosenHead[z]));
                                Double w = new Double(head.slopeTo(alreadyChosenHead[z]));
                                Double h = new Double(tail.slopeTo(alreadyChosenTail[z]));

                                // doesn't insert a segment with the same slope of other - very restrective 
                                if (x.compareTo(w) == 0) {
                                    insert = false;
                                    break;

                                }

                                /*
                                // discard a segment only if it has the same slope of other already chosen and both have the same head and tail 
                                if ((w.compareTo(h) == 0 && ((head.compareTo(alreadyChosenHead[z])) == 0)
                                        && (tail.compareTo(alreadyChosenTail[z])) == 0)) {
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
        return lines.clone(); // defensive copy
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
