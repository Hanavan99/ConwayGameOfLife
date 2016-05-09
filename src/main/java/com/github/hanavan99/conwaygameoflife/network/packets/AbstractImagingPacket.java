package com.github.hanavan99.conwaygameoflife.network.packets;

/**
 * An abstract base class for all packets that reimage data on a client game
 * model.
 * 
 * @author Zach Deibert
 * @param <T>
 *            The type of data to reimage
 */
abstract class AbstractImagingPacket<T> implements IPacket {
	/**
	 * The object to reimage with
	 */
	public T obj;

	@Override
	public abstract AbstractImagingPacket<T> clone();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if ( !(obj instanceof AbstractImagingPacket) ) {
			return false;
		}
		AbstractImagingPacket<?> other = (AbstractImagingPacket<?>) obj;
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
		return getClass().getSimpleName() + " [obj=" + obj + "]";
	}
}
