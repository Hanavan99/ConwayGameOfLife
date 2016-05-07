package com.github.hanvan99.conwaygameoflife.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.github.hanavan99.conwaygameoflife.ui.view.MainPanel;
import com.github.hanavan99.conwaygameoflife.ui.view.PanelManager;
import com.github.hanavan99.conwaygameoflife.ui.view.layout.UILookAndFeel;

public class UIMain {
	public static void main() {
		UILookAndFeel.init();
		JFrame gamewindow = new JFrame("Conway's Game Of Life");
		gamewindow.setLayout(new BorderLayout());
		gamewindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gamewindow.setUndecorated(true);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setVisible(true);
		PanelManager.setParentFrame(gamewindow);
		PanelManager.addPanel("main", new MainPanel());
	}
}
