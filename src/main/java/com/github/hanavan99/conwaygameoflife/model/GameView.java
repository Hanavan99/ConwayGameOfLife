package com.github.hanavan99.conwaygameoflife.model;

/**
 * Allows code to access the field as a whole, not just in chunk parts.
 * 
 * @author Zach Deibert
 */
public class GameView {
	private final Game game;

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game to use
	 */
	public GameView(Game game) {
		this.game = game;
	}
}
