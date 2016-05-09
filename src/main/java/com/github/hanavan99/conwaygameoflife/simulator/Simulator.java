package com.github.hanavan99.conwaygameoflife.simulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.ConnectionState;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.model.ServerInfo;

/**
 * Simulates a game and its challenges and handles changes to the game model
 * 
 * @author Zach Deibert
 */
public class Simulator extends Thread {
	private static final Logger log = LogManager.getLogger();
	private final Game game;
	private final GameSimulator main;
	private GameSimulator challenge;
	private Game challengeGame;

	@Override
	public void run() {
		try {
			final ServerInfo server = game.getServer();
			while ( true ) {
				// Wait for the server to connect before doing any simulating
				while ( server.getState() != ConnectionState.Connected ) {
					Thread.yield();
				}
				// Main loop
				long lastFrame = System.currentTimeMillis();
				while ( server.getState() == ConnectionState.Connected ) {
					if ( game.getChallenge() == null ) {
						int chunks = main.tick();
						double wait = game.getGenerationPeriod() * chunks;
						long end = (long) (lastFrame + wait);
						long now = System.currentTimeMillis();
						if ( end > now ) {
							Thread.sleep(end - now);
						}
					} else if ( game.getChallenge() == challengeGame ) {
						challenge.tick();
					} else {
						log.info("Starting challenge simulator");
						challengeGame = game.getChallenge();
						challenge = new GameSimulator(challengeGame);
						challenge.tick();
					}
				}
			}
		} catch ( InterruptedException ex ) {
			log.catching(ex);
			log.fatal("Simulator is STOPPING!");
		}
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 */
	public Simulator(Game game) {
		super("Simulator");
		this.game = game;
		main = new GameSimulator(game);
	}
}
