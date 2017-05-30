package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class QuadrillionSolverApp extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	
	// variables for computation
	private int boardShape = 0;
	private int[] orientations = {0,0,0,0};
	private Point[][][] boardGrids = Solver.getDefaultBoard();
	private int[][] grids = {{0,0},{0,1},{0,2},{0,3}};
	private PieceList pieces = Solver.getDefaultList();
	private ArrayList<Piece> solution;
	
	// variables for display pixel size
	private int slotSize = 72;
	private int horizontalBoardOffset = 352;
	private int verticalBoardOffset = 72;
	private int buttonWidth = 235;
	private int buttonHeight = 145;
	private int buttonSpacing = 45;
	private int horizontalButtonOffset = 72;
	private int horizontalDirectionsOffset = 72;
	private int verticalDirectionsOffset = 72;
	private int directionsFontSize = 30;
	
	// components
	private Rectangle solve, clear, shape;
	private Rectangle[] boardPieces = new Rectangle[4];
	
	// highlighting
	private boolean solveButton = false;
	private boolean clearButton = false;
	private boolean shapeButton = false;
	private boolean boardButtons[] = new boolean[4];

	public static void main(String[] args) {
		QuadrillionSolverApp qsa = new QuadrillionSolverApp();
		qsa.setVisible(true);
	}
	
	public QuadrillionSolverApp() {
		setTitle("Quadrillion Solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1920, 1080);
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		// setup components
		initButtons();
		initBoard();
	}
	
	
	/* Initialization */
	private void initButtons() {
		// dimensions
		int buttonX, buttonY, buttonW, buttonH;
		buttonX = horizontalBoardOffset + 16*slotSize + horizontalButtonOffset;
		buttonY = verticalBoardOffset;
		buttonW = buttonWidth;
		buttonH = buttonHeight;
		
		// solve button
		solve = new Rectangle(buttonX, buttonY, buttonW, buttonH);
		
		// clear button
		buttonY += buttonHeight+buttonSpacing;
		clear = new Rectangle(buttonX, buttonY, buttonW, buttonH);
		
		// shape button
		buttonY += buttonHeight+buttonSpacing;
		shape = new Rectangle(buttonX, buttonY, buttonW, buttonH);
	}
	
	private void initBoard() {
		// basis Rectangle
		Rectangle corner = new Rectangle(horizontalBoardOffset, 
										 verticalBoardOffset, 
										 slotSize*4, slotSize*4);
		
		// get positions of board pieces
		Point[] positions = getBoardPositions();
		
		// set locations
		for(int i = 0; i < 4; i++) {
			Point cur = positions[i];
			boardPieces[i] = new Rectangle(corner.x + cur.x*slotSize,
										   corner.y + cur.y*slotSize, 
										   corner.width, corner.height);
		}
	}
	
	private Point[] getBoardPositions() {
		Point[] result = new Point[4];
		switch(boardShape) {
		case 0:
			result[0] = new Point(0,0);
			result[1] = new Point(4,0);
			result[2] = new Point(0,4);
			result[3] = new Point(4,4);
			break;
		case 1:
			result[0] = new Point(0,0);
			result[1] = new Point(4,0);
			result[2] = new Point(8,0);
			result[3] = new Point(12,0);
			break;
		case 2:
			result[0] = new Point(0,0);
			result[1] = new Point(4,0);
			result[2] = new Point(4,4);
			result[3] = new Point(8,4);
			break;
		case 3:
			result[0] = new Point(4,0);
			result[1] = new Point(8,0);
			result[2] = new Point(0,4);
			result[3] = new Point(4,4);
			break;
		case 4:
			result[0] = new Point(2,0);
			result[1] = new Point(6,2);
			result[2] = new Point(0,4);
			result[3] = new Point(4,6);
			break;
		case 5:
			result[0] = new Point(4,0);
			result[1] = new Point(0,2);
			result[2] = new Point(4,4);
			result[3] = new Point(0,6);
			break;
		case 6:
			result[0] = new Point(0,0);
			result[1] = new Point(4,2);
			result[2] = new Point(0,4);
			result[3] = new Point(4,6);
			break;
		case 7:
			result[0] = new Point(4,0);
			result[1] = new Point(0,2);
			result[2] = new Point(8,2);
			result[3] = new Point(4,4);
			break;
		case 8:
			result[0] = new Point(0,0);
			result[1] = new Point(0,4);
			result[2] = new Point(4,4);
			result[3] = new Point(8,4);
			break;
		case 9:
			result[0] = new Point(8,0);
			result[1] = new Point(0,4);
			result[2] = new Point(4,4);
			result[3] = new Point(8,4);
			break;
		case 10:
			result[0] = new Point(0,0);
			result[1] = new Point(4,0);
			result[2] = new Point(8,0);
			result[3] = new Point(4,4);
			break;
		default: // same as case 0
			result[0] = new Point(0,0);
			result[1] = new Point(4,0);
			result[2] = new Point(0,4);
			result[3] = new Point(4,4);
			break;
		}
		
		return result;
	}
	
	
	/* Actions */
	public void solve() {
		// set up board
		BoardSetUp bsu = new BoardSetUp();
		Point[][] parts = {boardGrids[grids[0][0]][grids[0][1]],
						   boardGrids[grids[1][0]][grids[1][1]],
						   boardGrids[grids[2][0]][grids[2][1]],
						   boardGrids[grids[3][0]][grids[3][1]]};
		Board board = bsu.getBoard(boardShape, parts, orientations);
		
		// solve
		solution = Solver.solve(board, pieces);
		
		// check for solution
		if(solution == null) {
			pieces.reset();
		}
		
		repaint();
	}
	
	public void clear() {
		solution = null;
		pieces.reset();
		
		repaint();
	}
	
	public void changeShape() {
		boardShape++;
		if(boardShape > 10) {
			boardShape = 0;
		}
		initBoard();
		repaint();
	}
	
	public void flipPiece(int index) {
		grids[index][0] = grids[index][0] == 1 ? 0 : 1;
		
		repaint();
	}
	
	public void rotatePiece(int index, boolean clockwise) {
		if(clockwise) {
			orientations[index]--;
		} else {
			orientations[index]++;
		}
		
		if(orientations[index] < 0) {
			orientations[index] = 3;
		}
		if(orientations[index] > 3) {
			orientations[index] = 0;
		}
		
		repaint();
	}
	
	public void resetBoardPieceHighlight() {
		for(int i = 0; i < 4; i++) {
			boardButtons[i] = false;
		}
		
		repaint();
	}
	
	public void swapPieces(int index1, int index2) {
		int[] temp = new int[2];
		temp[0] = grids[index1][0];
		temp[1] = grids[index1][1];
		
		grids[index1][0] = grids[index2][0];
		grids[index1][1] = grids[index2][1];
		
		grids[index2][0] = temp[0];
		grids[index2][1] = temp[1];
		
		int temp2 = orientations[index1];
		orientations[index1] = orientations[index2];
		orientations[index2] = temp2;
		
		repaint();
	}
	
	
	/* Painting */
	@Override
	public void paint(Graphics g) {
		// double buffer
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics doubleBuffer = image.getGraphics();
		
		// draw background
		doubleBuffer.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		// buttons
		paintSolveButton(doubleBuffer);
		paintClearButton(doubleBuffer);
		paintShapeButton(doubleBuffer);
		
		// board
		paintBoard(doubleBuffer);
		
		// the solution - if applicable
		if(solution != null) {
			paintSolution(doubleBuffer);
		}
		
		// directions
		paintDirections(doubleBuffer);
		
		g.drawImage(image, 0, 0, this);
	}
	
	private void paintSolveButton(Graphics g) {
		// highlight and non colors
		Color bg = solveButton ? Color.GRAY : Color.DARK_GRAY;
		Color txt = solveButton ? Color.DARK_GRAY : Color.BLACK;
		
		g.setColor(bg);
		g.fillRect(solve.x, solve.y, solve.width, solve.height);
		
		g.setColor(Color.BLACK);
		g.drawRect(solve.x, solve.y, solve.width, solve.height);
		
		Point center = new Point(solve.x + solve.width/2, solve.y + solve.height/2);
		g.setColor(txt);
		g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(buttonHeight/2)));
		Point p = new Point((int)Math.round(center.x - solve.width*0.41), (int)Math.round(center.y + buttonHeight*0.2));
		g.drawString("Solve", p.x, p.y);
	}
	
	private void paintClearButton(Graphics g) {
		// highlight and non colors
		Color bg = clearButton ? Color.GRAY : Color.DARK_GRAY;
		Color txt = clearButton ? Color.DARK_GRAY : Color.BLACK;
		
		g.setColor(bg);
		g.fillRect(clear.x, clear.y, clear.width, clear.height);
		
		g.setColor(Color.BLACK);
		g.drawRect(clear.x, clear.y, clear.width, clear.height);
		
		Point center = new Point(clear.x + clear.width/2, clear.y + clear.height/2);
		g.setColor(txt);
		g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(buttonHeight/2)));
		Point p = new Point((int)Math.round(center.x - clear.width*0.41), (int)Math.round(center.y + buttonHeight*0.2));
		g.drawString("Clear", p.x, p.y);
	}
	
	private void paintShapeButton(Graphics g) {
		// highlight and non colors
		Color bg = shapeButton ? Color.GRAY : Color.DARK_GRAY;
		Color txt = shapeButton ? Color.DARK_GRAY : Color.BLACK;
		
		g.setColor(bg);
		g.fillRect(shape.x, shape.y, shape.width, shape.height);
		
		g.setColor(Color.BLACK);
		g.drawRect(shape.x, shape.y, shape.width, shape.height);
		
		Point center = new Point(shape.x + shape.width/2, shape.y + shape.height/2);
		g.setColor(txt);
		g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(buttonHeight/3)));
		Point p = new Point((int)Math.round(center.x - shape.width*0.48), (int)Math.round(center.y + buttonHeight*0.13));
		String s = "Shape=".concat(Integer.toString(boardShape+1));
		g.drawString(s, p.x, p.y);
	}
	
	private void paintBoard(Graphics g) {
		// draw the empty board pieces
		for (int i = 0; i < 4; i++) {
			Color bg = grids[i][0] == 1 ? boardButtons[i] ? Color.GRAY : Color.BLACK : Color.WHITE;
			Color holes = grids[i][0] == 1 ? Color.WHITE : boardButtons[i] ? Color.GRAY : Color.BLACK;
			
			// draw board piece
			Rectangle piece = boardPieces[i];
			g.setColor(bg);
			g.fillRect(piece.x, piece.y, piece.width, piece.height);
			
			// draw gaps
			Point[] gaps = getBoardPieceGaps(grids[i][0], grids[i][1], orientations[i]);
			for(Point gap: gaps) {
				// bring to location of board piece
				gap.x += (piece.x - horizontalBoardOffset)/slotSize;
				gap.y += (piece.y - verticalBoardOffset)/slotSize;
				
				// paint the slot
				g.setColor(holes);
				paintSlot(g, gap);
			}
			
			// boarder
			g.setColor(boardButtons[i] ? Color.GRAY : Color.BLACK);
			g.drawRect(piece.x, piece.y, piece.width, piece.height);
		}
	}
	
	private Point[] getBoardPieceGaps(int side, int piece, int rotation) {
		int size = piece == 0 ? 1 : 2;
		Point[] result = new Point[size];
		
		switch(side*4 + piece) {
		case 0: // white 0
			result[0] = new Point(1,0);
			break;
		case 1: // white 1
			result[0] = new Point(1,0);
			result[1] = new Point(2,0);
			break;
		case 2: // white 2
			result[0] = new Point(0,0);
			result[1] = new Point(3,0);
			break;
		case 3: // white 3
			result[0] = new Point(3,0);
			result[1] = new Point(0,2);
			break;
		case 4: // black 0
			result[0] = new Point(2,0);
			break;
		case 5: // black 1
			result[0] = new Point(1,1);
			result[1] = new Point(0,3);
			break;
		case 6: // black 2
			result[0] = new Point(3,1);
			result[1] = new Point(3,3);
			break;
		case 7: // black 3
			result[0] = new Point(2,2);
			result[1] = new Point(0,3);
			break;
		}
		
		// get correct orientation
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < rotation; j++) {
				// rotate 90 degrees CCW
				Point temp = new Point(result[i].y, 3 - result[i].x);
				result[i].x = temp.x;
				result[i].y = temp.y;
			}
		}
		
		return result;
	}
	
	private void paintSolution(Graphics g) {
		for (Piece p: solution) {
			g.setColor(p.getColor());
			for (Point s: p.getShape()) {
				paintSlot(g, s);
			}
		}
	}
	
	private void paintSlot(Graphics g, Point slot) {
		Point location = new Point(horizontalBoardOffset + slot.x*slotSize,
								   verticalBoardOffset + slot.y*slotSize);
		g.fillOval(location.x, location.y, slotSize, slotSize);
	}
	
	private void paintDirections(Graphics g) {
		String directions = "Left Click = Highlight    Right Click = Deselect    "
							+ "LEFT & RIGHT arrows rotate    F = flip over";
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, directionsFontSize));
		g.drawString(directions, horizontalDirectionsOffset, getHeight()-verticalDirectionsOffset);
	}
	
	
	/* Keyboard Events */
	@Override
	public void keyPressed(KeyEvent e) {
		int highlighted = getHighlightedPiece(-1);
		
		if(highlighted != -1) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) { // rotate ccw
				rotatePiece(highlighted, false);
			} 
			
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) { // rotate cw
				rotatePiece(highlighted, true);
			} 
			
			else if(e.getKeyCode() == KeyEvent.VK_F) { // flip over to other color
				flipPiece(highlighted);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	

	/* Mouse Events */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) { // left click
			// buttons
			if(solveButton) {
				resetBoardPieceHighlight();
				solve();
			} else if(clearButton) {
				resetBoardPieceHighlight();
				clear();
			} else if(shapeButton && solution == null) {
				resetBoardPieceHighlight();
				changeShape();
			}
			
			// board pieces
			if(solution == null) {
				Point loc  = new Point(e.getX(), e.getY());
				for(int i = 0; i < 4; i++) {
					if(isInRect(boardPieces[i], loc)) {
						boardButtons[i] = true;
						repaint();
					}
				}
			}
			
			// swapping
			int i1, i2;
			if((i1 = getHighlightedPiece(-1)) != -1) {
				if((i2 = getHighlightedPiece(i1)) != -1) {
					// swap the indices
					swapPieces(i1, i2);
					resetBoardPieceHighlight();
				}
			}
		}
		
		else if(e.getButton() == MouseEvent.BUTTON3) { // right click
			resetBoardPieceHighlight();
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point loc = new Point(e.getX(),e.getY());
		
		// highlighting if mouse inside
		solveButton = isInRect(solve, loc);
		clearButton = isInRect(clear, loc);
		shapeButton = isInRect(shape, loc);
		
		repaint();
	}
	
	
	/* Other Helper Methods */
	private boolean isInRect(Rectangle r, Point p) {
		return (p.x >= r.x && p.x <= r.x+r.width) &&
			   (p.y >= r.y && p.y <= r.y+r.height);
	}
	
	private int getHighlightedPiece(int after) {
		for(int i = after+1; i < 4; i++) {
			if(boardButtons[i]) {
				return i;
			}
		}
		
		return -1; // none highlighted
	}
}
