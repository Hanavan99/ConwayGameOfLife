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
	private static final Map<Class<?>, Byte> packetIds;
	private static byte lastPacketId;

	private static void register(IPacket packet) {
		byte last = lastPacketId;
		++lastPacketId;
		if ( lastPacketId < 0 || lastPacketId == last ) {
			throw new IllegalStateException("Too many packets have been registered");
		} else if ( packet != null ) {
			packets.put(lastPacketId, packet);
			packetIds.put(packet.getClass(), lastPacketId);
		}
	}

	static {
		packets = new HashMap<Byte, IPacket>();
		packetIds = new HashMap<Class<?>, Byte>();
		lastPacketId = -1;
		register(new HelloPacket());
		register(new LoginPacket(null));
		register(new ChallengePacket(null, 0));
		register(new HashPacket(null, 0, 0, null));
		register(new ChunkImagePacket(null, false));
		register(new PlayerImagePacket(null, false));
		register(new ChunkListImagePacket(null));
		register(new PlayerListImagePacket(null));
		register(new CellBuildPacket(null));
		register(new SwitchServerPacket(null));
		register(new MessagePacket(null));
		register(new SetSpeedPacket(0));
		register(new KetchupPacket());
		register(new ChallengeResponsePacket(0));
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

	/**
	 * Gets the id of a packet by its type
	 * 
	 * @param packet
	 *            The packet to reference
	 * @return The id of the packet
	 * @throws ClassNotFoundException
	 *             if the packet type is not registered with the factory
	 */
	public static byte getId(IPacket packet) throws ClassNotFoundException {
		Byte id = packetIds.get(packet.getClass());
		if ( id == null ) {
			throw new ClassNotFoundException("The packet's class was not registered in the PacketFactory");
		}
		return id;
	}
}
