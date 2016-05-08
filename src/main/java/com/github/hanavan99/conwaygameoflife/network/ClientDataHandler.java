package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.LoginPacket;

/**
 * Handles data for a client from the server
 * 
 * @author Zach Deibert
 */
class ClientDataHandler implements IDataHandler {
	private final Game game;

	@Override
	public void handle(IPacket packet, NetworkClient client) throws IOException {
		if ( packet instanceof LoginPacket ) {
			game.getPlayers().add(((LoginPacket) packet).player);
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 */
	public ClientDataHandler(Game game) {
		this.game = game;
	}
}
