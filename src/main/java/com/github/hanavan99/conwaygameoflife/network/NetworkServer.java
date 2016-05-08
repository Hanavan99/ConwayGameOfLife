package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * The server with a TCP listener
 * 
 * @author Zach Deibert
 */
class NetworkServer {
	private static final Logger log = LogManager.getLogger();
	private final List<NetworkClient> clients;

	/**
	 * Runs an action for every client that is conencted to this server
	 * 
	 * @param action
	 *            The action to run
	 */
	void forEach(Consumer<NetworkClient> action) {
		synchronized ( clients ) {
			for ( NetworkClient client : clients ) {
				action.accept(client);
			}
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param net
	 *            The networking manager
	 * @throws IOException
	 *             if an error occurs while connecting to the server
	 */
	NetworkServer(Networking net) throws IOException {
		Game game = net.getGame();
		ServerInfo server = game.getServer();
		ServerDataHandler handler = new ServerDataHandler(game, this);
		clients = new ArrayList<NetworkClient>();
		try ( ServerSocket sock = new ServerSocket(server.getPort(), 1, InetAddress.getByName(server.getIp()))) {
			log.info("Server started on port {}", server.getPort());
			int clientId = -1;
			while ( true ) {
				NetworkClient client = new NetworkClient(net, sock.accept(), handler);
				new Thread(client, String.format("Client-%d", ++clientId)).start();
				synchronized ( clients ) {
					clients.add(client);
				}
			}
		}
	}
}
