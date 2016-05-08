package com.github.hanavan99.conwaygameoflife.network;

import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;

/**
 * Interface for a data handler that will handle all incoming packets for the
 * networking module.
 * 
 * @author Zach Deibert
 */
@FunctionalInterface
interface IDataHandler {
	/**
	 * Handles a packet received by this computer
	 * 
	 * @param packet
	 *            The packet
	 * @param client
	 *            The client that received the packet
	 */
	public void handle(IPacket packet, NetworkClient client);
}
