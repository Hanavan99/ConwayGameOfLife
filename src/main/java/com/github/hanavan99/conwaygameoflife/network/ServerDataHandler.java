package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;

/**
 * Handles data for the server from a client
 * 
 * @author Zach Deibert
 */
class ServerDataHandler implements IDataHandler {
	private final Game game;
	
	@Override
	public void handle(IPacket packet, NetworkClient client) throws IOException {
		if ( packet instanceof HelloPacket ) {
			client.send(new HelloPacket());
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 */
	public ServerDataHandler(Game game) {
		this.game = game;
	}
}