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
		setBounds(0, 0, 1920, 1080);
		JLabel title = new JLabel("Conway's Game Of Life");
		getUIManager().getUIConfig("title").applyBoundsToComponent(title);
		title.setFont(getUIManager().getUIConfig("title").getFont());
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title);
	}
	
}
