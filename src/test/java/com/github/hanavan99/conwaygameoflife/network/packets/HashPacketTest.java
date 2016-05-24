package com.github.hanavan99.conwaygameoflife.network.packets;

import com.github.hanavan99.conwaygameoflife.model.PlayerHashIdentifier;

public class HashPacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new HashPacket(HashType.ChunkLength, 42, 0, null);
	}

	@Override
	protected IPacket packetB() {
		return new HashPacket(HashType.Player, 53, 1, new PlayerHashIdentifier("Someone"));
	}
}
