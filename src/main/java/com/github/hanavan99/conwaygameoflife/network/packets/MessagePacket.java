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
