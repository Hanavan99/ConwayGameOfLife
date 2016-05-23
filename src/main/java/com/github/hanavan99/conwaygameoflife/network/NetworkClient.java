package com.github.hanavan99.conwaygameoflife.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.ServerInfo;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.PacketFactory;

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
	private final IDataHandler handler;

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
			log.trace("Writing compressed packet of length {}", data.length);
			out.writeBoolean(true);
			try ( GZIPOutputStream gz = new GZIPOutputStream(out)) {
				gz.write(data);
			}
		} else {
			log.trace("Writing raw packet of length {}", data.length);
			out.writeBoolean(false);
			out.write(data);
		}
		out.flush();
		log.trace("Done writing");
	}

	/**
	 * Writes data to the server
	 * 
	 * @param packet
	 *            The data
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void send(IPacket packet) throws IOException {
		try ( ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			try ( DataOutputStream data = new DataOutputStream(buffer)) {
				data.writeByte(PacketFactory.getId(packet));
				packet.save(data);
			}
			send(buffer.toByteArray());
		} catch ( ClassNotFoundException ex ) {
			throw new IOException(ex);
		}
	}

	/**
	 * Runs the reading loop for the client
	 */
	@Override
	public void run() {
		try {
			while ( sock.isConnected() ) {
				boolean isCompressed = in.readBoolean();
				if ( isCompressed ) {
					log.trace("Reading compressed packet");
					GZIPInputStream gz = new GZIPInputStream(in);
					handleMessage(gz);
				} else {
					log.trace("Reading raw packet");
					handleMessage(in);
				}
				log.trace("Packet read");
			}
		} catch ( IOException ex ) {
			log.catching(ex);
			try {
				sock.close();
			} catch ( IOException e ) {
				log.catching(e);
			}
		}
		log.debug("Socket disconnected");
	}

	private void handleMessage(InputStream in) throws IOException {
		DataInputStream data;
		if ( in instanceof DataInputStream ) {
			data = (DataInputStream) in;
		} else {
			data = new DataInputStream(in);
		}
		byte id = data.readByte();
		IPacket packet = PacketFactory.construct(id);
		log.trace("Loading data from packet id {}", id);
		packet.load(data);
		log.trace("Handling message");
		handler.handle(packet, this);
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
	 * @param handler
	 *            The class to send the received packets to to handle
	 * @throws IOException
	 *             if an error occurs while connecting to the server
	 */
	NetworkClient(Networking net, Socket sock, IDataHandler handler) throws IOException {
		this.sock = sock;
		in = new DataInputStream(new BufferedInputStream(sock.getInputStream(), NetworkConfig.INPUT_BUFFER_SIZE));
		out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream(), NetworkConfig.OUTPUT_BUFFER_SIZE));
		this.handler = handler;
	}

	/**
	 * Default constructor
	 * 
	 * @param net
	 *            The networking manager
	 * @param handler
	 *            The class to send the received packets to to handle
	 * @throws IOException
	 *             if an error occurs while connecting to the server
	 */
	NetworkClient(Networking net, IDataHandler handler) throws IOException {
		this(net, createSocket(net), handler);
	}
}
