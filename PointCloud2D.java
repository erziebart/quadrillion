package Main;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;

public class PointCloud2D extends ArrayList<Point2D.Double> {

	private static final long serialVersionUID = 1L;
	
	public PointCloud2D() {
		super();
	}
	
	public PointCloud2D(int size) {
		super(size);
	}
	
	public PointCloud2D(Point2D.Double[] points) {
		super(points.length);
		for(Point2D.Double p: points) {
			this.add(p);
		}
	}
	
	// deep copy the object
	public PointCloud2D(PointCloud2D points) {
		super(points.size());
		for(Point2D.Double p: points) {
			this.add((Double) p.clone());
		}
	}
	
	// test for equality
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PointCloud2D)) {
			return false;
		}
		PointCloud2D points = (PointCloud2D)other;
		
		if(points.size() != this.size()) {
			return false;
		}
		
		Iterator<Double> i = this.iterator();
		while(i.hasNext()) {
			Point2D.Double p = (Point2D.Double)i.next();
			if(!points.contains(p)) {
				return false;
			}
		}
		return true;
	}
	
	// translates each point in the list by a given x and y value
	public void translate(double dx, double dy) {
		Iterator<Double> i = iterator();
		while(i.hasNext()) {
			Point2D.Double p = i.next();
			p.setLocation(p.getX() + dx, p.getY() + dy);
		}
	}
	
	// rotates each point about the origin by the given theta number of degrees
	public void rotate(double theta) {
		double sine, cosine;
		sine = Math.sin(Math.toRadians(theta));
		cosine = Math.cos(Math.toRadians(theta));
		
		// fixing precision errors
		if(theta%360 == 180) {
			sine = 0;
		}
		if(Math.abs(theta)%180 == 90) { 
			cosine = 0;
		}
		
		Iterator<Double> i = iterator();
		while(i.hasNext()) {
			Point2D.Double p = i.next();
			p.setLocation(p.x*cosine - p.y*sine, p.x*sine + p.y*cosine);
		}
	}
	
	// reflects each point over the given line of reflection
	// line passing through origin at angle theta relative to x-axis
	public void reflect(double theta) {
		double sine, cosine;
		sine = Math.sin(Math.toRadians(2*theta));
		cosine = Math.cos(Math.toRadians(2*theta));
		
		// fixing precision errors
		if(Math.abs(theta)%180 == 90) {
			sine = 0;
		}
		if(Math.abs(theta)%90 == 45) { 
			cosine = 0;
		}
		
		Iterator<Double> i = iterator();
		while(i.hasNext()) {
			Point2D.Double p = i.next();
			p.setLocation(p.x*cosine + p.y*sine, p.x*sine - p.y*cosine);
		}
	}
	
	// return the center of mass of the points
	public Point2D.Double getCenterOfMass() {
		double sumX, sumY;
		sumX = sumY = 0;
		Iterator<Double> i = iterator();
		while(i.hasNext()) {
			Point2D.Double p = i.next();
			sumX += p.getX();
			sumY += p.getY();
		}
		return new Point2D.Double(sumX/size(), sumY/size());
	}
}
