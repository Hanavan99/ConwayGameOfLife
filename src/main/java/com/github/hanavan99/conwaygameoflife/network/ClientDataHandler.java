package com.github.hanavan99.conwaygameoflife.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.Player;
import com.github.hanavan99.conwaygameoflife.network.packets.CellBuildPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChallengePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChallengeResponsePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChunkImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChunkListImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.HashPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.HashType;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.KetchupPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.LoginPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.MessagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.PlayerImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.PlayerListImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.SetSpeedPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.SwitchServerPacket;

/**
 * Handles data for a client from the server
 * 
 * @author Zach Deibert
 */
class ClientDataHandler implements IDataHandler {
	private static final Logger log = LogManager.getLogger();
	private final Game game;
	private final Networking net;

	@Override
	public void handle(IPacket packet, NetworkClient client) throws IOException {
		if ( packet instanceof CellBuildPacket ) {
			game.getChanges().addAll(((CellBuildPacket) packet).chunks);
		} else if ( packet instanceof ChallengeResponsePacket ) {
			throw new InvalidPacketException("ChallengeResponsePacket should only be sent to the server");
		} else if ( packet instanceof ChallengePacket ) {
			game.setChallenge(((ChallengePacket) packet).game);
		} else if ( packet instanceof ChunkImagePacket ) {
			Chunk target = ((ChunkImagePacket) packet).obj;
			Chunk old = null;
			for ( Chunk chunk : game.getChunks() ) {
				if ( chunk.getX() == target.getX() && chunk.getY() == target.getY()
						&& chunk.getPlayer().equals(target.getPlayer()) ) {
					old = chunk;
					break;
				}
			}
			if ( old != null ) {
				game.getChunks().remove(old);
			}
			game.getChunks().add(target);
		} else if ( packet instanceof ChunkListImagePacket ) {
			game.getChunks().clear();
			game.getChunks().addAll(((ChunkListImagePacket) packet).obj);
		} else if ( packet instanceof HashPacket ) {
			throw new InvalidPacketException("HashPacket should only be sent to the server");
		} else if ( packet instanceof HelloPacket ) {
			log.info("Connected to server with protocol version {}", ((HelloPacket) packet).version);
			if ( ((HelloPacket) packet).version != NetworkConfig.PROTOCOL_VERSION ) {
				throw new InvalidPacketException("Invalid server protocol version");
			}
		} else if ( packet instanceof KetchupPacket ) {
			throw new InvalidPacketException("KetchupPacket should only be sent to the server");
		} else if ( packet instanceof LoginPacket ) {
			throw new InvalidPacketException("LoginPacket should only be sent to the server");
		} else if ( packet instanceof MessagePacket ) {
			game.setMessage(((MessagePacket) packet).message);
		} else if ( packet instanceof PlayerImagePacket ) {
			Player target = ((PlayerImagePacket) packet).obj;
			Player old = null;
			for ( Player player : game.getPlayers() ) {
				if ( player.getName().equals(target.getName()) ) {
					old = player;
					break;
				}
			}
			if ( ((PlayerImagePacket) packet).deleted ) {
				if ( old != null ) {
					List<Chunk> removed = new ArrayList<Chunk>();
					for ( Chunk chunk : game.getChunks() ) {
						if ( chunk.getPlayer() == old ) {
							removed.add(chunk);
						}
					}
					game.getChunks().removeAll(removed);
					game.getPlayers().remove(old);
				}
			} else if ( old == null ) {
				game.getPlayers().add(target);
			} else {
				for ( Player player : game.getPlayers() ) {
					if ( player.getName().equals(target.getName()) ) {
						try ( ByteArrayOutputStream outBuffer = new ByteArrayOutputStream()) {
							try ( DataOutputStream out = new DataOutputStream(outBuffer)) {
								target.save(out);
							}
							try ( ByteArrayInputStream inBuffer = new ByteArrayInputStream(outBuffer.toByteArray())) {
								try ( DataInputStream in = new DataInputStream(inBuffer)) {
									player.load(in);
								}
							}
						}
					}
				}
			}
		} else if ( packet instanceof PlayerListImagePacket ) {
			game.getPlayers().clear();
			game.getPlayers().addAll(((PlayerListImagePacket) packet).obj);
			if ( game.getChunks().size() > 0 ) {
				client.send(new HashPacket(new ArrayList<Chunk>(), HashType.ChunkLength,
						game.getChunks().get(0).getGeneration()));
			}
		} else if ( packet instanceof SetSpeedPacket ) {
			game.setGenerationPeriod(((SetSpeedPacket) packet).generationPeriod);
		} else if ( packet instanceof SwitchServerPacket ) {
			net.setReconnect(((SwitchServerPacket) packet).server);
			throw new IOException("Switching servers");
		} else {
			throw new InvalidPacketException("Invalid packet type for protocol version");
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 * @param net
	 *            The networking
	 */
	public ClientDataHandler(Game game, Networking net) {
		this.game = game;
		this.net = net;
	}
}
