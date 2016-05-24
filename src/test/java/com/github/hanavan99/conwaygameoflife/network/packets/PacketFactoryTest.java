package com.github.hanavan99.conwaygameoflife.network.packets;

import java.util.ArrayList;
import java.util.List;

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
}
