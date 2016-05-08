package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Interface for packet sent across the network.
 * 
 * @author Zach Deibert
 */
public interface IPacket extends Cloneable {
	/**
	 * Clones this packet
	 * 
	 * @return The cloned packet
	 */
	public IPacket clone();

	/**
	 * Loads the fields in this packet from a stream
	 * 
	 * @param data
	 *            The stream
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void load(DataInputStream data) throws IOException;

	/**
	 * Saves the fields in this packet to a stream
	 * 
	 * @param data
	 *            The stream
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void save(DataOutputStream data) throws IOException;
}
