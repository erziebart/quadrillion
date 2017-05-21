package Main;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Solver {
	
	public static void main(String[] args) {
		// create board grids
		Board[][] boardGrids = getDefaultBoard();
		
		Board white0 = boardGrids[0][0];
		Board white1 = boardGrids[0][1];
		Board white2 = boardGrids[0][2];
		Board white3 = boardGrids[0][3];
		Board black0 = boardGrids[1][0];
		Board black1 = boardGrids[1][1];
		Board black2 = boardGrids[1][2];
		Board black3 = boardGrids[1][3];
		
		// set up board shape
		BoardSetUp bsu = new BoardSetUp();
		int shape = 0;
		Board[] grids = {white3, black2, black1, black0};
		int[] orientations = {3,1,1,3};
		Point[] slots = bsu.getBoard(shape, grids, orientations);
		Board board = new Board(slots);
		
		// set up pieces and their shapes
		PieceList pieces = getDefaultList();
		
		// call the solver
		Solver s = new Solver();
		ArrayList<Piece> solution = s.solve(board, pieces);
		
		if (solution == null) {
			System.out.println("No Solution");
		} else {
			printSolution(solution);
			BoardDisplay image = new BoardDisplay(solution, shape);
			image.setVisible(true);
		}
		
		// get # of solutions
		//int solutions = s.solutions(board, pieces);
		//System.out.println(solutions);
	}
	
	// returns a list of piece locations that fill the board
	public ArrayList<Piece> solve(Board bCurrent, PieceList lCurrent) {
		// base case
		if (bCurrent.size() == 0) {
			return new ArrayList<Piece>();
		}
		
		// find the best slot to investigate
		Point sCurrent = bCurrent.getMostCornered();
		
		System.out.println("Begin Piece Loop");
		while (lCurrent.hasNext()) {
			Piece p = lCurrent.getNext();
			ArrayList<Piece> orientations = bCurrent.fit(p, sCurrent);
			System.out.println("Begin Orientation Loop");
			for (Piece pCurrent: orientations) {
				Board bNext = bCurrent.removeSlots(pCurrent);
				PieceList lNext = lCurrent.removePiece();
				System.out.println("Call Next Layer");
				ArrayList<Piece> answer = solve(bNext, lNext);
				System.out.println("Answer Was: " + answer);
				if (answer != null) {
					answer.add(pCurrent);
					return answer;
				}
			}
			System.out.println("End Orientation Loop");
		}
		System.out.println("End Piece Loop");
		return null;
	}
	
	// returns how many ways the given piece list can fill the given board
	public int solutions(Board bCurrent, PieceList lCurrent) {
		// base case
		if (bCurrent.size() == 0) {
			return 1;
		}
		
		int solutions = 0;
		// find the best slot to investigate
		Point sCurrent = bCurrent.getMostCornered();
		
		while (lCurrent.hasNext()) {
			Piece p = lCurrent.getNext();
			ArrayList<Piece> orientations = bCurrent.fit(p, sCurrent);
			for (Piece pCurrent: orientations) {
				Board bNext = bCurrent.removeSlots(pCurrent);
				PieceList lNext = lCurrent.removePiece();
				int answer = solutions(bNext, lNext);
				System.out.println("Answer Was: " + answer);
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
	
	public static Board[][] getDefaultBoard() {
		Board[][] result = new Board[2][4];
		
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
		
		result[0][0] = new Board(slotsw0);
		result[0][1] = new Board(slotsw1);
		result[0][2] = new Board(slotsw2);
		result[0][3] = new Board(slotsw3);
		result[1][0] = new Board(slotsb0);
		result[1][1] = new Board(slotsb1);
		result[1][2] = new Board(slotsb2);
		result[1][3] = new Board(slotsb3);
		
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
		Point[] cyan = {new Point(0,0), new Point(1,0), new Point(1,1)};
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
