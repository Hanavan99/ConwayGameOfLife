package com.conway.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.conway.layout.UILookAndFeel;
import com.conway.panels.MainPanel;
import com.conway.panels.PanelManager;

public class Main {
	private static final Logger log = LogManager.getLogger();
	public static JFrame gamewindow;

	public static void main(String[] args) {
		UILookAndFeel.init();
		log.info("Starting game...");
		log.trace("Tracing something");
		log.debug("Debugging someting");
		log.info("Doing something");
		log.warn("Not doing so well at something");
		log.error("Failing to do something");
		log.fatal("Causing all sorts of problems");
		gamewindow = new JFrame("Conway's Game Of Life");
		gamewindow.setLayout(new BorderLayout());
		gamewindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gamewindow.setUndecorated(true);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setVisible(true);
		PanelManager.setParentFrame(gamewindow);
		PanelManager.addPanel("main", new MainPanel());
	}
}
