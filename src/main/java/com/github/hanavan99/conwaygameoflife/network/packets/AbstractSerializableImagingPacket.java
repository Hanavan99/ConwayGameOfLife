package com.github.hanavan99.conwaygameoflife.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.hanavan99.conwaygameoflife.model.ISerializable;

/**
 * An abstract base class for all packets that reimage serializable data on a
 * client game model.
 * 
 * @author Zach Deibert
 * @param <T>
 *            The type of data to reimage
 */
abstract class AbstractSerializableImagingPacket<T extends ISerializable> extends AbstractImagingPacket<T> {
	/**
	 * Gets the default value for the object to reimage with
	 * 
	 * @return The default value
	 */
	protected abstract T defaultObject();

	@Override
	public void load(DataInputStream data) throws IOException {
		if ( obj == null ) {
			obj = defaultObject();
		}
		obj.load(data);
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		if ( obj == null ) {
			defaultObject().save(data);
		}
		obj.save(data);
	}
}
