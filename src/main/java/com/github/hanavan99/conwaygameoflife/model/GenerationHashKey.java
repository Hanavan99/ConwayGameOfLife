package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The key in a map for generations' hashes
 * 
 * @author Zach Deibert
 */
public class GenerationHashKey implements ISerializable {
	private int generation;
	private IHashIdentifier identifier;

	/**
	 * Gets the generation number for this key set
	 * 
	 * @return The generation number
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * Gets the hash identifier for this key set
	 * 
	 * @return The hash identifier
	 */
	public IHashIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public GenerationHashKey clone() {
		return new GenerationHashKey(generation, identifier);
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		generation = data.readInt();
		identifier = null;
		switch ( data.readByte() ) {
		case 0:
			identifier = new ChunkHashIdentifier(0, 0, null);
			break;
		case 1:
			identifier = new PlayerHashIdentifier("");
			break;
		default:
			throw new IOException("Invalid identifier type");
		}
		if ( identifier != null ) {
			identifier.load(data);
		}
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(generation);
		if ( identifier == null ) {
			return;
		} else if ( identifier instanceof ChunkHashIdentifier ) {
			data.writeByte(0);
		} else if ( identifier instanceof PlayerHashIdentifier ) {
			data.writeByte(1);
		} else {
			throw new IOException("Invalid identifier type");
		}
		identifier.save(data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + generation;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
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
		if ( !(obj instanceof GenerationHashKey) ) {
			return false;
		}
		GenerationHashKey other = (GenerationHashKey) obj;
		if ( generation != other.generation ) {
			return false;
		}
		if ( identifier == null ) {
			if ( other.identifier != null ) {
				return false;
			}
		} else if ( !identifier.equals(other.identifier) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "GenerationHashKey [generation=" + generation + ", identifier=" + identifier + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param generation
	 *            The generation number
	 * @param identifier
	 *            The hash identifier
	 */
	public GenerationHashKey(int generation, IHashIdentifier identifier) {
		this.generation = generation;
		this.identifier = identifier;
	}

	/**
	 * Hashes a chunk
	 * 
	 * @param chunk
	 *            The chunk
	 */
	public GenerationHashKey(Chunk chunk) {
		this(chunk.getGeneration(), new ChunkHashIdentifier(chunk));
	}

	/**
	 * Hashes a player
	 * 
	 * @param player
	 *            The player
	 * @param generation
	 */
	public GenerationHashKey(Player player, int generation) {
		this(generation, new PlayerHashIdentifier(player));
	}
}
