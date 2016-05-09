package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A packet for the response to the {@link ChallengePacket}. The client should
 * send this packet after it finishes the challenge.
 * 
 * @author Zach Deibert
 */
public class ChallengeResponsePacket implements IPacket {
	/**
	 * The time it took to complete the challenge in milliseconds
	 */
	public double time;

	@Override
	public void load(DataInputStream data) throws IOException {
		time = data.readLong();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeDouble(time);
	}

	@Override
	public IPacket clone() {
		return new ChallengeResponsePacket(time);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(time);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if ( !(obj instanceof ChallengeResponsePacket) ) {
			return false;
		}
		ChallengeResponsePacket other = (ChallengeResponsePacket) obj;
		if ( Double.doubleToLongBits(time) != Double.doubleToLongBits(other.time) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ChallengeResponsePacket [time=" + time + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param time
	 *            The time it took to complete the challenge in milliseconds
	 */
	public ChallengeResponsePacket(double time) {
		this.time = time;
	}
}
