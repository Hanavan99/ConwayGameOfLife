package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.Game;

/**
 * A packet that provides a challenge for the client to do to find the speed of
 * the clients. This allows the server to find a simulation rate that all of the
 * clients can handle.
 * 
 * @author Zach Deibert
 */
public class ChallengePacket implements IPacket {
	/**
	 * The game that is being challenged
	 */
	public Game game;

	@Override
	public ChallengePacket clone() {
		return new ChallengePacket(game.clone());
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		if ( game == null ) {
			game = new Game();
		}
		game.load(data);
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		if ( game == null ) {
			new Game().save(data);
		} else {
			game.save(data);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
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
		if ( !(obj instanceof ChallengePacket) ) {
			return false;
		}
		ChallengePacket other = (ChallengePacket) obj;
		if ( game == null ) {
			if ( other.game != null ) {
				return false;
			}
		} else if ( !game.equals(other.game) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ChallengePacket [game=" + game + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game to use in the challenge
	 */
	public ChallengePacket(Game game) {
		this.game = game;
	}
}
