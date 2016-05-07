package com.github.hanavan99.conwaygameoflife.main;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.ConnectionState;
import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.Networking;
import com.github.hanavan99.conwaygameoflife.simulator.Simulator;

public class Main {
	private static final Logger log = LogManager.getLogger();

	private static void serverMain() {
		final Game game = new Game();
		final Simulator simulator = new Simulator(game);
		final Networking server = new Networking(game);
		simulator.start();
		server.start();
		log.info("All server threads started");
		game.getServer().setServer(true);
		game.getServer().setState(ConnectionState.Connecting);
	}

	private static void clientMain(Method main) throws ReflectiveOperationException {
		main.invoke(null);
	}

	public static void main(String[] args) {
		log.info("Starting game...");
		if ( GraphicsEnvironment.isHeadless() ) {
			log.info("Forcing server mode because the program is running in a headless environment");
			serverMain();
		} else {
			try {
				Class<?> cls = Class.forName("com.github.hanvan99.conwaygameoflife.main.UIMain");
				Method method = cls.getMethod("main");
				log.info("UI source set found; launching client");
				clientMain(method);
			} catch ( final ReflectiveOperationException ex ) {
				log.catching(Level.DEBUG, ex);
				log.info("UI source set not included in jar; launching server");
				serverMain();
			}
		}
	}
}
