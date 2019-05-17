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

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points){
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        assert isDuplicates(points);

        ArrayList<LineSegment> foundSegments = new ArrayList<LineSegment>();

        for (int i = 0;i < points.length; i++){
            Point[] copy = Arrays.copyOf(points, points.length);
            Point curr = points[i];
            Arrays.sort(copy, copy[i].slopeOrder());
            System.out.println("For point: " + curr.toString());
            ArrayList<Point> line = new ArrayList<Point>();
            int count = 1;
            for (int j = 1; j < copy.length; j++){
                System.out.println("Possible: "+ copy[j].toString());
                if (curr.slopeTo(copy[j]) == curr.slopeTo(copy[j - 1])) {
                    //slope to current is equal to last
                    count++;
                }else{
                    if (count >= 3){
                        for (int k = j - count; k < j; k++){
                            line.add(copy[k]);
                        }
                    }
                    count = 1;
                }
            }
            if (count >= 3){
                for (int x = copy.length - 1; x > copy.length - count; x--){
                    line.add(copy[x]);
                }
            }
            System.out.println("Size: " + line.size() + "\n");

            if (line.size() >= 3){
                line.add(curr);
                Point[] lineArray = line.toArray(new Point[line.size()]);
                Arrays.sort(lineArray);

                for (Point p: lineArray){
                    System.out.println("Final: " + p.toString());
                }
                System.out.println();

                LineSegment newLine = new LineSegment(lineArray[0],
                                                      lineArray[lineArray.length - 1]);
                if (foundSegments.isEmpty()){
                    foundSegments.add(newLine);
                }else{
                    boolean dup = false;
                    for (LineSegment ls : foundSegments){
                        if (isEqual(ls, newLine)){
                            dup = true;
                        }
                    }
                    if (!dup){
                        foundSegments.add(newLine);
                    }
                }
            }
        }
        this.segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    public int numberOfSegments(){
        return this.segments.length;
    }

    public LineSegment[] segments(){
        return this.segments;
    }

    private boolean isEqual(LineSegment lsA, LineSegment lsB){
        String a = lsA.toString();
        String b = lsB.toString();

        String ax = a.split("->")[0].split(",")[0].split("\\(")[1];
        String ay = a.split("->")[0].split(",")[1].split("\\)")[0];

        String bx = a.split("->")[1].split(",")[0].split("\\(")[1];
        String by = a.split("->")[1].split(",")[1].split("\\)")[0];

        String cx = b.split("->")[0].split(",")[0].split("\\(")[1];
        String cy = b.split("->")[0].split(",")[1].split("\\)")[0];

        String dx = a.split("->")[1].split(",")[0].split("\\(")[1];
        String dy = a.split("->")[1].split(",")[1].split("\\)")[0];

        //System.out.println(ax + ", " + ay + "   "+ bx + "  " + by);
        //System.out.println(ax.equals(cx) && ay.equals(cy) && bx.equals(dx) && by.equals(dy));

        return ax.equals(cx) && ay.equals(cy) && bx.equals(dx) && by.equals(dy);
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

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        for (LineSegment ls : fcp.segments()) {
            StdOut.println(ls);
            ls.draw();
        }
        StdDraw.show();
    }
}
