package com.github.hanavan99.conwaygameoflife.network.packets;

import com.github.hanavan99.conwaygameoflife.model.Player;

/**
 * Reimages a player on a client
 * 
 * @author Zach Deibert
 */
public class PlayerImagePacket extends AbstractSerializableImagingPacket<Player> {
	@Override
	protected Player defaultObject() {
		return new Player();
	}

	@Override
	public AbstractImagingPacket<Player> clone() {
		return new PlayerImagePacket(obj);
	}

	/**
	 * Default constructor
	 * 
	 * @param player
	 *            The player to reimage
	 */
	public PlayerImagePacket(Player player) {
		obj = player;
	}
}
