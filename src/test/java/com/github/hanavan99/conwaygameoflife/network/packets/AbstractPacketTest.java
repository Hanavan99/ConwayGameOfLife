package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractPacketTest {
	protected abstract IPacket packetA();
	protected abstract IPacket packetB();
	
	protected void checkInputs(IPacket a, IPacket b) {
		Assert.assertNotNull(a);
		Assert.assertNotNull(b);
		Assert.assertNotSame(a, b);
		Assert.assertNotEquals(a, b);
	}
	
	protected <T> List<T> asList(@SuppressWarnings("unchecked") T... objs) {
		List<T> list = new ArrayList<T>();
		for ( T obj : objs ) {
			list.add(obj);
		}
		return list;
	}
	
	private void testSerialization(IPacket a, IPacket b) throws IOException {
		checkInputs(a, b);
		try ( ByteArrayOutputStream out = new ByteArrayOutputStream() ) {
			try ( DataOutputStream data = new DataOutputStream(out) ) {
				a.save(data);
			}
			try ( ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray()) ) {
				try ( DataInputStream data = new DataInputStream(in) ) {
					b.load(data);
				}
			}
		}
		Assert.assertEquals(a, b);
	}
	
	@Test
	public void testSerialization() throws IOException {
		testSerialization(packetA(), packetB());
		testSerialization(packetB(), packetA());
	}
	
	private void testCloning(IPacket template) {
		Assert.assertNotNull(template);
		IPacket clone = template.clone();
		Assert.assertEquals(template, clone);
		Assert.assertNotSame(template, clone);
	}
	
	@Test
	public void testCloning() {
		testCloning(packetA());
		testCloning(packetB());
	}
	
	@Test
	public void testHashCode() {
		IPacket a = packetA();
		IPacket b = packetB();
		checkInputs(a, b);
		Assert.assertEquals(a.hashCode(), a.hashCode());
		Assert.assertEquals(b.hashCode(), b.hashCode());
		Assert.assertEquals(a.hashCode() == b.hashCode(), a.equals(b));
		Assert.assertEquals(a.hashCode(), a.clone().hashCode());
		Assert.assertEquals(b.hashCode(), b.clone().hashCode());
	}
	
	private void testToString(IPacket packet) {
		Assert.assertTrue(packet.toString().matches(packet.getClass().getSimpleName().concat(" \\[.*\\]")));
	}
	
	@Test
	public void testToString() {
		testToString(packetA());
		testToString(packetB());
	}
}
