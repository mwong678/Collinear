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
            for (int q = 0; q < points.length; q++) {
                if (q != p) {
                    for (int r = 0; r < points.length; r++) {
                        if (r != q && r != p) {
                            for (int s = 0; s < points.length; s++) {
                                if (s != r && s != q && s != p) {
                                    Point pP = points[p];
                                    Point qP = points[q];
                                    Point rP = points[r];
                                    Point sP = points[s];

                                    double pq = pP.slopeTo(qP);
                                    double pr = pP.slopeTo(rP);
                                    double ps = pP.slopeTo(sP);
                                    System.out.println("/////////");
                                    System.out.println(pP.toString() + "   " + qP.toString() + "   " + rP.toString() + "   " +
                                                               sP.toString());



                                    if ((pq == pr) && (pr == ps)) {
                                        if ((pP.compareTo(qP) == -1) && (qP.compareTo(rP) == -1) &&
                                                (rP.compareTo(sP) == -1)) {
                                            lineSegments.add(new LineSegment(pP, sP));
                                        }
                                    }
                                }

                            }
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
