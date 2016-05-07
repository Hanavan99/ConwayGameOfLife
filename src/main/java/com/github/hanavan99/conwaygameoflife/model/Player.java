package com.github.hanavan99.conwaygameoflife.model;

import java.awt.Color;

/**
 * Information about a player who is connected to the server
 * 
 * @author Zach Deibert
 */
public class Player implements Cloneable {
	private String name;
	private Color color;
	private double cells;
	private double score;

	/**
	 * Gets the name of the player
	 * 
	 * @return The player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player
	 * 
	 * @param name
	 *            The player's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the color of the player
	 * 
	 * @return The player's color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the player
	 * 
	 * @param color
	 *            The player's color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Gets the number of cells the player has available for building with
	 * 
	 * @return The number of available cells
	 */
	public double getCells() {
		return cells;
	}

	/**
	 * Sets the number of cells the player has available for building with
	 * 
	 * @param cells
	 *            The number of available cells
	 */
	public void setCells(double cells) {
		this.cells = cells;
	}

	/**
	 * Gets the total score for the player
	 * 
	 * @return The score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Sets the total score for the player
	 * 
	 * @param score
	 *            The score
	 */
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cells);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(score);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !(obj instanceof Player) ) {
			return false;
		}
		Player other = (Player) obj;
		if ( Double.doubleToLongBits(cells) != Double.doubleToLongBits(other.cells) ) {
			return false;
		}
		if ( color == null ) {
			if ( other.color != null ) {
				return false;
			}
		} else if ( !color.equals(other.color) ) {
			return false;
		}
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		} else if ( !name.equals(other.name) ) {
			return false;
		}
		if ( Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + ", cells=" + cells + ", score=" + score + "]";
	}
}
