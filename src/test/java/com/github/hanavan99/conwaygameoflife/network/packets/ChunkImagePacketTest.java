package com.github.hanavan99.conwaygameoflife.network.packets;

import java.awt.Color;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Player;

public class ChunkImagePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new ChunkImagePacket(new Chunk(new Player("Nobdy", Color.BLACK, 0, 0), 0, 0), false);
	}

	@Override
	protected IPacket packetB() {
		return new ChunkImagePacket(new Chunk(new Player("Someone", Color.BLACK, 0, 0), -1, 1), true);
	}
}
