package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * The server with a TCP listener
 * 
 * @author Zach Deibert
 */
class NetworkServer {
	private static final Logger log = LogManager.getLogger();

	/**
	 * Default constructor
	 * 
	 * @param net
	 *            The networking manager
	 * @throws IOException
	 *             if an error occurs while connecting to the server
	 */
	NetworkServer(Networking net) throws IOException {
		ServerInfo server = net.getGame().getServer();
		ServerDataHandler handler = new ServerDataHandler();
		try ( ServerSocket sock = new ServerSocket(server.getPort(), 1, InetAddress.getByName(server.getIp()))) {
			log.info("Server started on port {}", server.getPort());
			while ( true ) {
				NetworkClient client = new NetworkClient(net, sock.accept(), handler);
				client.send("Hello, world!".getBytes());
			}
		}
	}
}
