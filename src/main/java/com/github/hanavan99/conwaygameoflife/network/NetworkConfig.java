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
	/**
	 * The number of generations to require in a challenge
	 */
	static final int CHALLENGE_GENERATIONS = 100;
	/**
	 * The square root of the number of chunks to send in a challenge. The
	 * challenge will be a square with this value as its side length.
	 */
	static final int CHALLENGE_CHUNK_SQRT = 10;
	/**
	 * The tolerance for simulated generations. Each generation must be done in
	 * approximately the challenged amount of time minus this tolerance or less.
	 */
	static final int SIMULATOR_PERIOD_TOLERANCE = 10;
}
