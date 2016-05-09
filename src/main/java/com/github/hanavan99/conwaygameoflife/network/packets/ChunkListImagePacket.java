package com.github.hanavan99.conwaygameoflife.network.packets;

import java.util.List;

import com.github.hanavan99.conwaygameoflife.model.Chunk;

/**
 * Reimages the list of chunks on a client
 * 
 * @author Zach Deibert
 */
public class ChunkListImagePacket extends AbstractSerializableListImagingPacket<Chunk> {
	@Override
	protected Chunk defaultObject() {
		return new Chunk();
	}

	@Override
	protected AbstractSerializableListImagingPacket<Chunk> clone(List<Chunk> o) {
		return new ChunkListImagePacket(o);
	}

	/**
	 * Default constructor
	 * 
	 * @param chunks
	 *            The list of chunks to reimage
	 */
	public ChunkListImagePacket(List<Chunk> chunks) {
		obj = chunks;
	}
}
