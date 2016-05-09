package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.ChunkHashIdentifier;
import com.github.hanavan99.conwaygameoflife.model.IHashIdentifier;
import com.github.hanavan99.conwaygameoflife.model.Player;
import com.github.hanavan99.conwaygameoflife.model.PlayerHashIdentifier;

/**
 * A packet containing an object's hash so the server can verify the client has
 * the correct data.
 * 
 * @author Zach Deibert
 */
public class HashPacket implements IPacket {
	/**
	 * The type of hash the object is
	 */
	public HashType type;
	/**
	 * The hash of the object
	 */
	public int hash;
	/**
	 * The generation number the hash was taken at
	 */
	public int generation;
	/**
	 * The identifier for the hash
	 */
	public IHashIdentifier identifier;

	@Override
	public void load(DataInputStream data) throws IOException {
		type = HashType.values()[data.readInt()];
		hash = data.readInt();
		generation = data.readInt();
		switch ( type ) {
		case Chunk:
			identifier = new ChunkHashIdentifier(0, 0, null);
			break;
		case Player:
			identifier = new PlayerHashIdentifier("");
			break;
		default:
			identifier = null;
		}
		if ( identifier != null ) {
			identifier.load(data);
		}
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(type.ordinal());
		data.writeInt(hash);
		data.writeInt(generation);
		switch ( type ) {
		case Chunk:
		case Player:
			identifier.save(data);
			break;
		default:
		}
	}

	@Override
	public IPacket clone() {
		return new HashPacket(type, hash, generation, identifier);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + generation;
		result = prime * result + hash;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if ( !(obj instanceof HashPacket) ) {
			return false;
		}
		HashPacket other = (HashPacket) obj;
		if ( generation != other.generation ) {
			return false;
		}
		if ( hash != other.hash ) {
			return false;
		}
		if ( identifier == null ) {
			if ( other.identifier != null ) {
				return false;
			}
		} else if ( !identifier.equals(other.identifier) ) {
			return false;
		}
		if ( type != other.type ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "HashPacket [type=" + type + ", hash=" + hash + ", generation=" + generation + ", identifier="
				+ identifier + "]";
	}

	/**
	 * Copy constructor
	 * 
	 * @param type
	 *            The type of hash
	 * @param hash
	 *            The hash value
	 * @param generation
	 *            The generation the hash was taken in
	 * @param identifier
	 *            The identifier for the hash
	 */
	public HashPacket(HashType type, int hash, int generation, IHashIdentifier identifier) {
		this.type = type;
		this.hash = hash;
		this.generation = generation;
		this.identifier = identifier;
	}

	/**
	 * Hashes a chunk
	 * 
	 * @param chunk
	 *            The chunk to hash
	 */
	public HashPacket(Chunk chunk) {
		this(chunk, HashType.Chunk, chunk.getGeneration(), new ChunkHashIdentifier(chunk));
	}

	/**
	 * Hashes a player
	 * 
	 * @param player
	 *            The player to hash
	 * @param generation
	 *            The generation the hash was taken in
	 */
	public HashPacket(Player player, int generation) {
		this(player, HashType.Player, generation, new PlayerHashIdentifier(player));
	}

	/**
	 * Hashes a list
	 * 
	 * @param list
	 *            The list to hash
	 * @param type
	 *            The type of hash to perform
	 * @param generation
	 *            The generation the hash was taken in
	 */
	public HashPacket(List<?> list, HashType type, int generation) {
		this(type, list.size(), generation, null);
	}

	/**
	 * Hashes an object
	 * 
	 * @param o
	 *            The object to hash
	 * @param type
	 *            The type of hash to perform
	 * @param generation
	 *            The generation the hash was taken in
	 * @param identifier
	 *            The identifier for the hash
	 */
	public HashPacket(Object o, HashType type, int generation, IHashIdentifier identifier) {
		this(type, o.hashCode(), generation, identifier);
	}
}
