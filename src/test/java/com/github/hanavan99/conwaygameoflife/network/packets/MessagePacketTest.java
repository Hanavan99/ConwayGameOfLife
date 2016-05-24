package com.github.hanavan99.conwaygameoflife.network.packets;

public class MessagePacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new MessagePacket("Hello, world!");
	}

	@Override
	protected IPacket packetB() {
		return new MessagePacket("Hi, world!");
	}
}
