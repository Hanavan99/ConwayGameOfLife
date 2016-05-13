package com.github.hanavan99.conwaygameoflife.ui.model;

/**
 * A dimension in the layout
 * 
 * @author Zach Deibert
 */
public class Dimension extends java.awt.Dimension {
	private static final long serialVersionUID = -5318099953217997321L;
	private String width;
	private String height;

	/**
	 * Gets the width of the dimension
	 * 
	 * @param width
	 *            The width of the parent component
	 * @return The width of this dimension
	 */
	public double getWidth(double width) {
		if ( this.width == null ) {
			return 0;
		} else if ( this.width.endsWith("%") ) {
			return Double.parseDouble(this.width.substring(0, this.width.length() - 1)) / 100. * width;
		} else {
			return Double.parseDouble(this.width);
		}
	}

	/**
	 * Gets the width of the dimension
	 * 
	 * @param size
	 *            The size of the parent component
	 * @return The width of this dimension
	 */
	public double getWidth(java.awt.Dimension size) {
		return getWidth(size.getWidth());
	}

	/**
	 * Sets the width of this dimension
	 * 
	 * @param width
	 *            The width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * Gets the height of the dimension
	 * 
	 * @param height
	 *            The height of the parent component
	 * @return The height of this dimension
	 */
	public double getHeight(double height) {
		if ( this.height == null ) {
			return 0;
		} else if ( this.height.endsWith("%") ) {
			return Double.parseDouble(this.height.substring(0, this.height.length() - 1)) / 100. * height;
		} else {
			return Double.parseDouble(this.height);
		}
	}

	/**
	 * Gets the height of the dimension
	 * 
	 * @param size
	 *            The size of the parent component
	 * @return The height of this dimension
	 */
	public double getHeight(java.awt.Dimension size) {
		return getHeight(size.getHeight());
	}

	/**
	 * Sets the height of this dimension
	 * 
	 * @param height
	 *            The height
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		if ( !(obj instanceof Dimension) ) {
			return false;
		}
		Dimension other = (Dimension) obj;
		if ( height == null ) {
			if ( other.height != null ) {
				return false;
			}
		} else if ( !height.equals(other.height) ) {
			return false;
		}
		if ( width == null ) {
			if ( other.width != null ) {
				return false;
			}
		} else if ( !width.equals(other.width) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Dimension [width=" + width + ", height=" + height + "]";
	}
}
