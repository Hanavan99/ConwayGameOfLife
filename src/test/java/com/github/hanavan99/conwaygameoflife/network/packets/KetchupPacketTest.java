package com.github.hanavan99.conwaygameoflife.network.packets;

import spark.utils.Assert;

public class KetchupPacketTest extends AbstractPacketTest {
	@Override
	protected void checkInputs(IPacket a, IPacket b) {
		Assert.notNull(a);
		Assert.notNull(b);
	}

	@Override
	protected IPacket packetA() {
		return new KetchupPacket();
	}

	@Override
	protected IPacket packetB() {
		return new KetchupPacket();
	}
}
