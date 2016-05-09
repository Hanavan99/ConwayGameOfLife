package com.github.hanavan99.conwaygameoflife.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The interface for a serializable object
 * 
 * @author Zach Deibert
 */
public interface ISerializable extends Cloneable {
	/**
	 * Clones this object
	 * 
	 * @return The cloned object
	 */
	public ISerializable clone();

	/**
	 * Loads the fields in this object from a stream
	 * 
	 * @param data
	 *            The stream
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void load(DataInputStream data) throws IOException;

	/**
	 * Saves the fields in this object to a stream
	 * 
	 * @param data
	 *            The stream
	 * @throws IOException
	 *             if an i/o error occurs
	 */
	public void save(DataOutputStream data) throws IOException;
}
