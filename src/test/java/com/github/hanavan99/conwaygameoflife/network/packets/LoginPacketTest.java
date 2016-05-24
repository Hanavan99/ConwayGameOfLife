package com.github.hanavan99.conwaygameoflife.network.packets;

import java.awt.Color;

import com.github.hanavan99.conwaygameoflife.model.Player;

public class LoginPacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new LoginPacket(new Player("Nobdy", Color.BLACK, 0, 0));
	}

	@Override
	protected IPacket packetB() {
		return new LoginPacket(new Player("Someone", Color.BLACK, -1, 1));
	}
}
