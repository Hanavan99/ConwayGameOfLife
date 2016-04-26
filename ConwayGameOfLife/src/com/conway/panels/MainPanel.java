package com.conway.panels;

import javax.swing.JLabel;

/**
 * The main menu panel.
 * 
 * @author Hanavan Kuhn
 *
 */
public class MainPanel extends AbstractPanel {

	private static final long serialVersionUID = -830536385562796749L;

	public MainPanel() {
		setBounds(0, 0, getUIManager().getScreenWidth(), getUIManager().getScreenHeight());
		JLabel title = new JLabel("Conway's Game Of Life");
		getUIManager().getUIConfig("main_title").applyBoundsToComponent(title);
		title.setFont(getUIManager().getUIConfig("main_title").getFont());
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title);
	}
	
}
