package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.hanavan99.conwaygameoflife.model.ISerializable;

/**
 * An abstract base class for all packets that reimage a list of serializable
 * data on a client game model.
 * 
 * @author Zach Deibert
 * @param <T>
 *            The type of data in the list to reimage
 */
abstract class AbstractSerializableListImagingPacket<T extends ISerializable> extends AbstractImagingPacket<List<T>> {
	/**
	 * Gets the default value for the object to reimage with
	 * 
	 * @return The default value
	 */
	protected abstract T defaultObject();

	/**
	 * Clones this packet
	 * 
	 * @param o
	 *            The list to use as the new {@link AbstractImagingPacket#obj}.
	 * @return The new packet
	 */
	protected abstract AbstractSerializableListImagingPacket<T> clone(List<T> o);

	@Override
	public void load(DataInputStream data) throws IOException {
		if ( obj == null ) {
			obj = new ArrayList<T>();
		} else {
			obj.clear();
		}
		int len = data.readInt();
		for ( int i = 0; i < len; ++i ) {
			T e = defaultObject();
			e.load(data);
			obj.add(e);
		}
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		data.writeInt(obj.size());
		for ( T e : obj ) {
			e.save(data);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractImagingPacket<List<T>> clone() {
		List<T> list = new ArrayList<T>();
		if ( obj != null ) {
			for ( T e : obj ) {
				list.add((T) e.clone());
			}
		}
		return clone(list);
	}
}
