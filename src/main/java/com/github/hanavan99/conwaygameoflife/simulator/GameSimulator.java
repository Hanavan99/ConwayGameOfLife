package com.github.hanavan99.conwaygameoflife.simulator;

import com.github.hanavan99.conwaygameoflife.model.Chunk;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.GameView;
import com.github.hanavan99.conwaygameoflife.model.Player;

/**
 * Simulates a single game model without any challenges
 * 
 * @author Zach Deibert
 */
class GameSimulator {
	private final Game game;
	private final GameView view;

	/**
	 * Simulates one generation of the game
	 * 
	 * @return The number of chunks processed
	 */
	int tick() {
		int processed = 0;
		Game clone = new Game();
		for ( Chunk chunk : game.getChunks() ) {
			clone.getChunks().add(chunk.clone());
		}
		GameView cloneView = new GameView(clone);
		for ( Chunk chunk : clone.getChunks() ) {
			for ( int x = 0; x < 64; ++x ) {
				for ( int y = 0; y < 64; ++y ) {
					int gx = chunk.getX() * 64 + x;
					int gy = chunk.getY() * 64 + y;
					Player[] neighbors = view.getNeighbors(gx, gy);
					if ( view.getTilePlayer(gx, gy) == null ) {
						if ( neighbors.length == 3 ) {
							if ( neighbors[0].equals(neighbors[1]) || neighbors[0].equals(neighbors[2]) ) {
								cloneView.setTile(gx, gy, neighbors[0], true);
							} else if ( neighbors[1].equals(neighbors[2]) ) {
								cloneView.setTile(gx, gy, neighbors[1], true);
							}
						}
					} else if ( neighbors.length < 2 || neighbors.length > 3 ) {
						cloneView.kill(gx, gy);
					}
				}
			}
			chunk.setGeneration(chunk.getGeneration() + 1);
			++processed;
		}
		game.getChunks().clear();
		game.getChunks().addAll(clone.getChunks());
		if ( game.getChunks().size() > 0 ) {
			game.snapshotHashes(game.getChunks().get(0).getGeneration());
		}
		return processed;
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model to simulate
	 */
	GameSimulator(Game game) {
		this.game = game;
		view = new GameView(game);
	}
}
