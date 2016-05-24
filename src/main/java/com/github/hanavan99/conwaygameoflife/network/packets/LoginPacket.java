package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.Player;

/**
 * A packet that creates a user
 * 
 * @author Zach Deibert
 */
public class LoginPacket implements IPacket {
	/**
	 * The player to create
	 */
	public Player player;

	@Override
	public IPacket clone() {
		return new LoginPacket(player);
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		player.load(data);
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		player.save(data);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		if ( !(obj instanceof LoginPacket) ) {
			return false;
		}
		LoginPacket other = (LoginPacket) obj;
		if ( player == null ) {
			if ( other.player != null ) {
				return false;
			}
		} else if ( !player.equals(other.player) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LoginPacket [player=" + player + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param player
	 *            The player to create
	 */
	public LoginPacket(Player player) {
		super();
		this.player = player;
	}
}
