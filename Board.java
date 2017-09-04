import java.awt.Point;
import java.util.ArrayList;

public class Board {
	private ArrayList<Point> points;
	private boolean[][] slots;
	
	public Board(ArrayList<Point> slots) {
		this.points = new ArrayList<Point>(slots);
		
		// find max x and y values
		int maxX, maxY;
		maxX = maxY = 0;
		for(Point p: slots) {
			if(p.x > maxX) {
				maxX = p.x;
			}
			if(p.y > maxY) {
				maxY = p.y;
			}
		}
		
		// copy Points
		this.slots = new boolean[maxX+1][maxY+1];
		for(Point p: slots) {
			this.slots[p.x][p.y] = true;
		}
	}
	
	// returns the number of remaining slots
	public int size() {
		return points.size();
	}
	
	// returns a slot with the fewest neighbors
	public Point getMostCornered() {
		int record = 4;
		Point winner = points.get(0);
		for(Point p: points) {
			int current = getNeighbors(p);
			if(current < record) {
				record = current;
				winner = p;
			}
			if(record == 1) {
				return winner;
			}
		}
		return winner;
	}
	
	// returns how many slots are adjacent to given
	private int getNeighbors(Point p) {
		int neighbors = 0;
		if (isSlotOpen(p.x+1,p.y)) { //right
			neighbors++;
		}
		if (isSlotOpen(p.x-1, p.y)) { // left
			neighbors++;
		}
		if (isSlotOpen(p.x, p.y+1)) { // up
			neighbors++;
		}
		if (isSlotOpen(p.x, p.y-1)) { // down
			neighbors++;
		}
		return neighbors;
	}
	
	// returns whether a given slot is true
	public boolean isSlotOpen(int x, int y) {
		try {
			return slots[x][y];
		} 
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
		
	}
	
	// returns a list of transformed pieces p that will fit on the board in position s
	public ArrayList<Piece> fit(Piece p, Point s) {
		ArrayList<Piece> answer = new ArrayList<Piece>();
		// loop through root nodes
		for(int i = 0; i < p.getSize(); i++) {
			
			// set parameters according to symmetries
			int end = 8;
			int delta = 1;
			if(p.has90DegRotSymmetry()) {
				end = 2;
			} else if(p.has180DegRotSymmetry()) {
				end = 4;
			}
			if(p.hasMirrorSymmetry()) {
				delta = 2;
			}
			
			// loop through orientations
			for(int j = 0; j < end; j+=delta) {
				Piece transformed = p.getTransformed(s, i, j);
				if (transformed.doesFit(this)) {
					answer.add(transformed);
				}
			}
		}
		
		return answer;
	}
	
	// remove slots corresponding to given piece
	public void fillSlots(Piece p) {
		// modify points
		ArrayList<Point> copy = new ArrayList<Point>(size()-p.getSize());
		for (int j = 0; j < size(); j++) {
			Point current = points.get(j);
			if (!p.isInPiece(current)) {
				copy.add(current);
			}
		}
		points = copy;
		
		// modify slots
		for(Point point: p.getShape()) {
			slots[point.x][point.y] = false;
		}
	}
	
	// add slots corresponding to a given piece
	public void freeSlots(Piece p) {
		// copy points
		ArrayList<Point> copy = new ArrayList<Point>(size()+p.getSize());
		copy.addAll(points);
		
		// add back piece points
		for(Point point: p.getShape()) {
			copy.add(point);
			slots[point.x][point.y] = true;
		}
		
		points = copy;
	}
	
	@Override
	public String toString() {
		String s = "Board: ";
		for (Point p: points) {
			s = s.concat("(" + Integer.toString(p.x) + "," + Integer.toString(p.y) + ");");
		}
		return s;
	}
}
