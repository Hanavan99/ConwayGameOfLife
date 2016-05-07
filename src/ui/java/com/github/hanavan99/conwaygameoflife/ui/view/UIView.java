package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;
import com.github.hanavan99.conwaygameoflife.ui.view.layout.UILookAndFeel;

public class UIView implements Runnable {
	
	@Override
	public void run() {
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
	
	public UIView(UIModel model) {
	}
}
