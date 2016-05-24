package com.github.hanavan99.conwaygameoflife.network.packets;

public class SetSpeedPacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new SetSpeedPacket(42);
	}

	@Override
	protected IPacket packetB() {
		return new SetSpeedPacket(53);
	}
}
