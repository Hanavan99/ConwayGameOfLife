package com.github.hanavan99.conwaygameoflife.ui.model;

/**
 * A position in the layout
 * 
 * @author Zach Deibert
 */
public class Point extends java.awt.Point {
	private static final long serialVersionUID = -3735157353094832593L;
	private String x;
	private String y;

	/**
	 * Gets the x-coordinate of the point
	 * 
	 * @param width
	 *            The width of the parent component
	 * @return The x-coordinate of this point
	 */
	public double getX(double width) {
		if ( x == null ) {
			return 0;
		} else if ( x.endsWith("%") ) {
			return java.lang.Double.parseDouble(x.substring(0, x.length() - 1)) / 100. * width;
		} else {
			return java.lang.Double.parseDouble(x);
		}
	}

	/**
	 * Gets the x-coordinate of the point
	 * 
	 * @param size
	 *            The size of the parent component
	 * @return The x-coordinate of this point
	 */
	public double getX(java.awt.Dimension size) {
		return getX(size.getWidth());
	}

	/**
	 * Sets the x-coordinate of this point
	 * 
	 * @param x
	 *            The x-coordinate
	 */
	public void setX(String x) {
		this.x = x;
	}

	/**
	 * Gets the y-coordinate of the point
	 * 
	 * @param height
	 *            The height of the parent component
	 * @return The y-coordinate of this point
	 */
	public double getY(double height) {
		if ( y == null ) {
			return 0;
		} else if ( y.endsWith("%") ) {
			return java.lang.Double.parseDouble(y.substring(0, y.length() - 1)) / 100. * height;
		} else {
			return java.lang.Double.parseDouble(y);
		}
	}

	/**
	 * Gets the y-coordinate of the point
	 * 
	 * @param size
	 *            The size of the parent component
	 * @return The y-coordinate of this point
	 */
	public double getY(java.awt.Dimension size) {
		return getY(size.getHeight());
	}

	/**
	 * Sets the y-coordinate of this point
	 * 
	 * @param y
	 *            The y-coordinate
	 */
	public void setY(String y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( !super.equals(obj) ) {
			return false;
		}
		if ( !(obj instanceof Point) ) {
			return false;
		}
		Point other = (Point) obj;
		if ( x == null ) {
			if ( other.x != null ) {
				return false;
			}
		} else if ( !x.equals(other.x) ) {
			return false;
		}
		if ( y == null ) {
			if ( other.y != null ) {
				return false;
			}
		} else if ( !y.equals(other.y) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
