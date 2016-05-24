package com.github.hanavan99.conwaygameoflife.network.packets;

import com.github.hanavan99.conwaygameoflife.model.Chunk;

/**
 * Reimages a chunk on a client
 * 
 * @author Zach Deibert
 */
public class ChunkImagePacket extends AbstractSerializableImagingPacket<Chunk> {
	@Override
	protected Chunk defaultObject() {
		return new Chunk();
	}

	@Override
	public AbstractImagingPacket<Chunk> clone() {
		return new ChunkImagePacket(obj == null ? null : obj.clone(), deleted);
	}

	/**
	 * Default constructor
	 * 
	 * @param chunk
	 *            The chunk to reimage
	 * @param deleted
	 *            If this object should be deleted instead of reimaged
	 */
	public ChunkImagePacket(Chunk chunk, boolean deleted) {
		obj = chunk;
		this.deleted = deleted;
	}
}
