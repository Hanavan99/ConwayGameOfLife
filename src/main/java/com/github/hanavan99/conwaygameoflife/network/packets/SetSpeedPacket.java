package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Sets the speed of the simulator on the clients
 * 
 * @author Zach Deibert
 */
public class SetSpeedPacket implements IPacket {
	/**
	 * The time in milliseconds between each generation
	 */
	public int generationPeriod;

	@Override
	public void load(DataInputStream data) throws IOException {
		generationPeriod = data.readInt();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(generationPeriod);
	}

	@Override
	public IPacket clone() {
		return new SetSpeedPacket(generationPeriod);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + generationPeriod;
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
		if ( !(obj instanceof SetSpeedPacket) ) {
			return false;
		}
		SetSpeedPacket other = (SetSpeedPacket) obj;
		if ( generationPeriod != other.generationPeriod ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SetSpeedPacket [generationPeriod=" + generationPeriod + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param generationPeriod
	 *            The time in milliseconds between each generation
	 */
	public SetSpeedPacket(int generationPeriod) {
		this.generationPeriod = generationPeriod;
	}
}
