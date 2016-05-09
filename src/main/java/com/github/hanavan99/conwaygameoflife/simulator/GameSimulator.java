package com.github.hanavan99.conwaygameoflife.simulator;

import com.github.hanavan99.conwaygameoflife.model.Game;

/**
 * Simulates a single game model without any challenges
 * 
 * @author Zach Deibert
 */
class GameSimulator {
	private final Game game;

	/**
	 * Simulates one generation of the game
	 * 
	 * @return The number of chunks processed
	 */
	int tick() {
		return 0;
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model to simulate
	 */
	GameSimulator(Game game) {
		this.game = game;
	}
}
