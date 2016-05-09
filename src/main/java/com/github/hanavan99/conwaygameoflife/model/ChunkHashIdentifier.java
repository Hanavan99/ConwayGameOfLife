package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Identifier for locating chunks
 * 
 * @author Zach Deibert
 */
public class ChunkHashIdentifier implements IHashIdentifier {
	/**
	 * The x-coordinate of the chunk
	 */
	public int x;
	/**
	 * The y-coordinate of the chunk
	 */
	public int y;
	/**
	 * The player the chunk is for
	 */
	public PlayerHashIdentifier player;

	@Override
	public ISerializable clone() {
		return new ChunkHashIdentifier(x, y, player);
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		x = data.readInt();
		y = data.readInt();
		if ( player == null ) {
			player = new PlayerHashIdentifier("");
		}
		player.load(data);
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		player.save(data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if ( !(obj instanceof ChunkHashIdentifier) ) {
			return false;
		}
		ChunkHashIdentifier other = (ChunkHashIdentifier) obj;
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
		return "ChunkHashIdentifier [x=" + x + ", y=" + y + ", player=" + player + "]";
	}

	/**
	 * Copy constructor
	 * 
	 * @param x
	 *            The x-coordinate of the chunk
	 * @param y
	 *            The y-coordinate of the chunk
	 * @param player
	 *            The player identifier
	 */
	public ChunkHashIdentifier(int x, int y, PlayerHashIdentifier player) {
		this.x = x;
		this.y = y;
		this.player = player;
	}

	/**
	 * Default constructor
	 * 
	 * @param chunk
	 *            The chunk
	 */
	public ChunkHashIdentifier(Chunk chunk) {
		this(chunk.getX(), chunk.getY(), new PlayerHashIdentifier(chunk.getPlayer()));
	}
}
