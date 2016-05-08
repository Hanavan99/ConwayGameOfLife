package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.LoginPacket;

/**
 * Handles data for the server from a client
 * 
 * @author Zach Deibert
 */
class ServerDataHandler implements IDataHandler {
	private final Game game;
	private final NetworkServer server;

	@Override
	public void handle(IPacket packet, NetworkClient client) throws IOException {
		if ( packet instanceof HelloPacket ) {
			client.send(new HelloPacket());
		} else if ( packet instanceof LoginPacket ) {
			game.getPlayers().add(((LoginPacket) packet).player);
			try ( ThrowingConsumer<NetworkClient> action = new ThrowingConsumer<NetworkClient>(c -> c.send(packet))) {
				server.forEach(action);
			}
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 * @param server
	 *            The server connection
	 */
	public ServerDataHandler(Game game, NetworkServer server) {
		this.game = game;
		this.server = server;
	}
}
