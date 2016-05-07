package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;

import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * The client with a TCP client
 * 
 * @author Zach Deibert
 */
class NetworkClient {
	private final Socket sock;
	private final InputStream in;
	private final OutputStream out;

	/**
	 * Writes data to the server
	 * 
	 * @param data
	 *            The data
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void send(byte[] data) throws IOException {
		out.write(data);
		out.flush();
	}

	private static Socket createSocket(Networking net) throws IOException {
		ServerInfo server = net.getGame().getServer();
		return new Socket(server.getIp(), server.getPort());
	}

	/**
	 * Creates a new <code>NetworkClient</code> with a custom socket.
	 * 
	 * @param net
	 *            The networking manager
	 * @param sock
	 *            The socket to use for connection to the server
	 * @throws IOException
	 *             if an error occurs while connecting to the server
	 */
	NetworkClient(Networking net, Socket sock) throws IOException {
		this.sock = sock;
		in = new DeflaterInputStream(sock.getInputStream());
		out = new DeflaterOutputStream(sock.getOutputStream());
	}

	/**
	 * Default constructor
	 * 
	 * @param net
	 *            The networking manager
	 * @throws IOException
	 *             if an error occurs while connecting to the server
	 */
	NetworkClient(Networking net) throws IOException {
		this(net, createSocket(net));
	}
}
