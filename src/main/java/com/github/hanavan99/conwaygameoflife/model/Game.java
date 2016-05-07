package com.github.hanavan99.conwaygameoflife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class to store data about a game.
 * 
 * @author Zach Deibert
 */
public class Game implements Cloneable {
	private final ServerInfo server;
	private final List<Player> players;
	private final List<Chunk> chunks;

	/**
	 * Gets the information about the server.
	 * 
	 * @return The server information
	 */
	public ServerInfo getServer() {
		return server;
	}

	/**
	 * Gets a list of players.
	 * 
	 * @return The list of players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Gets a list of chunks.
	 * 
	 * @return The list of chunks
	 */
	public List<Chunk> getChunks() {
		return chunks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chunks == null) ? 0 : chunks.hashCode());
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((server == null) ? 0 : server.hashCode());
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
		if ( !(obj instanceof Game) ) {
			return false;
		}
		Game other = (Game) obj;
		if ( chunks == null ) {
			if ( other.chunks != null ) {
				return false;
			}
		} else if ( !chunks.equals(other.chunks) ) {
			return false;
		}
		if ( players == null ) {
			if ( other.players != null ) {
				return false;
			}
		} else if ( !players.equals(other.players) ) {
			return false;
		}
		if ( server == null ) {
			if ( other.server != null ) {
				return false;
			}
		} else if ( !server.equals(other.server) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Game [server=" + server + ", players=" + players + ", chunks=" + chunks + "]";
	}

	/**
	 * Creates a new instance of the <code>Game</code> model class.
	 */
	public Game() {
		server = new ServerInfo();
		players = new ArrayList<Player>();
		chunks = new ArrayList<Chunk>();
	}
}
