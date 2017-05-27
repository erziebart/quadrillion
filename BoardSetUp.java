package Main;

import java.awt.Point;
import java.util.ArrayList;

public class BoardSetUp {
	
	/* Board Shapes
	 * 
	 ******************
	 * shape = 0
	 *
	 * 0 0 1 1 
	 * 0 0 1 1 
	 * 2 2 3 3 
	 * 2 2 3 3 
	 ******************
	 * shape = 1
	 * 
	 * 0 0 1 1 2 2 3 3 
	 * 0 0 1 1 2 2 3 3 
	 ******************
	 * shape = 2
	 * 
	 * 0 0 1 1 
	 * 0 0 1 1 
	 *     2 2 3 3 
	 *     2 2 3 3 
	 ******************
	 * shape = 3
	 * 
	 *     0 0 1 1 
	 *     0 0 1 1 
	 * 2 2 3 3 
	 * 2 2 3 3 
	 ******************
	 * shape = 4
	 *
	 *   0 0
	 *   0 0 1 1 
	 * 2 2   1 1 
	 * 2 2 3 3 
	 *     3 3 
	 ******************
	 * shape = 5
	 *
	 *     0 0 
	 * 1 1 0 0 
	 * 1 1 2 2 
	 * 3 3 2 2 
	 * 3 3 
	 ******************
	 * shape = 6
	 *   
	 * 0 0 
	 * 0 0 1 1 
	 * 2 2 1 1 
	 * 2 2 3 3 
	 *     3 3 
	 ******************
	 * shape = 7
	 * 
	 *     0 0 
	 * 1 1 0 0 2 2 
	 * 1 1 3 3 2 2 
	 *     3 3 
	 ******************
	 * shape = 8
	 * 
	 * 0 0 
	 * 0 0 
	 * 1 1 2 2 3 3 
	 * 1 1 2 2 3 3 
	 ******************
	 * shape = 9
	 * 
	 *         0 0 
	 *         0 0 
	 * 1 1 2 2 3 3 
	 * 1 1 2 2 3 3 
	 ******************
	 * shape = 10
	 * 
	 * 0 0 1 1 2 2 
	 * 0 0 1 1 2 2 
	 *     3 3 
	 *     3 3
	 ******************
	 *
	 * orientations (CCW):
	 * 0 = 0 degrees
	 * 1 = 90 degrees
	 * 2 = 180 degrees
	 * 3 = 270 degrees
	*/
	public Board getBoard(int shape, Point[][] grids, int[] orientations) {
		Point[][] parts = new Point[4][];
		
		// do rotations
		for(int i = 0; i < 4; i++) {
			parts[i] = getRotated(grids[i], orientations[i]);
		}
		
		// arrange into given shape
		switch(shape){
			case 0:
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],4,0);
				parts[2] = getTranslated(parts[2],0,4);
				parts[3] = getTranslated(parts[3],4,4);
				break;
			case 1:
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],4,0);
				parts[2] = getTranslated(parts[2],8,0);
				parts[3] = getTranslated(parts[3],12,0);
				break;
			case 2:
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],4,0);
				parts[2] = getTranslated(parts[2],4,4);
				parts[3] = getTranslated(parts[3],8,4);
				break;
			case 3:
				parts[0] = getTranslated(parts[0],4,0);
				parts[1] = getTranslated(parts[1],8,0);
				parts[2] = getTranslated(parts[2],0,4);
				parts[3] = getTranslated(parts[3],4,4);
				break;
			case 4:
				parts[0] = getTranslated(parts[0],2,0);
				parts[1] = getTranslated(parts[1],6,2);
				parts[2] = getTranslated(parts[2],0,4);
				parts[3] = getTranslated(parts[3],4,6);
				break;
			case 5:
				parts[0] = getTranslated(parts[0],4,0);
				parts[1] = getTranslated(parts[1],0,2);
				parts[2] = getTranslated(parts[2],4,4);
				parts[3] = getTranslated(parts[3],0,6);
				break;
			case 6:
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],4,2);
				parts[2] = getTranslated(parts[2],0,4);
				parts[3] = getTranslated(parts[3],4,6);
				break;
			case 7:
				parts[0] = getTranslated(parts[0],4,0);
				parts[1] = getTranslated(parts[1],0,2);
				parts[2] = getTranslated(parts[2],8,2);
				parts[3] = getTranslated(parts[3],4,4);
				break;
			case 8:
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],0,4);
				parts[2] = getTranslated(parts[2],4,4);
				parts[3] = getTranslated(parts[3],8,4);
				break;
			case 9:
				parts[0] = getTranslated(parts[0],8,0);
				parts[1] = getTranslated(parts[1],0,4);
				parts[2] = getTranslated(parts[2],4,4);
				parts[3] = getTranslated(parts[3],8,4);
				break;
			case 10:
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],4,0);
				parts[2] = getTranslated(parts[2],8,0);
				parts[3] = getTranslated(parts[3],4,4);
				break;
			default: // same as case 0
				parts[0] = getTranslated(parts[0],0,0);
				parts[1] = getTranslated(parts[1],4,0);
				parts[2] = getTranslated(parts[2],0,4);
				parts[3] = getTranslated(parts[3],4,4);
				break;
		}
		
		// combine Points
		ArrayList<Point> combined = new ArrayList<Point>();
		for (Point[] b: parts) {
			for (Point p: b) {
				combined.add(p);
			}
		}
		
		return new Board(combined);
	}
	
	// returns rotated board piece points
	public Point[] getRotated(Point[] b, int orientation) {
		Point[] rotated = new Point[b.length];
		
		for (int i = 0; i < b.length; i++) {
			Point p = rotated[i] = (Point)b[i].clone();
			for (int j = 0; j < orientation; j++) {
				p.setLocation(p.y, -p.x + 3);
			}
		}
		return rotated;
	}
	
	// returns translated board piece points
	public Point[] getTranslated(Point[] b, int deltaX, int deltaY) {
		Point[] translated = new Point[b.length];
		for (int i = 0; i < b.length; i++) {
			Point p = translated[i] = (Point)b[i].clone();
			p.translate(deltaX, deltaY);
		}
		return translated;
	}
}
