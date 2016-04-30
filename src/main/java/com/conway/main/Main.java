package com.conway.main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import com.conway.layout.UILookAndFeel;
import com.conway.logging.LogManager;
import com.conway.net.GamePacket;
import com.conway.panels.MainPanel;
import com.conway.panels.PanelManager;
import com.conway.util.Vector2D;

public class Main {
	public static JFrame gamewindow;

	public static void main(String[] args) {
		UILookAndFeel.init();
		LogManager.useLogger(true);
		LogManager.init();
		LogManager.log("debug", "Logger initialized");
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
