package Main;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Solver {
	
	public static void main(String[] args) {
		// create board grids
		Point[][][] boardGrids = getDefaultBoard();
		
		// set up board shape
		BoardSetUp bsu = new BoardSetUp();
		int shape = 0;
		Point[][] grids = {boardGrids[0][0],  // white 0
						   boardGrids[0][1],  // white 1
						   boardGrids[0][2],  // white 2
						   boardGrids[0][3]}; // white 3
		int[] orientations = {0,0,0,0};
		Board board = bsu.getBoard(shape, grids, orientations);
		
		// set up pieces and their shapes
		PieceList pieces = getDefaultList();
		
		// call the solver
		ArrayList<Piece> solution = solve(board, pieces);
		
		if (solution == null) {
			System.out.println("No Solution");
		} else {
			printSolution(solution);
			BoardDisplay image = new BoardDisplay(solution, shape);
			image.setVisible(true);
		}
		
		pieces.reset();
		
		// get # of solutions
		int solutions = solutions(board, pieces);
		System.out.println("Solutions Found = " + solutions);
	}
	
	// returns a list of piece locations that fill the board
	public static ArrayList<Piece> solve(Board bCurrent, PieceList lCurrent) {
		// base case
		if (bCurrent.size() == 0) {
			return new ArrayList<Piece>();
		}
		
		// find the best slot to investigate
		Point sCurrent = bCurrent.getMostCornered();
		
		//System.out.println("Begin Piece Loop");
		// loop through all the pieces
		while (lCurrent.hasNext()) {
			Piece p = lCurrent.getNext();
			
			/*if(lCurrent.size() == 12) {
				System.out.println(p);
			}
			if(lCurrent.size() == 11) {
				System.out.println("\t" + p);
			}*/
			
			// loop across all orientations of this piece
			ArrayList<Piece> orientations = bCurrent.fit(p, sCurrent);
			//System.out.println("Begin Orientation Loop");
			for (Piece pCurrent: orientations) {
				
				// place piece
				bCurrent.fillSlots(pCurrent);
				PieceList lNext = lCurrent.removePiece();
				
				// recursive call
				//System.out.println("Call Next Layer");
				ArrayList<Piece> answer = solve(bCurrent, lNext);
				
				// remove piece
				bCurrent.freeSlots(pCurrent);
				
				// check for answer
				//System.out.println("Answer Was: " + answer);
				if (answer != null) {
					answer.add(pCurrent);
					return answer;
				}
			}
			//System.out.println("End Orientation Loop");
		}
		//System.out.println("End Piece Loop");
		return null;
	}
	
	// returns how many ways the given piece list can fill the given board
	public static int solutions(Board bCurrent, PieceList lCurrent) {
		//System.out.println(lCurrent.size());
		
		// base case
		if (bCurrent.size() == 0) {
			return 1;
		}
		
		int solutions = 0; // running total
		
		// find the best slot to investigate
		Point sCurrent = bCurrent.getMostCornered();
		
		// loop through all the pieces
		while (lCurrent.hasNext()) {
			Piece p = lCurrent.getNext();
			/*if(lCurrent.size() == 11) {
				System.out.println(p);
			}
			if(lCurrent.size() == 10) {
				System.out.println("\t" + p);
			}*/
			
			// loop across all orientations of this piece
			ArrayList<Piece> orientations = bCurrent.fit(p, sCurrent);
			for (Piece pCurrent: orientations) {
				
				// place piece
				bCurrent.fillSlots(pCurrent);
				PieceList lNext = lCurrent.removePiece();
				
				// recursive call
				int answer = solutions(bCurrent, lNext);
				//System.out.println("Answer Was: " + answer);
				
				// remove piece
				bCurrent.freeSlots(pCurrent);
				
				// add answers to total
				solutions += answer;
			}
		}
		
		return solutions;
	}
	
	public static void printSolution(ArrayList<Piece> solution) {
		for (Piece p: solution) {
			System.out.println(p.toString());
		}
	}
	
	public static Point[][][] getDefaultBoard() {
		Point[][][] result = new Point[2][4][];
		
		Point[] slotsw0 = {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3),
				/*new Point(1,0),*/ new Point(1,1), new Point(1,2), new Point(1,3),
				new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3),
				new Point(3,0), new Point(3,1), new Point(3,2), new Point(3,3)};
		Point[] slotsw1 = {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3),
				/*new Point(1,0),*/ new Point(1,1), new Point(1,2), new Point(1,3),
				/*new Point(2,0),*/ new Point(2,1), new Point(2,2), new Point(2,3),
				new Point(3,0), new Point(3,1), new Point(3,2), new Point(3,3)};
		Point[] slotsw2 = {/*new Point(0,0),*/ new Point(0,1), new Point(0,2), new Point(0,3),
				new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3),
				new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3),
				/*new Point(3,0),*/ new Point(3,1), new Point(3,2), new Point(3,3)};
		Point[] slotsw3 = {new Point(0,0), new Point(0,1), /*new Point(0,2),*/ new Point(0,3),
				new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3),
				new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3),
				/*new Point(3,0),*/ new Point(3,1), new Point(3,2), new Point(3,3)};
		Point[] slotsb0 = {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3),
				new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3),
				/*new Point(2,0),*/ new Point(2,1), new Point(2,2), new Point(2,3),
				new Point(3,0), new Point(3,1), new Point(3,2), new Point(3,3)};
		Point[] slotsb1 = {new Point(0,0), new Point(0,1), new Point(0,2), /*new Point(0,3),*/
				new Point(1,0), /*new Point(1,1),*/ new Point(1,2), new Point(1,3),
				new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3),
				new Point(3,0), new Point(3,1), new Point(3,2), new Point(3,3)};
		Point[] slotsb2 = {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3),
				new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3),
				new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3),
				new Point(3,0), /*new Point(3,1),*/ new Point(3,2), /*new Point(3,3)*/};
		Point[] slotsb3 = {new Point(0,0), new Point(0,1), new Point(0,2), /*new Point(0,3),*/
				new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3),
				new Point(2,0), new Point(2,1), /*new Point(2,2),*/ new Point(2,3),
				new Point(3,0), new Point(3,1), new Point(3,2), new Point(3,3)};
		
		result[0][0] = slotsw0;
		result[0][1] = slotsw1;
		result[0][2] = slotsw2;
		result[0][3] = slotsw3;
		result[1][0] = slotsb0;
		result[1][1] = slotsb1;
		result[1][2] = slotsb2;
		result[1][3] = slotsb3;
		
		return result;
	}
	
	public static PieceList getDefaultList() {
		PieceList result = new PieceList();
		
		// piece shapes
		Point[] red = {new Point(0,0), new Point(0,1),
				new Point(1,0), new Point(2,0), new Point(3,0)};
		Point[] magenta = {new Point(0,0), new Point(0, -1),
				new Point(1,0), new Point(1,1)};
		Point[] purple = {new Point(0,0), new Point(1,0),
				new Point(1,1), new Point(2,1), new Point(2,2)};
		Point[] blue = {new Point(0,0), new Point(1,0), new Point(2,0),
				new Point(0,1), new Point(0,2)};
		Point[] yellow = {new Point(0,0), new Point(0,1), new Point(0,2),
				new Point(-1,1), new Point(0,3)};
		Point[] lime = {new Point(0,0), new Point(1,0), new Point(2,0),
				new Point(0,1), new Point(2,1)};
		Point[] mint = {new Point(0,0), new Point(0,1), new Point(0,2),
				new Point(1,0), new Point(1,1)};
		Point[] cyan = {new Point(0,0), new Point(1,0), new Point(0,1)};
		Point[] indigo = {new Point(0,0), new Point(1,0), new Point(1,1),
				new Point(1,2), new Point(2,2)};
		Point[] pink = {new Point(0,0), new Point(0,1), new Point(0,2),
				new Point(1,2), new Point(1,3)};
		Point[] orange = {new Point(0,0), new Point(-1,0), new Point(0,-1),
				new Point(1,0), new Point(1,1)};
		Point[] turquoise = {new Point(0,0), new Point(0,1), new Point(0,2),
				new Point(-1,2), new Point(1,2)};
		
		// add all
		result.addPiece(new Piece("Red", red, new Color(255,35,0)));
		result.addPiece(new Piece("Purple", purple, new Color(90,0,90)));
		result.addPiece(new Piece("Indigo", indigo, new Color(40,0,150)));
		result.addPiece(new Piece("Orange", orange, new Color(255,155,0)));
		result.addPiece(new Piece("Blue", blue, new Color(0,165,235)));
		result.addPiece(new Piece("Yellow", yellow, new Color(255,230,0)));
		result.addPiece(new Piece("Lime", lime, new Color(185,255,65)));
		result.addPiece(new Piece("Mint", mint, new Color(100,255,170)));
		result.addPiece(new Piece("Pink", pink, new Color(255,120,170)));
		result.addPiece(new Piece("Magenta", magenta, new Color(124,0,20)));
		result.addPiece(new Piece("Cyan", cyan, new Color(100,255,255)));
		result.addPiece(new Piece("Turquoise", turquoise, new Color(0,135,100)));
		
		return result;
	}

}
