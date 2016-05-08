package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.network.NetworkConfig;

/**
 * A packet that informs the other node what protocol version is running.
 * 
 * @author Zach Deibert
 */
public class HelloPacket implements IPacket {
	public int version;

	@Override
	public void load(DataInputStream data) throws IOException {
		version = data.readInt();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(version);
	}

	@Override
	public HelloPacket clone() {
		return new HelloPacket(version);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + version;
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
		if ( !(obj instanceof HelloPacket) ) {
			return false;
		}
		HelloPacket other = (HelloPacket) obj;
		if ( version != other.version ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "HelloPacket [version=" + version + "]";
	}

	/**
	 * Initializes this packet with the default values
	 */
	public HelloPacket() {
		version = NetworkConfig.PROTOCOL_VERSION;
	}

	/**
	 * Initializes this packet with a specific version
	 * 
	 * @param version
	 *            The protocol version
	 */
	public HelloPacket(int version) {
		this.version = version;
	}
}
