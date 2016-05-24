package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A packet to inform the user of some information
 * 
 * @author Zach Deibert
 */
public class MessagePacket implements IPacket {
	/**
	 * The message to tell the user
	 */
	public String message;

	@Override
	public void load(DataInputStream data) throws IOException {
		message = data.readUTF();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeUTF(message);
	}

	@Override
	public IPacket clone() {
		return new MessagePacket(message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		MessagePacket other = (MessagePacket) obj;
		if ( message == null ) {
			if ( other.message != null )
				return false;
		} else if ( !message.equals(other.message) )
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessagePacket [message=" + message + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param message
	 *            The message to send
	 */
	public MessagePacket(String message) {
		this.message = message;
	}
}
