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
    private ArrayList<LineSegment> lineSegments;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        // repeat check
        lineSegments = new ArrayList<LineSegment>();
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
        Arrays.sort(points);
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        // check whether the three slopes between p and q, between p and r,
                        // and between p and s are all equal

                        // get slopes of the 3
                        // if pq, pr, ps are all the same, add only one lineseg
                        // else add any individual segments as well



                        Point pP = points[p];
                        Point qP = points[q];
                        Point rP = points[r];
                        Point sP = points[s];

                        //Arrays.sort(points);

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

        // need to filter and cut down list of segments based on endpoints
    }
    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[lineSegments.size()];
        for (int i = 0;i < ls.length;i++) {
            ls[i] = lineSegments.get(i);
        }
        return ls;
    }

    public void printLines() {
        for (LineSegment l : lineSegments) {
            System.out.println(l.toString());
        }
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
        bcp.printLines();
        for (LineSegment ls : bcp.lineSegments) {
            StdOut.println(ls);
            ls.draw();
        }
        StdDraw.show();
    }
}
