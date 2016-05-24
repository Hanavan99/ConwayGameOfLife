package com.github.hanavan99.conwaygameoflife.network.packets;

import java.awt.Color;

import com.github.hanavan99.conwaygameoflife.model.Player;

public class PlayerImagePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new PlayerImagePacket(new Player("Nobdy", Color.BLACK, 0, 0), false);
	}

	@Override
	protected IPacket packetB() {
		return new PlayerImagePacket(new Player("Someone", Color.BLACK, -1, 1), true);
	}
}
