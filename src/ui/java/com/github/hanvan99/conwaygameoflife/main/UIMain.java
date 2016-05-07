package com.github.hanvan99.conwaygameoflife.main;

import java.awt.EventQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.hanavan99.conwaygameoflife.model.Game;
import com.github.hanavan99.conwaygameoflife.network.Networking;
import com.github.hanavan99.conwaygameoflife.simulator.Simulator;
import com.github.hanavan99.conwaygameoflife.ui.controller.UIController;
import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;
import com.github.hanavan99.conwaygameoflife.ui.view.UIView;

public class UIMain {
	private static final Logger log = LogManager.getLogger();

	public static void main() {
		final Game game = new Game();
		final Simulator simulator = new Simulator(game);
		final Networking client = new Networking(game);
		simulator.start();
		client.start();
		final UIModel model = new UIModel(game);
		final UIView view = new UIView(model);
		final UIController ctrlr = new UIController(model);
		EventQueue.invokeLater(view);
		EventQueue.invokeLater(ctrlr);
		log.info("All client threads started");
	}
}
