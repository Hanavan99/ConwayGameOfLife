package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class to store data about a game.
 * 
 * @author Zach Deibert
 */
public class Game implements ISerializable {
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
	public Game clone() {
		Game game = new Game(server.clone(), new ArrayList<Player>(), new ArrayList<Chunk>());
		for ( Player player : players ) {
			game.players.add(player.clone());
		}
		for ( Chunk chunk : chunks ) {
			game.chunks.add(chunk.clone());
		}
		return game;
	}

	@Override
	public void load(DataInputStream data) throws IOException {
		server.load(data);
		players.clear();
		int len = data.readInt();
		for ( int i = 0; i < len; ++i ) {
			Player player = new Player();
			player.load(data);
			players.add(player);
		}
		chunks.clear();
		len = data.readInt();
		for ( int i = 0; i < len; ++i ) {
			Chunk chunk = new Chunk();
			chunk.load(data);
			chunks.add(chunk);
		}
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		server.save(data);
		data.writeInt(players.size());
		for ( Player player : players ) {
			player.save(data);
		}
		data.writeInt(chunks.size());
		for ( Chunk chunk : chunks ) {
			chunk.save(data);
		}
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

	/**
	 * Copy constructor
	 * 
	 * @param server
	 *            The server information
	 * @param players
	 *            The list of players
	 * @param chunks
	 *            The list of chunks
	 */
	public Game(ServerInfo server, List<Player> players, List<Chunk> chunks) {
		super();
		this.server = server;
		this.players = players;
		this.chunks = chunks;
	}
}
