package com.github.hanavan99.conwaygameoflife.network.packets;

import com.github.hanavan99.conwaygameoflife.model.ISerializable;

/**
 * Interface for packet sent across the network.
 * 
 * @author Zach Deibert
 */
public interface IPacket extends ISerializable {
	/**
	 * Clones this packet
	 * 
	 * @return The cloned packet
	 */
	public IPacket clone();
}
