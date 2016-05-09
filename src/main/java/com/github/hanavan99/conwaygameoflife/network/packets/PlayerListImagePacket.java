package com.github.hanavan99.conwaygameoflife.network.packets;

import java.util.List;

import com.github.hanavan99.conwaygameoflife.model.Player;

/**
 * Reimages the list of players on a client
 * 
 * @author Zach Deibert
 */
public class PlayerListImagePacket extends AbstractSerializableListImagingPacket<Player> {
	@Override
	protected Player defaultObject() {
		return new Player();
	}

	@Override
	protected AbstractSerializableListImagingPacket<Player> clone(List<Player> o) {
		return new PlayerListImagePacket(o);
	}

	/**
	 * Default constructor
	 * 
	 * @param players
	 *            The list of players to reimage
	 */
	public PlayerListImagePacket(List<Player> players) {
		obj = players;
	}
}
