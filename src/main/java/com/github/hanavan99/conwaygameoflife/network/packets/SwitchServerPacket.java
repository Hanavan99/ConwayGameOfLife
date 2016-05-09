package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * A packet instructing the client to switch to another server. This can be used
 * when one server is overloaded and the data is shared between multiple
 * servers, or for a main server that assigns different clients to different
 * games, etc. The client should send a lot of {@link HashPacket}s after
 * connecting to the other server to ensure the data was successfully
 * transfered.
 * 
 * @author Zach Deibert
 */
public class SwitchServerPacket implements IPacket {
	/**
	 * The new server to switch to
	 */
	public ServerInfo server;

	@Override
	public void load(DataInputStream data) throws IOException {
		if ( server == null ) {
			server = new ServerInfo();
		}
		server.load(data);
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		server.save(data);
	}

	@Override
	public IPacket clone() {
		return new SwitchServerPacket(server.clone());
	}

	/**
	 * Default constructor
	 * 
	 * @param server
	 *            The new server to switch to
	 */
	public SwitchServerPacket(ServerInfo server) {
		this.server = server;
	}
}
