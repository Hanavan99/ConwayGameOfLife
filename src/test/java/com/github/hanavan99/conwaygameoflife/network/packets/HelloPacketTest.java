package com.github.hanavan99.conwaygameoflife.network.packets;

public class HelloPacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new HelloPacket(42);
	}

	@Override
	protected IPacket packetB() {
		return new HelloPacket(53);
	}
}
