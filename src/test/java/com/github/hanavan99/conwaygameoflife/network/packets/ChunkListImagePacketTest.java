package com.github.hanavan99.conwaygameoflife.network.packets;

import java.awt.Color;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Player;

public class ChunkListImagePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new ChunkListImagePacket(asList(new Chunk(new Player("Nobdy", Color.BLACK, 0, 0), 0, 0),
				new Chunk(new Player("Nobdy", Color.BLACK, 0, 0), 0, 1)));
	}

	@Override
	protected IPacket packetB() {
		return new ChunkListImagePacket(asList(new Chunk(new Player("Someone", Color.BLACK, 0, 0), -1, 0),
				new Chunk(new Player("Nobdy", Color.BLACK, 0, 0), -1, 1)));
	}
}
