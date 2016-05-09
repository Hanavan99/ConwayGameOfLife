package com.github.hanavan99.conwaygameoflife.model;

import java.awt.Dimension;
import java.awt.Rectangle;

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
	 * Gets the total size of the game in tiles to the nearest chunk
	 * 
	 * @return The size
	 * @see GameView#totalSizeExact()
	 */
	public Rectangle totalSize() {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for ( Chunk chunk : game.getChunks() ) {
			int x = chunk.getX();
			int y = chunk.getY();
			minX = Math.min(minX, x);
			maxX = Math.max(maxX, x);
			minY = Math.min(minY, y);
			maxY = Math.max(maxY, y);
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

	/**
	 * Gets the total size of the game in tiles to the nearest tile
	 * 
	 * @return The size
	 * @see GameView#totalSize()
	 */
	public Rectangle totalSizeExact() {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for ( Chunk chunk : game.getChunks() ) {
			int cx = chunk.getX();
			int cy = chunk.getY();
			if ( cx * 64 < minX ) {
				int x;
				top: for ( x = 0; x < 64; ++x ) {
					for ( int y = 0; y < 64; ++y ) {
						if ( chunk.getDataBit(x, y) ) {
							break top;
						}
					}
				}
				minX = Math.min(minX, cx * 64 + x);
			}
			if ( cx * 64 + 63 > maxX ) {
				int x;
				top: for ( x = 63; x >= 0; --x ) {
					for ( int y = 0; y < 64; ++y ) {
						if ( chunk.getDataBit(x, y) ) {
							break top;
						}
					}
				}
				maxX = Math.min(maxX, cx * 64 + x);
			}
			if ( cy * 64 < minY ) {
				int y;
				top: for ( y = 0; y < 64; ++y ) {
					for ( int x = 0; x < 64; ++x ) {
						if ( chunk.getDataBit(x, y) ) {
							break top;
						}
					}
				}
				minY = Math.min(minY, cy * 64 + y);
			}
			if ( cy * 64 + 63 > maxY ) {
				int y;
				top: for ( y = 63; y >= 0; --y ) {
					for ( int x = 0; x < 64; ++x ) {
						if ( chunk.getDataBit(x, y) ) {
							break top;
						}
					}
				}
				maxY = Math.min(maxY, cy * 64 + y);
			}
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
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
