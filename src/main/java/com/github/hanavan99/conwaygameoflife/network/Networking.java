package com.github.hanavan99.conwaygameoflife.network;

import com.github.hanavan99.conwaygameoflife.model.ConnectionState;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * The networking protocol used by the game.
 * 
 * @author Zach Deibert
 */
public class Networking extends Thread {
	private final Game game;

	/**
	 * Gets the game model
	 * 
	 * @return The game model
	 */
	Game getGame() {
		return game;
	}

	@Override
	public void run() {
		final ServerInfo server = game.getServer();
		while ( true ) {
			// Wait for a connection to start
			while ( server.getState() == ConnectionState.Ready ) {
				Thread.yield();
			}
			// Start the correct submodule for what we are required to be
			if ( server.isServer() ) {
				new NetworkServer(this);
			} else {
				new NetworkClient(this);
			}
			// After the constructors return, the connection is closed
			server.setState(ConnectionState.Disconnected);
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game to network for
	 */
	public Networking(Game game) {
		this.game = game;
	}
}
