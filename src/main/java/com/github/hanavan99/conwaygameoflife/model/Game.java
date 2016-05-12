package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.hanavan99.conwaygameoflife.network.NetworkConfig;

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
	private double generationPeriod;
	private Consumer<List<Chunk>> changeAccepted;
	private final Map<GenerationHashKey, Integer> hashes;
	private final Map<Integer, Integer> chunkListHashes;
	private final Map<Integer, Integer> playerListHashes;

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
	public double getGenerationPeriod() {
		return generationPeriod;
	}

	/**
	 * Sets the time in milliseconds between each generation
	 * 
	 * @param generationPeriod
	 *            The time
	 */
	public void setGenerationPeriod(double generationPeriod) {
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

	/**
	 * Takes a snapshot of all of the hashes of all of the objects.
	 * 
	 * @param generation
	 *            The current generation
	 */
	public void snapshotHashes(int generation) {
		chunkListHashes.put(generation, chunks.size());
		playerListHashes.put(generation, players.size());
		for ( Chunk chunk : chunks ) {
			hashes.put(new GenerationHashKey(chunk), chunk.hashCode());
		}
		for ( Player player : players ) {
			hashes.put(new GenerationHashKey(player, generation), player.hashCode());
		}
		List<GenerationHashKey> oldHashes = new ArrayList<GenerationHashKey>();
		List<Integer> oldChunks = new ArrayList<Integer>();
		List<Integer> oldPlayers = new ArrayList<Integer>();
		int oldest = generation - NetworkConfig.KEEP_GENERATIONS;
		for ( GenerationHashKey key : hashes.keySet() ) {
			if ( key.getGeneration() < oldest ) {
				oldHashes.add(key);
			}
		}
		for ( int key : chunkListHashes.keySet() ) {
			if ( key < oldest ) {
				oldChunks.add(key);
			}
		}
		for ( int key : playerListHashes.keySet() ) {
			if ( key < oldest ) {
				oldPlayers.add(key);
			}
		}
		for ( GenerationHashKey key : oldHashes ) {
			hashes.remove(key);
		}
		for ( int key : oldChunks ) {
			chunkListHashes.remove(key);
		}
		for ( int key : oldPlayers ) {
			playerListHashes.remove(key);
		}
	}

	/**
	 * Gets the hash of an object at a specified time
	 * 
	 * @param key
	 *            The key to get the hash from
	 * @return The hash
	 */
	public int getHash(GenerationHashKey key) {
		return hashes.get(key);
	}

	/**
	 * Gets the hash of the list of chunks at a specified time
	 * 
	 * @param generation
	 *            The generation to get the hash from
	 * @return The hash
	 */
	public int getChunkHash(int generation) {
		return chunkListHashes.get(generation);
	}

	/**
	 * Gets the hash of the list of players at a specified time
	 * 
	 * @param generation
	 *            The generation to get the hash from
	 * @return The hash
	 */
	public int getPlayerHash(int generation) {
		return playerListHashes.get(generation);
	}

	/**
	 * Clears the list of hashes
	 */
	public void clearHashes() {
		hashes.clear();
		chunkListHashes.clear();
		playerListHashes.clear();
	}

	@Override
	public Game clone() {
		Game game = new Game(server.clone(), new ArrayList<Player>(), new ArrayList<Chunk>(), challenge.clone(),
				message, new ArrayList<Chunk>(), generationPeriod, changeAccepted,
				new HashMap<GenerationHashKey, Integer>(), new HashMap<Integer, Integer>(),
				new HashMap<Integer, Integer>());
		for ( Player player : players ) {
			game.players.add(player.clone());
		}
		for ( Chunk chunk : chunks ) {
			game.chunks.add(chunk.clone());
		}
		for ( Chunk chunk : changes ) {
			game.changes.add(chunk.clone());
		}
		for ( GenerationHashKey key : hashes.keySet() ) {
			game.hashes.put(key.clone(), hashes.get(key));
		}
		for ( int key : chunkListHashes.keySet() ) {
			game.chunkListHashes.put(key, chunkListHashes.get(key));
		}
		for ( int key : playerListHashes.keySet() ) {
			game.playerListHashes.put(key, playerListHashes.get(key));
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
		generationPeriod = data.readDouble();
		len = data.readInt();
		hashes.clear();
		for ( int i = 0; i < len; ++i ) {
			GenerationHashKey key = new GenerationHashKey(0, null);
			key.load(data);
			int value = data.readInt();
			hashes.put(key, value);
		}
		len = data.readInt();
		chunkListHashes.clear();
		for ( int i = 0; i < len; ++i ) {
			int key = data.readInt();
			int value = data.readInt();
			chunkListHashes.put(key, value);
		}
		len = data.readInt();
		playerListHashes.clear();
		for ( int i = 0; i < len; ++i ) {
			int key = data.readInt();
			int value = data.readInt();
			playerListHashes.put(key, value);
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
		data.writeDouble(generationPeriod);
		data.writeInt(hashes.size());
		for ( GenerationHashKey key : hashes.keySet() ) {
			key.save(data);
			data.writeInt(hashes.get(key));
		}
		data.writeInt(chunkListHashes.size());
		for ( int key : chunkListHashes.keySet() ) {
			data.writeInt(key);
			data.writeInt(chunkListHashes.get(key));
		}
		data.writeInt(playerListHashes.size());
		for ( int key : playerListHashes.keySet() ) {
			data.writeInt(key);
			data.writeInt(playerListHashes.get(key));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((challenge == null) ? 0 : challenge.hashCode());
		result = prime * result + ((changeAccepted == null) ? 0 : changeAccepted.hashCode());
		result = prime * result + ((changes == null) ? 0 : changes.hashCode());
		result = prime * result + ((chunkListHashes == null) ? 0 : chunkListHashes.hashCode());
		result = prime * result + ((chunks == null) ? 0 : chunks.hashCode());
		long temp;
		temp = Double.doubleToLongBits(generationPeriod);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((hashes == null) ? 0 : hashes.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((playerListHashes == null) ? 0 : playerListHashes.hashCode());
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
		if ( chunkListHashes == null ) {
			if ( other.chunkListHashes != null ) {
				return false;
			}
		} else if ( !chunkListHashes.equals(other.chunkListHashes) ) {
			return false;
		}
		if ( chunks == null ) {
			if ( other.chunks != null ) {
				return false;
			}
		} else if ( !chunks.equals(other.chunks) ) {
			return false;
		}
		if ( Double.doubleToLongBits(generationPeriod) != Double.doubleToLongBits(other.generationPeriod) ) {
			return false;
		}
		if ( hashes == null ) {
			if ( other.hashes != null ) {
				return false;
			}
		} else if ( !hashes.equals(other.hashes) ) {
			return false;
		}
		if ( message == null ) {
			if ( other.message != null ) {
				return false;
			}
		} else if ( !message.equals(other.message) ) {
			return false;
		}
		if ( playerListHashes == null ) {
			if ( other.playerListHashes != null ) {
				return false;
			}
		} else if ( !playerListHashes.equals(other.playerListHashes) ) {
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
				+ ", changeAccepted=" + changeAccepted + ", hashes=" + hashes + ", chunkListHashes=" + chunkListHashes
				+ ", playerListHashes=" + playerListHashes + "]";
	}

	/**
	 * Creates a new instance of the <code>Game</code> model class.
	 */
	public Game() {
		server = new ServerInfo();
		players = new ArrayList<Player>();
		chunks = new ArrayList<Chunk>();
		changes = new ArrayList<Chunk>();
		hashes = new HashMap<GenerationHashKey, Integer>();
		chunkListHashes = new HashMap<Integer, Integer>();
		playerListHashes = new HashMap<Integer, Integer>();
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
	 * @param hashes
	 *            The map of old hashes
	 * @param chunkListHashes
	 *            The map of old chunk list hashes
	 * @param playerListHashes
	 *            The map of old player list hashes
	 */
	public Game(ServerInfo server, List<Player> players, List<Chunk> chunks, Game challenge, String message,
			List<Chunk> changes, double generationPeriod, Consumer<List<Chunk>> changeAccepted,
			Map<GenerationHashKey, Integer> hashes, Map<Integer, Integer> chunkListHashes,
			Map<Integer, Integer> playerListHashes) {
		this.server = server;
		this.players = players;
		this.chunks = chunks;
		this.challenge = challenge;
		this.message = message;
		this.changes = changes;
		this.generationPeriod = generationPeriod;
		this.changeAccepted = changeAccepted;
		this.hashes = hashes;
		this.chunkListHashes = chunkListHashes;
		this.playerListHashes = playerListHashes;
	}
}
