package com.github.hanavan99.conwaygameoflife.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * The client with a TCP client
 * 
 * @author Zach Deibert
 */
class NetworkClient implements Runnable {
	private static final Logger log = LogManager.getLogger();
	private final Socket sock;
	private final DataInputStream in;
	private final DataOutputStream out;

	/**
	 * Writes data to the server
	 * 
	 * @param data
	 *            The data
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void send(byte[] data) throws IOException {
		if ( data.length >= NetworkConfig.MINIMUM_COMPRESSION_SIZE ) {
			try ( ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
				try ( GZIPOutputStream gz = new GZIPOutputStream(out)) {
					gz.write(data);
				}
				byte[] compressed = buffer.toByteArray();
				out.writeBoolean(true);
				out.writeInt(data.length);
				out.write(compressed);
			}
		} else {
			out.writeBoolean(false);
			out.writeInt(data.length);
			out.write(data);
		}
		out.flush();
	}

	/**
	 * Runs the reading loop for the client
	 */
	@Override
	public void run() {
		try {
			while ( sock.isConnected() ) {
				boolean isCompressed = in.readBoolean();
				int len = in.readInt();
				byte[] buffer = new byte[len];
				if ( isCompressed ) {
					GZIPInputStream gz = new GZIPInputStream(in);
					gz.read(buffer);
				} else {
					in.read(buffer);
				}
				handleMessage(buffer);
			}
		} catch ( IOException ex ) {
			log.catching(ex);
			try {
				sock.close();
			} catch ( IOException e ) {
				log.catching(e);
			}
		}
	}
	
	private void handleMessage(byte[] data) {
		
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
		in = new DataInputStream(new BufferedInputStream(sock.getInputStream(), NetworkConfig.INPUT_BUFFER_SIZE));
		out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream(), NetworkConfig.OUTPUT_BUFFER_SIZE));
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
