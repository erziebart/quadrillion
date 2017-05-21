package Main;

import java.awt.Point;
import java.util.ArrayList;

public class Board {
	private ArrayList<Point> slots;
	
	public Board(Point[] slots) {
		this.slots = new ArrayList<Point>();
		for (int i = 0; i < slots.length; i++) {
			this.slots.add(slots[i]);
		}
	}
	
	// returns the number of remaining slots
	public int size() {
		return slots.size();
	}
	
	public ArrayList<Point> getSlots() {
		return slots;
	}
	
	// returns a slot with the fewest neighbors
	public Point getMostCornered() {
		int record = 4;
		Point winner = slots.get(0);
		for(Point p: slots) {
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
	
	// returns whether a given Point is in the list
	public boolean isSlotOpen(int x, int y) {
		for (Point p: slots) {
			if (p.x == x && p.y == y) {
				return true;
			}
		}
		return false;
	}
	
	// returns a list of transformed pieces p that will fit on the board in position s
	public ArrayList<Piece> fit(Piece p, Point s) {
		ArrayList<Piece> answer = new ArrayList<Piece>();
		// loop through root nodes
		for(int i = 0; i < p.getSize(); i++) {
			// loop through orientations
			for(int j = 0; j < 8; j++) {
				Piece transformed = p.getTransformed(s, i, j);
				if (transformed.doesFit(this)) {
					answer.add(transformed);
				}
			}
		}
		
		return answer;
	}
	
	// return new Board without slots corresponding to given piece
	public Board removeSlots(Piece p) {
		Point[] copy = new Point[slots.size()-p.getSize()];
		
		int i = 0;
		for (int j = 0; j < slots.size(); j++) {
			Point current = slots.get(j);
			if (!p.isInPiece(current)) {
				copy[i] = current;
				i++;
			}
		}
		
		return new Board(copy);
	}
	
	@Override
	public String toString() {
		String s = "Board: ";
		for (Point p: slots) {
			s = s.concat("(" + Integer.toString(p.x) + "," + Integer.toString(p.y) + ");");
		}
		return s;
	}
}
