package com.github.hanavan99.conwaygameoflife.network;

/**
 * Configuration settings for the network module
 * 
 * @author Zach Deibert
 */
public abstract class NetworkConfig {
	/**
	 * The minimum size of a packet before it is compressed
	 */
	static final int MINIMUM_COMPRESSION_SIZE = 256;
	/**
	 * The amount of compression to do on the data
	 */
	static final int COMPRESSION_LEVEL = 9;
	/**
	 * The size of buffer to use for input operations
	 */
	static final int INPUT_BUFFER_SIZE = 4096;
	/**
	 * The size of the buffer to use for output operations
	 */
	static final int OUTPUT_BUFFER_SIZE = 4096;
	/**
	 * The version of protocol this implementation is for
	 */
	public static final int PROTOCOL_VERSION = 1;
}
