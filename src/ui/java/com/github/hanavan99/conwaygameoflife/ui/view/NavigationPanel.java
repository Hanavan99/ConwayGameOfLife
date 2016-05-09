package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel{

	private static final long serialVersionUID = 6549683888585429996L;

	private JButton mainPanelButton = new JButton("Main Menu");
	
	public NavigationPanel() {
		setBackground(new Color(0, 125, 0));
		add(mainPanelButton);
		mainPanelButton.addActionListener((ActionEvent e) -> {
			PanelManager.addPanel("mainmenu", new MainPanel());
			PanelManager.showPanel("mainmenu");
		});
	}

}
