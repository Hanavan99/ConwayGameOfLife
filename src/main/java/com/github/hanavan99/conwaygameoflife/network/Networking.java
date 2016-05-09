package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.ConnectionState;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.ServerInfo;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;

/**
 * The networking protocol used by the game.
 * 
 * @author Zach Deibert
 */
public class Networking extends Thread {
	private static final Logger log = LogManager.getLogger();
	private final Game game;
	private ServerInfo reconnect;

	/**
	 * Gets the game model
	 * 
	 * @return The game model
	 */
	Game getGame() {
		return game;
	}

	/**
	 * Gets the server to reconnect to after the connection fails
	 * 
	 * @return The server
	 */
	ServerInfo getReconnect() {
		return reconnect;
	}

	/**
	 * Sets the server to reconnect to after the connection fails
	 * 
	 * @param reconnect
	 *            The server
	 */
	void setReconnect(ServerInfo reconnect) {
		this.reconnect = reconnect;
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
			try {
				if ( server.isServer() ) {
					new NetworkServer(this);
				} else {
					NetworkClient client = new NetworkClient(this, new ClientDataHandler(game, this));
					client.send(new HelloPacket());
					client.run();
				}
			} catch ( final IOException ex ) {
				log.catching(ex);
			}
			if ( reconnect != null ) {
				server.setIp(reconnect.getIp());
				server.setPort(reconnect.getPort());
			}
			// After the constructors return, the connection is closed
			server.setState(ConnectionState.Disconnected);
			// Wait for the server to become ready again unless a reconnect was
			// requested
			if ( reconnect == null ) {
				while ( server.getState() == ConnectionState.Disconnected ) {
					Thread.yield();
				}
			} else {
				server.setState(ConnectionState.Connecting);
				reconnect = null;
			}
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game to network for
	 */
	public Networking(Game game) {
		super("Networking");
		this.game = game;
	}
}
