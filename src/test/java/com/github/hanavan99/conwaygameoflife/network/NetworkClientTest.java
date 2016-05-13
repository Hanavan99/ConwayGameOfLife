package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;

public class NetworkClientTest {
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

	@Test
	public void testSend() throws IOException, InterruptedException {
		clientPacket = null;
		serverPacket = null;
		IPacket packet = new HelloPacket(42);
		client.send(packet);
		clientOut.flush();
		Assert.assertNull(clientPacket);
		Assert.assertEquals(packet, serverPacket);
		Assert.assertNotSame(packet, serverPacket);
		IPacket oldSent = serverPacket;
		serverPacket = null;
		server.send(packet);
		serverOut.flush();
		Assert.assertNull(serverPacket);
		Assert.assertEquals(packet, clientPacket);
		Assert.assertNotSame(packet, serverPacket);
		Assert.assertNotSame(oldSent, clientPacket);
	}
}
