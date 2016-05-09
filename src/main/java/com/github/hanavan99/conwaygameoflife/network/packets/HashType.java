package com.github.hanavan99.conwaygameoflife.network.packets;

/**
 * The type of hash a hash packet contains
 * 
 * @author Zach Deibert
 */
public enum HashType {
	/**
	 * The hash code of a
	 * {@link com.github.hanavan99.conwaygameoflife.model.Chunk} model object.
	 */
	Chunk,
	/**
	 * The hash code of a
	 * {@link com.github.hanavan99.conwaygameoflife.model.Player} model object.
	 */
	Player,
	/**
	 * The number of chunks in the game.
	 */
	ChunkLength,
	/**
	 * The number of players in the game.
	 */
	PlayerLength
}
