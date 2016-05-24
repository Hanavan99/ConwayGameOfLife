package com.github.hanavan99.conwaygameoflife.network.packets;

import com.github.hanavan99.conwaygameoflife.model.ConnectionState;
import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

public class SwitchServerPacketTest extends AbstractPacketTest {
	@Override
	protected IPacket packetA() {
		return new SwitchServerPacket(new ServerInfo(false, "127.0.0.1", 4242, ConnectionState.Ready));
	}

	@Override
	protected IPacket packetB() {
		return new SwitchServerPacket(new ServerInfo(false, "0.0.0.0", 4242, ConnectionState.Ready));
	}
}
