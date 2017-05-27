package Main;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Piece {
	private String name;
	private Point[] shape;
	private Color color;
	
	// symmetries
	private boolean deg90Rot = false;
	private boolean deg180Rot = false;
	private boolean mirror = false;
	
	public Piece(String name, Point[] shape, Color color) {
		// set given fields
		this.name = name;
		this.shape = shape;
		this.color = color;
		
		// set symmetries
		this.setSymmetries();
	}
	
	private Piece(Piece p) {
		// copy fields
		this.name = p.name;
		this.color = p.color;
		this.deg90Rot = p.deg90Rot;
		this.deg180Rot = p.deg180Rot;
		this.mirror = p.mirror;
		
		// copy Point array
		this.shape = new Point[p.shape.length];
		for (int i = 0; i < this.shape.length; i++) {
			this.shape[i] = new Point(p.shape[i]);
		}
	}
	
	// sets the symmetries in the Piece
	private void setSymmetries() {
		// convert Points to double precision
		int size = this.getSize();
		PointCloud2D points = new PointCloud2D(size);
		for(int i = 0; i < size; i++) {
			points.add(new Point2D.Double(shape[i].x, shape[i].y)); 
		}
		
		// translate Points so center of mass at origin
		Point2D.Double cm = points.getCenterOfMass();
		points.translate(-cm.getX(), -cm.getY());
		
		// test for symmetries
		if(Piece.has90DegRotSymmetry(points)) {
			deg90Rot = true;
			deg180Rot = true;
		} else if(Piece.has180DegRotSymmetry(points)) {
			deg180Rot = true;
		}
		
		if(Piece.hasMirrorSymmetry(points)) {
			mirror = true;
		}
	}
	
	private static boolean has90DegRotSymmetry(PointCloud2D points) {
		PointCloud2D other = new PointCloud2D(points);
		other.rotate(90);
		return points.equals(other);
	}
	
	boolean has90DegRotSymmetry() {
		return deg90Rot;
	}
	
	private static boolean has180DegRotSymmetry(PointCloud2D points) {
		PointCloud2D other = new PointCloud2D(points);
		other.rotate(180);
		return points.equals(other);
	}
	
	boolean has180DegRotSymmetry() {
		return deg180Rot;
	}
	
	private static boolean hasMirrorSymmetry(PointCloud2D points) {
		PointCloud2D other;
		
		// horizontal
		other = new PointCloud2D(points);
		other.reflect(0);
		if(other.equals(points)) {
			return true;
		}
		
		// vertical
		other = new PointCloud2D(points);
		other.reflect(90);
		if(other.equals(points)) {
			return true;
		}
		
		// isometric
		other = new PointCloud2D(points);
		other.reflect(45);
		if(other.equals(points)) {
			return true;
		}
		other = new PointCloud2D(points);
		other.reflect(-45);
		if(other.equals(points)) {
			return true;
		}
		
		return false;
	}
	
	boolean hasMirrorSymmetry() {
		return mirror;
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
	Point[] getShape() {
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
		
		if(orientation % 2 == 1) {
			transformed.mirror();
		}
		for (int i = 0; i < orientation/2; i++) {
			transformed.rotate();
		}
		transformed.translate(position.x, position.y);
		return transformed;
	}
	
	// returns a copy of this Piece
	private Piece getCopy() {
		return new Piece(this);
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
