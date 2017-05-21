package Main;

import java.awt.Color;
import java.awt.Point;

public class Piece {
	private String name;
	private Point[] shape;
	private Color color;
	
	public Piece(String name, Point[] shape, Color color) {
		this.name = name;
		this.shape = shape;
		this.color = color;
	}
	
	// returns the Piece size
	public int getSize() {
		return shape.length;
	}
	
	// returns the Color of the Piece
	public Color getColor() {
		return color;
	}
	
	// returns the Points array
	public Point[] getShape() {
		return shape;
	}
	
	// returns if given point is in piece
	public boolean isInPiece(Point p) {
		for (Point q: shape) {
			if (p.equals(q)) {
				return true;
			}
		}
		return false;
	}
	
	// returns if the current piece fits on the board given
	public boolean doesFit(Board b) {
		for (Point p: shape) {
			if(!b.isSlotOpen(p.x, p.y)) {
				return false;
			}
		}
		return true;
	}
	
	// returns a translated, rotated, and/or mirrored version of the piece
	public Piece getTransformed(Point position, int root, int orientation) {
		Piece transformed = this.getCopy();
		transformed.setRootNode(root);
		if (orientation > 3) {
			transformed.mirror();
		}
		for (int i = 0; i < orientation%4; i++) {
			transformed.rotate();
		}
		transformed.translate(position.x, position.y);
		return transformed;
	}
	
	// returns a copy of this Piece
	private Piece getCopy() {
		Point[] newShape = new Point[this.shape.length];
		for (int i = 0; i < newShape.length; i++) {
			Point p = this.shape[i];
			newShape[i] = new Point(p);
		}
		return new Piece(this.name, newShape, this.color);
	}
	
	// translates the piece's position by (x,y)
	private void translate(int x, int y) {
		for (Point p: shape) {
			p.translate(x, y);
		}
	}
	
	// sets the rootNode (brings it to origin)
	private void setRootNode(int value) {
		translate(-shape[value].x,-shape[value].y);
	}
		
	// mirrors the piece (over y-axis)
	private void mirror() {
		for(Point p: shape) {
			p.setLocation(-p.x, p.y);
		}
	}
	
	// rotates the piece by 90 degrees CW about root node
	private void rotate() {
		for (Point p: shape) {
			p.setLocation(p.y, -p.x);
		}
	}

	
	@Override
	public String toString() {
		String s = "[" + name + ":";
		for (int i = 0; i < shape.length; i++) {
			s = s.concat("(");
			s = s.concat(Integer.toString(shape[i].x));
			s = s.concat(",");
			s = s.concat(Integer.toString(shape[i].y));
			s = s.concat(")");
		}
		s = s.concat("]");
		return s;
	}
}
