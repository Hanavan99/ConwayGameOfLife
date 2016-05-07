package com.github.hanavan99.conwaygameoflife.model;

import java.io.Serializable;

/**
 * This class is similar to <code>Point</code>, but it is better.
 * 
 * @author Hanavan Kuhn
 *
 */
public class Vector2D implements Serializable {

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
