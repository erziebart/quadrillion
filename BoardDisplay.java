package Main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

public class BoardDisplay extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Piece> arrangement;
	int shape;
	
	public BoardDisplay(ArrayList<Piece> a, int shape) {
		arrangement = a;
		this.shape = shape;
		setTitle("Solution");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	@Override
	public void paint(Graphics g) {
		// draw the board
		Point[] positions = new Point[4];
		switch(shape) {
		case 0:
			positions[0] = new Point(0,0);
			positions[1] = new Point(4,0);
			positions[2] = new Point(0,4);
			positions[3] = new Point(4,4);
			break;
		case 1:
			positions[0] = new Point(0,0);
			positions[1] = new Point(4,0);
			positions[2] = new Point(8,0);
			positions[3] = new Point(12,0);
			break;
		case 2:
			positions[0] = new Point(0,0);
			positions[1] = new Point(4,0);
			positions[2] = new Point(4,4);
			positions[3] = new Point(8,4);
			break;
		case 3:
			positions[0] = new Point(4,0);
			positions[1] = new Point(8,0);
			positions[2] = new Point(0,4);
			positions[3] = new Point(4,4);
			break;
		case 4:
			positions[0] = new Point(2,0);
			positions[1] = new Point(6,2);
			positions[2] = new Point(0,4);
			positions[3] = new Point(4,6);
			break;
		case 5:
			positions[0] = new Point(4,0);
			positions[1] = new Point(0,2);
			positions[2] = new Point(4,4);
			positions[3] = new Point(0,6);
			break;
		case 6:
			positions[0] = new Point(0,0);
			positions[1] = new Point(4,2);
			positions[2] = new Point(0,4);
			positions[3] = new Point(4,6);
			break;
		case 7:
			positions[0] = new Point(4,0);
			positions[1] = new Point(0,2);
			positions[2] = new Point(8,2);
			positions[3] = new Point(4,4);
			break;
		case 8:
			positions[0] = new Point(0,0);
			positions[1] = new Point(0,4);
			positions[2] = new Point(4,4);
			positions[3] = new Point(8,4);
			break;
		case 9:
			positions[0] = new Point(8,0);
			positions[1] = new Point(0,4);
			positions[2] = new Point(4,4);
			positions[3] = new Point(8,4);
			break;
		case 10:
			positions[0] = new Point(0,0);
			positions[1] = new Point(4,0);
			positions[2] = new Point(8,0);
			positions[3] = new Point(4,4);
			break;
		default: // same as case 0
			positions[0] = new Point(0,0);
			positions[1] = new Point(4,0);
			positions[2] = new Point(0,4);
			positions[3] = new Point(4,4);
			break;
		}
		for (Point p: positions) {
			g.drawRect(352+72*p.x, 72+72*p.y, 288, 288);
		}
		
		// draw the pieces
		for (Piece p: arrangement) {
			g.setColor(p.getColor());
			for (Point s: p.getShape()) {
				g.fillOval(352+72*s.x, 72+72*s.y, 72, 72);
			}
		}
	}
}
