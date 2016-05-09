package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The main class to store data about a game.
 * 
 * @author Zach Deibert
 */
public class Game implements ISerializable {
	private final ServerInfo server;
	private final List<Player> players;
	private final List<Chunk> chunks;
	private Game challenge;
	private String message;
	private final List<Chunk> changes;
	private int generationPeriod;
	private Consumer<List<Chunk>> changeAccepted;

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

	/**
	 * Gets the challenge to run
	 * 
	 * @return The challenge game
	 */
	public Game getChallenge() {
		return challenge;
	}

	/**
	 * Sets the challenge to run
	 * 
	 * @param challenge
	 *            The challenge game
	 */
	public void setChallenge(Game challenge) {
		this.challenge = challenge;
	}

	/**
	 * Gets the message to tell the user
	 * 
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message to tell the user
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the list of all built changes
	 * 
	 * @return The list
	 */
	public List<Chunk> getChanges() {
		return changes;
	}

	/**
	 * Gets the time in milliseconds between each generation
	 * 
	 * @return The time
	 */
	public int getGenerationPeriod() {
		return generationPeriod;
	}

	/**
	 * Sets the time in milliseconds between each generation
	 * 
	 * @param generationPeriod
	 *            The time
	 */
	public void setGenerationPeriod(int generationPeriod) {
		this.generationPeriod = generationPeriod;
	}

	/**
	 * Gets the method to run when a change has been accepted. It is implied
	 * that the change be removed from the changes list before this method is
	 * called.
	 * 
	 * @return The method
	 */
	public Consumer<List<Chunk>> getChangeAccepted() {
		return changeAccepted;
	}

	/**
	 * Adds the method to run when a change has been accepted
	 * 
	 * @param changeAccepted
	 *            The method
	 * @see Consumer#andThen(Consumer)
	 */
	public void addChangeAccepted(Consumer<List<Chunk>> changeAccepted) {
		if ( this.changeAccepted == null ) {
			this.changeAccepted = changeAccepted;
		} else {
			this.changeAccepted = this.changeAccepted.andThen(changeAccepted);
		}
	}

	/**
	 * Sets the method to run when a change has been accepted
	 * 
	 * @param changeAccepted
	 *            The method
	 * @see Game#addChangeAccepted(Consumer)
	 */
	public void setChangeAccepted(Consumer<List<Chunk>> changeAccepted) {
		this.changeAccepted = changeAccepted;
	}

	@Override
	public Game clone() {
		Game game = new Game(server.clone(), new ArrayList<Player>(), new ArrayList<Chunk>(), challenge.clone(),
				message, new ArrayList<Chunk>(), generationPeriod, changeAccepted);
		for ( Player player : players ) {
			game.players.add(player.clone());
		}
		for ( Chunk chunk : chunks ) {
			game.chunks.add(chunk.clone());
		}
		for ( Chunk chunk : changes ) {
			game.changes.add(chunk.clone());
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
		if ( data.readBoolean() ) {
			if ( challenge == null ) {
				challenge = new Game();
			}
			challenge.load(data);
		} else {
			challenge = null;
		}
		message = data.readUTF();
		changes.clear();
		len = data.readInt();
		for ( int i = 0; i < len; ++i ) {
			Chunk chunk = new Chunk();
			chunk.load(data);
			changes.add(chunk);
		}
		generationPeriod = data.readInt();
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
		if ( challenge == null ) {
			data.writeBoolean(false);
		} else {
			data.writeBoolean(true);
			challenge.save(data);
		}
		data.writeUTF(message);
		data.writeInt(changes.size());
		for ( Chunk chunk : changes ) {
			chunk.save(data);
		}
		data.writeInt(generationPeriod);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((challenge == null) ? 0 : challenge.hashCode());
		result = prime * result + ((changeAccepted == null) ? 0 : changeAccepted.hashCode());
		result = prime * result + ((changes == null) ? 0 : changes.hashCode());
		result = prime * result + ((chunks == null) ? 0 : chunks.hashCode());
		result = prime * result + generationPeriod;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		if ( challenge == null ) {
			if ( other.challenge != null ) {
				return false;
			}
		} else if ( !challenge.equals(other.challenge) ) {
			return false;
		}
		if ( changeAccepted == null ) {
			if ( other.changeAccepted != null ) {
				return false;
			}
		} else if ( !changeAccepted.equals(other.changeAccepted) ) {
			return false;
		}
		if ( changes == null ) {
			if ( other.changes != null ) {
				return false;
			}
		} else if ( !changes.equals(other.changes) ) {
			return false;
		}
		if ( chunks == null ) {
			if ( other.chunks != null ) {
				return false;
			}
		} else if ( !chunks.equals(other.chunks) ) {
			return false;
		}
		if ( generationPeriod != other.generationPeriod ) {
			return false;
		}
		if ( message == null ) {
			if ( other.message != null ) {
				return false;
			}
		} else if ( !message.equals(other.message) ) {
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
		return "Game [server=" + server + ", players=" + players + ", chunks=" + chunks + ", challenge=" + challenge
				+ ", message=" + message + ", changes=" + changes + ", generationPeriod=" + generationPeriod
				+ ", changeAccepted=" + changeAccepted + "]";
	}

	/**
	 * Creates a new instance of the <code>Game</code> model class.
	 */
	public Game() {
		server = new ServerInfo();
		players = new ArrayList<Player>();
		chunks = new ArrayList<Chunk>();
		changes = new ArrayList<Chunk>();
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
	 * @param challenge
	 *            The challenge to run
	 * @param message
	 *            The message to tell the user
	 * @param changes
	 *            The list of built changes
	 * @param generationPeriod
	 *            The time in milliseconds between each generation
	 * @param changeAccepted
	 *            The method to call when a change is accepted
	 */
	public Game(ServerInfo server, List<Player> players, List<Chunk> chunks, Game challenge, String message,
			List<Chunk> changes, int generationPeriod, Consumer<List<Chunk>> changeAccepted) {
		this.server = server;
		this.players = players;
		this.chunks = chunks;
		this.challenge = challenge;
		this.message = message;
		this.changes = changes;
		this.generationPeriod = generationPeriod;
		this.changeAccepted = changeAccepted;
	}
}
