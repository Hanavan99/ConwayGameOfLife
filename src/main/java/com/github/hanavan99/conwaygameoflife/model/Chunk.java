package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Represents a chunk of tile data.
 * 
 * @author Zach Deibert
 */
public class Chunk implements ISerializable {
	private Player player;
	private int x;
	private int y;
	private long[] data;
	private int generation;

	/**
	 * Gets the player this chunk has data for
	 * 
	 * @return The player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the raw data stored in this chunk
	 * 
	 * @return The raw data
	 */
	public long[] getData() {
		return data;
	}

	/**
	 * Sets the raw data stored in this chunk
	 * 
	 * @param data
	 *            The raw data
	 */
	public void setData(long[] data) {
		this.data = data;
	}

	/**
	 * Gets a data bit at a point. If the bit is true, the cell is alive, and if
	 * it is false, the cell is dead.
	 * 
	 * @param x
	 *            The x-coordinate of the cell
	 * @param y
	 *            The y-coordinate of the cell
	 * @return The state of the cell
	 */
	public boolean getDataBit(int x, int y) {
		if ( x < 0 || x >= 64 ) {
			throw new IllegalArgumentException("x must be [0, 64)");
		}
		if ( y < 0 || y >= 64 ) {
			throw new IllegalArgumentException("y must be [0, 64)");
		}
		return (data[y] & (1 << x)) != 0;
	}

	/**
	 * Sets a data bit at a point. If the bit is true, the cell is alive, and if
	 * it is false, the cell is dead.
	 * 
	 * @param x
	 *            The x-coordinate of the cell
	 * @param y
	 *            The y-coordinate of the cell
	 * @param v
	 *            The state of the cell
	 */
	public void setDataBit(int x, int y, boolean v) {
		if ( x < 0 || x >= 64 ) {
			throw new IllegalArgumentException("x must be [0, 64)");
		}
		if ( y < 0 || y >= 64 ) {
			throw new IllegalArgumentException("y must be [0, 64)");
		}
		if ( v ) {
			data[y] |= (1 << x);
		} else {
			data[y] &= ~(1 << x);
		}
	}

	/**
	 * Gets the x-coordinate of this chunk
	 * 
	 * @return The x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y-coordinate of this chunk
	 * 
	 * @return The y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the generation number this chunk is in
	 * 
	 * @return The generation number
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * Sets the generation number this chunk is in
	 * 
	 * @return The generation number
	 */
	public void setGeneration(int generation) {
		this.generation = generation;
	}

	@Override
	public Chunk clone() {
		return new Chunk(player.clone(), x, y, data.clone(), generation);
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		if ( player == null ) {
			player = new Player();
		}
		player.load(data);
		x = data.readInt();
		y = data.readInt();
		this.data = new long[64];
		for ( int i = 0; i < 64; ++i ) {
			this.data[i] = data.readLong();
		}
		generation = data.readInt();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		if ( player == null ) {
			new Player().save(data);
		} else {
			player.save(data);
		}
		data.writeInt(x);
		data.writeInt(y);
		for ( int i = 0; i < 64; ++i ) {
			data.writeLong(this.data[i]);
		}
		data.writeInt(generation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + generation;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + x;
		result = prime * result + y;
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
		if ( !(obj instanceof Chunk) ) {
			return false;
		}
		Chunk other = (Chunk) obj;
		if ( !Arrays.equals(data, other.data) ) {
			return false;
		}
		if ( generation != other.generation ) {
			return false;
		}
		if ( player == null ) {
			if ( other.player != null ) {
				return false;
			}
		} else if ( !player.equals(other.player) ) {
			return false;
		}
		if ( x != other.x ) {
			return false;
		}
		if ( y != other.y ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Chunk [player=" + player + ", x=" + x + ", y=" + y + ", data=" + Arrays.toString(data) + ", generation="
				+ generation + "]";
	}
	
	/**
	 * Nullary constructor
	 */
	public Chunk() {
		player = new Player();
	}

	/**
	 * Default constructor
	 * 
	 * @param player
	 *            The player this chunk has data for
	 * @param x
	 *            The x-coordinate of this chunk
	 * @param y
	 *            The y-coordinate of this chunk
	 */
	public Chunk(Player player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
		data = new long[64];
	}

	/**
	 * Copy constructor
	 * 
	 * @param player
	 *            The player this chunk has data for
	 * @param x
	 *            The x-coordinate of this chunk
	 * @param y
	 *            The y-coordinate of this chunk
	 * @param data
	 *            The raw data
	 * @param generation
	 *            The generation number
	 */
	public Chunk(Player player, int x, int y, long[] data, int generation) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.data = data;
		this.generation = generation;
	}
}
