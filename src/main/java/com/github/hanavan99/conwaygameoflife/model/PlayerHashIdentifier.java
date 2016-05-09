package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Identifier for locating players
 * 
 * @author Zach Deibert
 */
public class PlayerHashIdentifier implements IHashIdentifier {
	/**
	 * The name of the player
	 */
	public String name;

	@Override
	public ISerializable clone() {
		return new PlayerHashIdentifier(name);
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		name = data.readUTF();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeUTF(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if ( !(obj instanceof PlayerHashIdentifier) ) {
			return false;
		}
		PlayerHashIdentifier other = (PlayerHashIdentifier) obj;
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		} else if ( !name.equals(other.name) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PlayerHashIdentifier [name=" + name + "]";
	}

	/**
	 * Copy constructor
	 * 
	 * @param name
	 *            The name of the player
	 */
	public PlayerHashIdentifier(String name) {
		this.name = name;
	}

	/**
	 * Default constructor
	 * 
	 * @param player
	 *            The player
	 */
	public PlayerHashIdentifier(Player player) {
		this(player.getName());
	}
}
