package com.conway.main;

import javax.swing.JFrame;

import com.conway.panels.AbstractPanel;
import com.conway.panels.MainPanel;
import com.conway.panels.PanelManager;

public class Main {

	public static JFrame gamewindow;
	
	public static void main(String[] args) {
		AbstractPanel.init();
		gamewindow = new JFrame("Conway's Game Of Life");
		gamewindow.setLayout(null);
		gamewindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setVisible(true);
		PanelManager.setParentFrame(gamewindow);
		PanelManager.addPanel("main", new MainPanel());
	}

}
