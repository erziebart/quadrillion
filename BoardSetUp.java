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
	 * orientations (CW):
	 * 0 = 0 degrees
	 * 1 = 90 degrees
	 * 2 = 180 degrees
	 * 3 = 270 degrees
	*/
	public Point[] getBoard(int shape, Board[] grids, int[] orientations) {
		Board[] parts = new Board[4];
		
		// copy and do rotations
		for(int i = 0; i < 4; i++) {
			ArrayList<Point> slots = grids[i].getSlots();
			Point[] next = new Point[slots.size()];
			parts[i] = new Board(slots.toArray(next));
			for(int j = 0; j < orientations[i]; j++) {
				parts[i] = getRotated(parts[i]);
			}
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
		for (Board b: parts) {
			combined.addAll(b.getSlots());
		}
		Point[] board = new Point[combined.size()];
		return combined.toArray(board);
	}
	
	// returns rotated board piece
	public Board getRotated(Board b) {
		ArrayList<Point> slots = b.getSlots();
		Point[] rotated = new Point[slots.size()];
		rotated = slots.toArray(rotated);
		for (Point p: rotated) {
			p.setLocation(p.y, -p.x + 3);
		}
		return new Board(rotated);
	}
	
	// returns translated board piece
	public Board getTranslated(Board b, int deltaX, int deltaY) {
		ArrayList<Point> slots = b.getSlots();
		Point[] translated = new Point[slots.size()];
		translated = slots.toArray(translated);
		for (Point p: translated) {
			p.translate(deltaX, deltaY);
		}
		return new Board(translated);
	}
}
