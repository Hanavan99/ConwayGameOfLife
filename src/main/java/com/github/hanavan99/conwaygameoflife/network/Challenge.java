package com.github.hanavan99.conwaygameoflife.network;

import java.awt.Color;
import java.util.Random;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.Player;

/**
 * The game model containing the challenge data
 * 
 * @author Zach Deibert
 */
class Challenge extends Game {
	/**
	 * Generates the challenge
	 */
	Challenge() {
		Random rand = new Random();
		Player player = new Player("Challenge", Color.BLACK, 0, 0);
		for ( int x = 0; x < NetworkConfig.CHALLENGE_CHUNK_SQRT; ++x ) {
			for ( int y = 0; y < NetworkConfig.CHALLENGE_CHUNK_SQRT; ++y ) {
				final long[] data = new long[64];
				for ( int i = 0; i < 64; ++i ) {
					data[i] = rand.nextLong();
				}
				getChunks().add(new Chunk(player, x, y, data, 0));
			}
		}
	}
}
