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
	 * If this object should be deleted instead of reimaged
	 */
	public boolean deleted;

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
		deleted = data.readBoolean();
	}

	@Override
	public void save(DataOutputStream data) throws IOException {
		if ( obj == null ) {
			defaultObject().save(data);
		}
		obj.save(data);
		data.writeBoolean(deleted);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !(obj instanceof AbstractSerializableImagingPacket) ) {
			return false;
		}
		AbstractSerializableImagingPacket<?> other = (AbstractSerializableImagingPacket<?>) obj;
		if ( deleted != other.deleted ) {
			return false;
		}
		if ( this.obj == null ) {
			if ( other.obj != null ) {
				return false;
			}
		} else if ( !this.obj.equals(other.obj) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [obj=" + obj + ", deleted=" + deleted + "]";
	}
}
