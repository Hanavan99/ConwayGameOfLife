package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;

public class NetworkClientTest {
	private static final Logger log = LogManager.getLogger();
	private static final long MAX_LATENCY = 1000;
	private static Game game;
	private static Networking net;
	private static NetworkClient client;
	private static NetworkClient server;
	private static IPacket clientPacket;
	private static IPacket serverPacket;
	private static PipedInputStream clientIn;
	private static PipedInputStream serverIn;
	private static PipedOutputStream clientOut;
	private static PipedOutputStream serverOut;
	private static Thread clientThread;
	private static Thread serverThread;

	@BeforeClass
	public static void setup() throws IOException {
		game = new Game();
		net = new Networking(game);
		clientIn = new PipedInputStream();
		serverIn = new PipedInputStream();
		clientOut = new PipedOutputStream(serverIn);
		serverOut = new PipedOutputStream(clientIn);
		client = new NetworkClient(net, new Socket() {
			@Override
			public InputStream getInputStream() throws IOException {
				return clientIn;
			}

			@Override
			public OutputStream getOutputStream() throws IOException {
				return clientOut;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		}, (packet, c) -> {
			Assert.assertSame(client, c);
			clientPacket = packet;
		});
		server = new NetworkClient(net, new Socket() {
			@Override
			public InputStream getInputStream() throws IOException {
				return serverIn;
			}

			@Override
			public OutputStream getOutputStream() throws IOException {
				return serverOut;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		}, (packet, c) -> {
			Assert.assertSame(server, c);
			serverPacket = packet;
		});
		clientThread = new Thread(client, "Test-Client");
		clientThread.start();
		serverThread = new Thread(server, "Test-Server");
		serverThread.start();
	}

	@AfterClass
	public static void cleanup() throws IOException, InterruptedException {
		log.warn("Closing streams forcefully; expect IOExceptions");
		serverThread.interrupt();
		clientThread.interrupt();
		serverThread.join();
		clientThread.join();
		serverOut.close();
		clientOut.close();
		serverIn.close();
		clientIn.close();
		client = null;
		net = null;
		game = null;
	}
	
	private void testSent(IPacket packet, Supplier<IPacket> received) throws InterruptedException {
		long end = System.currentTimeMillis() + MAX_LATENCY;
		IPacket got;
		while ( (got = received.get()) == null ) {
			Assert.assertTrue("Send timed out", end > System.currentTimeMillis());
			Thread.sleep(1);
		}
		Assert.assertEquals(packet, got);
		Assert.assertNotSame(packet, got);
	}

	@Test
	public void testSend() throws IOException, InterruptedException {
		clientPacket = null;
		serverPacket = null;
		IPacket packet = new HelloPacket(42);
		client.send(packet);
		clientOut.flush();
		testSent(packet, () -> serverPacket);
		Assert.assertNull(clientPacket);
		IPacket oldSent = serverPacket;
		serverPacket = null;
		server.send(packet);
		serverOut.flush();
		testSent(packet, () -> clientPacket);
		Assert.assertNull(serverPacket);
		Assert.assertNotSame(oldSent, clientPacket);
	}
}
