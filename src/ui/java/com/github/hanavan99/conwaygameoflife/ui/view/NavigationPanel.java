package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class NavigationPanel extends AbstractPanel{

	private static final long serialVersionUID = 6549683888585429996L;

	private JButton mainPanelButton;
	
	public NavigationPanel() {
		mainPanelButton = new JButton("Main Menu");
		add(mainPanelButton, "navigation_mainmenu");
  		setLayout(null);
		mainPanelButton.addActionListener((ActionEvent e) -> {
			PanelManager.showPanel("main");
		});
	}

}
