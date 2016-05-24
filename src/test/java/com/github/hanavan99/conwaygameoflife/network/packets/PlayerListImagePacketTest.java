package com.github.hanavan99.conwaygameoflife.network.packets;

import java.awt.Color;

import com.github.hanavan99.conwaygameoflife.model.Player;

public class PlayerListImagePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new PlayerListImagePacket(
				asList(new Player("Nobdy", Color.BLACK, 0, 0), new Player("Someone", Color.BLACK, -1, 1)));
	}

	@Override
	protected IPacket packetB() {
		return new PlayerListImagePacket(asList(new Player("Someone", Color.BLACK, -2, 41)));
	}
}
