// import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
// random comment to test git
class Point implements Comparable<Point> {
	int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int compareTo(Point p) {
		if (this.x == p.x) {
			return this.y - p.y;
		} else {
			return this.x - p.x;
		}
	}

	public String toString() {
		return "["+x + "," + y+"],";
	}

}

public class MonotoneChain {

	public static long cross(Point O, Point A, Point B) {
		return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
	}

	public static Point[] convex_hull(Point[] P) {

		if (P.length > 1) {
			int n = P.length, k = 0;
			Point[] H = new Point[2 * n];

			Arrays.sort(P);

			// Build lower hull
			for (int i = 0; i < n; ++i) {
				while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0)
					k--;
				H[k++] = P[i];
			}

			// Build upper hull
			for (int i = n - 2, t = k + 1; i >= 0; i--) {
				while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0)
					k--;
				H[k++] = P[i];
			}
			if (k > 1) {
				H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
			}
			return H;
		} else if (P.length <= 1) {
			return P;
		} else{
			return null;
		}
	}
private static PrintWriter createFile(String fileName){
		
		try{
			
			// Creates a File object that allows you to work with files on the hardrive
			
			File listOfNames = new File(fileName);
			
			// FileWriter is used to write streams of characters to a file
			// BufferedWriter gathers a bunch of characters and then writes
			// them all at one time (Speeds up the Program)
			// PrintWriter is used to write characters to the console, file
	
			PrintWriter infoToWrite = new PrintWriter(
			new BufferedWriter(
					new FileWriter(listOfNames)));
			return infoToWrite;
		}
	
		// You have to catch this when you call FileWriter
		
		catch(IOException e){
		
			System.out.println("An I/O Error Occurred");
			
			// Closes the program
			
			System.exit(0);
		
		}
		return null;
		
	}
	public static void main(String[] args) {

		PrintWriter timeToRun = createFile("/Users/brianferry/Documents/javaOutput/monoPerf.txt");
		//PrintWriter points = createFile("/Users/brianferry/Documents/javaOutput/monoPoints.txt");
		//PrintWriter hullPoints = createFile("/Users/brianferry/Documents/javaOutput/monoHullPoints.txt");

		int maxInput = 2000000;
        Random ran = new Random();
	    for (int N = 0; N < maxInput; N+= 20000 ) {
	        Point[] p = new Point[N];
	        // points.println("The points in the set for which we will be finding the convex hull are: \n");

	        for (int i = 0; i < N; i++)
	        {
	            int x = ran.nextInt(N); //sc.nextInt();
	            int y = ran.nextInt(N); //sc.nextInt();
	            p[i] = new Point(x, y);
	            // points.println(p[i]);
	        }
	      //  points.println("The convex hull of above points is:\n");
	
	        long startTime = System.currentTimeMillis();
	
			Point[] hull = convex_hull(p).clone();

	        /*System.out
	                .println("The points in the Convex hull using monotone chain are: ");
	       for (int i = 0; i < hull.length; i++) {
				if (hull[i] != null)
					System.out.print(hull[i] + "\n");
					hullPoints.println((hull[i]));
	        } */
	        long endTime = System.currentTimeMillis();
			
			String perfInfo = Integer.toString(N) + " " + Long.toString(endTime - startTime);
			//custInfo += Integer.toString(customer.custAge);
			
			// Writes the string to the file
			
			timeToRun.println(perfInfo);
	        System.out.println("It took " + (endTime - startTime) + " milliseconds");

        }
		timeToRun.close();
		// points.close();
	
	}

}
