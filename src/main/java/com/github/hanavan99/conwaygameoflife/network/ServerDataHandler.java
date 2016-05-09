package com.github.hanavan99.conwaygameoflife.network;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.Player;
import com.github.hanavan99.conwaygameoflife.network.packets.CellBuildPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChallengePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChallengeResponsePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChunkHashIdentifier;
import com.github.hanavan99.conwaygameoflife.network.packets.ChunkImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.ChunkListImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.HashPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.HelloPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.IPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.LoginPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.MessagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.PlayerHashIdentifier;
import com.github.hanavan99.conwaygameoflife.network.packets.PlayerImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.PlayerListImagePacket;
import com.github.hanavan99.conwaygameoflife.network.packets.SetSpeedPacket;
import com.github.hanavan99.conwaygameoflife.network.packets.SwitchServerPacket;

/**
 * Handles data for the server from a client
 * 
 * @author Zach Deibert
 */
class ServerDataHandler implements IDataHandler {
	private static final Logger log = LogManager.getLogger();
	private final Game game;
	private final NetworkServer server;
	private final Game challenge;

	private void broadcast(IPacket packet) throws IOException {
		try ( ThrowingConsumer<NetworkClient> action = new ThrowingConsumer<>(c -> c.send(packet))) {
			server.forEach(action);
		}
	}

	@Override
	public void handle(IPacket packet, NetworkClient client) throws IOException {
		if ( packet instanceof CellBuildPacket ) {
			game.getChanges().addAll(((CellBuildPacket) packet).chunks);
			// TODO broadcast changes to clients after the changes are accepted
		} else if ( packet instanceof ChallengeResponsePacket ) {
			int generationPeriod = game.getGenerationPeriod();
			int clientPeriod = (int) ((ChallengeResponsePacket) packet).time / (NetworkConfig.CHALLENGE_GENERATIONS
					* NetworkConfig.CHALLENGE_CHUNK_SQRT * NetworkConfig.CHALLENGE_CHUNK_SQRT)
					- NetworkConfig.SIMULATOR_PERIOD_TOLERANCE;
			if ( clientPeriod < generationPeriod ) {
				game.setGenerationPeriod(clientPeriod);
				broadcast(new SetSpeedPacket(clientPeriod));
			}
		} else if ( packet instanceof ChallengePacket ) {
			throw new InvalidPacketException("ChallengePacket should only be sent to the client");
		} else if ( packet instanceof ChunkImagePacket ) {
			throw new InvalidPacketException("ChunkImagePacket should only be sent to the client");
		} else if ( packet instanceof ChunkListImagePacket ) {
			throw new InvalidPacketException("ChunkListImagePacket should only be sent to the client");
		} else if ( packet instanceof HashPacket ) {
			// TODO generation checking
			switch ( ((HashPacket) packet).type ) {
			case Chunk: {
				ChunkHashIdentifier id = (ChunkHashIdentifier) ((HashPacket) packet).identifier;
				Chunk ours = null;
				for ( Chunk chunk : game.getChunks() ) {
					if ( chunk.getX() == id.x && chunk.getY() == id.y
							&& chunk.getPlayer().getName().equals(id.player.name) ) {
						ours = chunk;
						break;
					}
				}
				if ( ours == null ) {
					client.send(
							new ChunkImagePacket(new Chunk(new Player(id.player.name, null, 0, 0), id.x, id.y), true));
				} else if ( ours.hashCode() != ((HashPacket) packet).hash ) {
					client.send(new ChunkImagePacket(ours, false));
				}
			}
				break;
			case ChunkLength:
				if ( ((HashPacket) packet).hash != game.getChunks().size() ) {
					client.send(new ChunkListImagePacket(game.getChunks()));
				}
				break;
			case Player: {
				PlayerHashIdentifier id = (PlayerHashIdentifier) ((HashPacket) packet).identifier;
				Player ours = null;
				for ( Player player : game.getPlayers() ) {
					if ( player.getName().equals(id.name) ) {
						ours = player;
						break;
					}
				}
				if ( ours == null ) {
					client.send(new PlayerImagePacket(new Player(id.name, null, 0, 0), true));
				} else if ( ours.hashCode() != ((HashPacket) packet).hash ) {
					client.send(new PlayerImagePacket(ours, false));
				}
			}
				break;
			case PlayerLength:
				if ( ((HashPacket) packet).hash != game.getPlayers().size() ) {
					client.send(new PlayerListImagePacket(game.getPlayers()));
				}
				break;
			default:
				throw new InvalidPacketException("Invalid hash packet type");
			}
		} else if ( packet instanceof HelloPacket ) {
			log.info("Client connected with protocol {}", ((HelloPacket) packet).version);
			if ( ((HelloPacket) packet).version != NetworkConfig.PROTOCOL_VERSION ) {
				throw new InvalidPacketException("Invalid client protocol version");
			}
			client.send(new HelloPacket());
			client.send(new ChallengePacket(challenge, NetworkConfig.CHALLENGE_GENERATIONS));
		} else if ( packet instanceof LoginPacket ) {
			log.info("Client logged in with name {}", ((LoginPacket) packet).player.getName());
			game.getPlayers().add(((LoginPacket) packet).player);
		} else if ( packet instanceof MessagePacket ) {
			throw new InvalidPacketException("MessagePacket should only be sent to the client");
		} else if ( packet instanceof PlayerImagePacket ) {
			throw new InvalidPacketException("PlayerImagePacket should only be sent to the client");
		} else if ( packet instanceof PlayerListImagePacket ) {
			throw new InvalidPacketException("PlayerListImagePacket should only be sent to the client");
		} else if ( packet instanceof SetSpeedPacket ) {
			throw new InvalidPacketException("SetSpeedPacket should only be sent to the client");
		} else if ( packet instanceof SwitchServerPacket ) {
			throw new InvalidPacketException("SwitchServerPacket should only be sent to the client");
		} else {
			throw new InvalidPacketException("Invalid packet type for protocol version");
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 * @param server
	 *            The server connection
	 */
	public ServerDataHandler(Game game, NetworkServer server) {
		this.game = game;
		this.server = server;
		challenge = new Challenge();
	}
}
