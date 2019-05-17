/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;


    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

        assert isDuplicates(points);

        Arrays.sort(points);
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++){
                    for (int r = q + 1; r < points.length; r++) {
                            for (int s = r + 1; s < points.length; s++) {


                                    Point pP = points[p];
                                    Point qP = points[q];
                                    Point rP = points[r];
                                    Point sP = points[s];

                                    double pq = pP.slopeTo(qP);
                                    double pr = pP.slopeTo(rP);
                                    double ps = pP.slopeTo(sP);


                                    if ((pq == pr) && (pr == ps)) {
                                        lineSegments.add(new LineSegment(pP, sP));
                                    }

                        }
                    }

            }
        }
        this.segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    private boolean isDuplicates(Point[] points){
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            for (int j = 0; j < i; j++) {
                // checks for repeats
                if (points[j].compareTo(points[i]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment ls : bcp.segments()) {
            StdOut.println(ls);
            ls.draw();
        }
        StdDraw.show();
    }
}
