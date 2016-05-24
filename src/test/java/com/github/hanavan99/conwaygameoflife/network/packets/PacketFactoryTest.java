package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;

import javassist.Modifier;

public class PacketFactoryTest {
	private static final Logger log = LogManager.getLogger();

	@Test
	public void testConstruct() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.addAll(
				new Reflections("com.github.hanavan99.conwaygameoflife.network.packets").getSubTypesOf(IPacket.class));
		for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i ) {
			try {
				IPacket pkt = PacketFactory.construct((byte) i);
				Class<?> cls = pkt.getClass();
				Assert.assertTrue(classes.remove(cls));
			} catch ( IllegalArgumentException ex ) {
				log.catching(Level.DEBUG, ex);
			}
		}
		List<Class<?>> ifaces = new ArrayList<Class<?>>();
		for ( Class<?> cls : classes ) {
			if ( cls.isInterface() || Modifier.isAbstract(cls.getModifiers()) ) {
				ifaces.add(cls);
			}
		}
		classes.removeAll(ifaces);
		if ( classes.size() > 0 ) {
			log.warn("Unregistered packets: {}", classes);
		}
		Assert.assertEquals(0, classes.size());
	}

	@Test
	public void testGetId() throws ClassNotFoundException {
		for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i ) {
			try {
				IPacket pkt = PacketFactory.construct((byte) i);
				Assert.assertEquals(i, PacketFactory.getId(pkt));
			} catch ( IllegalArgumentException ex ) {
				log.catching(Level.DEBUG, ex);
			}
		}
	}

	@Test
	public void testConsistentIds() throws IOException, ClassNotFoundException {
		File store = new File(new File("build"), "PacketFactoryTest.ser").getAbsoluteFile();
		log.info("Storing PacketFactoryTest store in file {}", store);
		Map<Integer, String> current = new HashMap<Integer, String>();
		for ( int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; ++i ) {
			try {
				IPacket pkt = PacketFactory.construct((byte) i);
				current.put(i, pkt.getClass().getName());
			} catch ( IllegalArgumentException ex ) {
				log.catching(Level.DEBUG, ex);
			}
		}
		if ( store.exists() ) {
			try ( FileInputStream in = new FileInputStream(store) ) {
				try ( ObjectInputStream obj = new ObjectInputStream(in) ) {
					@SuppressWarnings("unchecked")
					Map<Integer, String> old = (Map<Integer, String>) obj.readObject();
					for ( int id : old.keySet() ) {
						Assert.assertTrue(current.containsKey(id));
						Assert.assertEquals(old.get(id), current.get(id));
					}
					if ( current.size() == old.size() ) {
						return;
					}
				}
			}
		}
		try ( FileOutputStream out = new FileOutputStream(store) ) {
			try ( ObjectOutputStream obj = new ObjectOutputStream(out) ) {
				obj.writeObject(current);
			}
		}
	}
}
