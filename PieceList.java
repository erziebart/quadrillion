package Main;

import java.util.ArrayList;

public class PieceList {
	private ArrayList<Piece> pieces;
	private int current;
	
	public PieceList() {
		pieces = new ArrayList<Piece>();
		current = -1;
	}
	
	// adds a piece to the list
	public void addPiece(Piece p) {
		pieces.add(p);
	}
	
	// returns size of list
	public int size() {
		return pieces.size();
	}
	
	// returns a new PieceList with the current Piece removed
	public PieceList removePiece() {
		PieceList copy = new PieceList();
		for (int i = 0; i < pieces.size(); i++) {
			if (current != i) {
				copy.addPiece(pieces.get(i));
			}
		}
		return copy;
	}
	
	// returns if there is a next piece in the list
	public boolean hasNext() {
		if(current+1 < pieces.size()) {
			return true;
		}
		return false;
	}
	
	// gets the next piece
	public Piece getNext() {
		try {
			current++;
			return pieces.get(current);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
}
