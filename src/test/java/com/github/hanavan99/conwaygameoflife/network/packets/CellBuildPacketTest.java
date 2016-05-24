package com.github.hanavan99.conwaygameoflife.network.packets;

import java.awt.Color;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Player;

public class CellBuildPacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new CellBuildPacket(asList(new Chunk(new Player("Nobdy", Color.BLACK, 0, 0), 0, 0)));
	}

	@Override
	protected IPacket packetB() {
		return new CellBuildPacket(asList(new Chunk(new Player("Someone", Color.BLACK, 0, 0), 0, 1)));
	}
}
