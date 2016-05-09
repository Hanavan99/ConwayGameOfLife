package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A packet the client can send to the server to indicate the client can not
 * catch up with the server's simulator.
 * 
 * @author Zach Deibert
 */
public class KetchupPacket implements IPacket {
	@Override
	public void load(DataInputStream data) throws IOException {
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
	}

	@Override
	public IPacket clone() {
		return new KetchupPacket();
	}

	@Override
	public int hashCode() {
		// final int prime = 31;
		int result = 1;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !(obj instanceof KetchupPacket) ) {
			return false;
		}
		// KetchupPacket other = (KetchupPacket) obj;
		return true;
	}
}
