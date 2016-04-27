package com.conway.util;

/**
 * This class is similar to <code>Point</code>, but it is better.
 * 
 * @author Hanavan Kuhn
 *
 */
public class Vector2D {

	private int x;
	private int y;

	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x-component of this <code>Vector2D</code>
	 * 
	 * @return The x-component
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y-component of this <code>Vector2D</code>
	 * 
	 * @return The y-component
	 */
	public int getY() {
		return y;
	}

}
