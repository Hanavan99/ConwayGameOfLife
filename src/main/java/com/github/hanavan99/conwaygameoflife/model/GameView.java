package com.github.hanavan99.conwaygameoflife.model;

/**
 * Allows code to access the field as a whole, not just in chunk parts.
 * 
 * @author Zach Deibert
 */
public class GameView {
	private final Game game;

	/**
	 * Gets the player at a tile
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @return The player who has a cell at the tile, or null if the tile is
	 *         dead
	 */
	public Player getTilePlayer(int x, int y) {
		int cx = x / 64;
		int cy = y / 64;
		int sx = x % 64;
		int sy = y % 64;
		for ( Chunk chunk : game.getChunks() ) {
			if ( chunk.getX() == cx && chunk.getY() == cy && chunk.getDataBit(sx, sy) ) {
				return chunk.getPlayer();
			}
		}
		return null;
	}

	/**
	 * Sets the player at a tile
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @param p
	 *            The player who has a cell at the tile
	 * @param alive
	 *            True if the cell is alive, false otherwise
	 */
	public void setTile(int x, int y, Player p, boolean alive) {
		int cx = x / 64;
		int cy = y / 64;
		int sx = x % 64;
		int sy = y % 64;
		for ( Chunk chunk : game.getChunks() ) {
			if ( chunk.getX() == cx && chunk.getY() == cy && chunk.getPlayer().equals(p) ) {
				chunk.setDataBit(sx, sy, alive);
				return;
			}
		}
	}

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
