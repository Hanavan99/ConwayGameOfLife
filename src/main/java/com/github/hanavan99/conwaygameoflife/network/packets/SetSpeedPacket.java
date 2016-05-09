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
	public double generationPeriod;

	@Override
	public void load(DataInputStream data) throws IOException {
		generationPeriod = data.readDouble();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeDouble(generationPeriod);
	}

	@Override
	public IPacket clone() {
		return new SetSpeedPacket(generationPeriod);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(generationPeriod);
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
		if ( !(obj instanceof SetSpeedPacket) ) {
			return false;
		}
		SetSpeedPacket other = (SetSpeedPacket) obj;
		if ( Double.doubleToLongBits(generationPeriod) != Double.doubleToLongBits(other.generationPeriod) ) {
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
	public SetSpeedPacket(double generationPeriod) {
		this.generationPeriod = generationPeriod;
	}
}
