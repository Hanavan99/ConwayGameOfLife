package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.hanavan99.conwaygameoflife.model.Chunk;

/**
 * A packet to inform the clients a player has build with some cells.
 * 
 * @author Zach Deibert
 */
public class CellBuildPacket implements IPacket {
	/**
	 * The list of chunks that were built
	 */
	public List<Chunk> chunks;

	@Override
	public void load(DataInputStream data) throws IOException {
		if ( chunks == null ) {
			chunks = new ArrayList<Chunk>();
		} else {
			chunks.clear();
		}
		int len = data.readInt();
		for ( int i = 0; i < len; ++i ) {
			Chunk chunk = new Chunk();
			chunk.load(data);
			chunks.add(chunk);
		}
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(chunks.size());
		for ( Chunk chunk : chunks ) {
			chunk.save(data);
		}
	}

	@Override
	public IPacket clone() {
		List<Chunk> list = new ArrayList<Chunk>();
		if ( chunks != null ) {
			for ( Chunk chunk : chunks ) {
				list.add(chunk.clone());
			}
		}
		return new CellBuildPacket(list);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chunks == null) ? 0 : chunks.hashCode());
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
		if ( !(obj instanceof CellBuildPacket) ) {
			return false;
		}
		CellBuildPacket other = (CellBuildPacket) obj;
		if ( chunks == null ) {
			if ( other.chunks != null ) {
				return false;
			}
		} else if ( !chunks.equals(other.chunks) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CellBuildPacket [chunks=" + chunks + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param chunks
	 *            The list of chunks that were built
	 */
	public CellBuildPacket(List<Chunk> chunks) {
		this.chunks = chunks;
	}
}
