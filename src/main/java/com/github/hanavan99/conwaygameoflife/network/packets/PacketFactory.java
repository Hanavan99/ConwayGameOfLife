package com.github.hanavan99.conwaygameoflife.network.packets;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for constructing packet objects
 * 
 * @author Zach Deibert
 */
public abstract class PacketFactory {
	private static final Map<Byte, IPacket> packets;
	private static byte lastPacketId;

	private static void register(IPacket packet) {
		byte last = lastPacketId;
		++lastPacketId;
		if ( lastPacketId < 0 || lastPacketId == last ) {
			throw new IllegalStateException("Too many packets have been registered");
		} else if ( packet != null ) {
			packets.put(lastPacketId, packet);
		}
	}

	static {
		packets = new HashMap<Byte, IPacket>();
		lastPacketId = -1;
		register(new HelloPacket());
		register(new LoginPacket(null));
		register(new ChallengePacket(null, 0));
		register(new HashPacket(null, 0, 0));
		register(new ChunkImagePacket(null, false));
		register(new PlayerImagePacket(null, false));
		register(new ChunkListImagePacket(null));
		register(new PlayerListImagePacket(null));
		register(new CellBuildPacket(null));
		register(new SwitchServerPacket(null));
	}

	/**
	 * Creates a new packet from its packet id
	 * 
	 * @param id
	 *            The packet id
	 * @return The new packet
	 */
	public static IPacket construct(byte id) {
		if ( packets.containsKey(id) ) {
			return packets.get(id).clone();
		} else {
			throw new IllegalArgumentException("Packet id not registered");
		}
	}
}
