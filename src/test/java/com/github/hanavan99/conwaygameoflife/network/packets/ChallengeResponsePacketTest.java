package com.github.hanavan99.conwaygameoflife.network.packets;

public class ChallengeResponsePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new ChallengeResponsePacket(42);
	}

	@Override
	protected IPacket packetB() {
		return new ChallengeResponsePacket(53);
	}
}
