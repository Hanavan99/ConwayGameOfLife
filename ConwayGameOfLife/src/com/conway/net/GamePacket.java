package com.conway.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.conway.util.Vector2D;

/**
 * Packet that is sent to update all clients on a server.
 * 
 * @author Hanavan Kuhn
 *
 */
public class GamePacket implements Serializable {

	/**
	 * UID for this object.
	 */
	private static final long serialVersionUID = 5521131528149625075L;

	private Vector2D[] updates;

	/**
	 * Creates a new <code>GamePacket</code> and when sent, will toggle all of
	 * the game pieces that are at the given positions.
	 * 
	 * @param updates The positions to toggle.
	 */
	public GamePacket(Vector2D[] updates) {
		this.updates = updates;
	}
	
	/**
	 * Creates a new <code>GamePacket</code> using serialized data that will be deserialized when read.
	 * @param data The byte array holding the data
	 */
	public GamePacket(byte[] data) {
		try {
			ByteArrayInputStream i = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(i);
			GamePacket p = (GamePacket) in.readObject();
			this.updates = p.updates;
		} catch (Exception e) {
			
		}
	}

	public byte[] serialize() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(out);
			o.writeObject(this);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Vector2D[] getUpdates() {
		return updates;
	}
	
}
