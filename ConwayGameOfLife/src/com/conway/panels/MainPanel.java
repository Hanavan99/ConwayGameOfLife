package com.conway.panels;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The main menu panel.
 * 
 * @author Hanavan Kuhn, Nathan Gawith
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
		JButton multiCreate = new JButton("Create Multiplayer Game");
		JButton multiJoin = new JButton("Join Multiplayer Game");
		JButton singleCreate = new JButton("Singleplayer Game");
		JButton exit = new JButton("Exit");
		getUIManager().getUIConfig("main_button1").applyBoundsToComponent(title);
		multiCreate.setFont(getUIManager().getUIConfig("main_button1").getFont());
		multiCreate.setHorizontalAlignment(JLabel.CENTER);
		add(multiCreate);
		getUIManager().getUIConfig("main_button2").applyBoundsToComponent(title);
		multiJoin.setFont(getUIManager().getUIConfig("main_button2").getFont());
		multiJoin.setHorizontalAlignment(JLabel.CENTER);
		add(multiCreate);
		getUIManager().getUIConfig("main_button3").applyBoundsToComponent(title);
		singleCreate.setFont(getUIManager().getUIConfig("main_button3").getFont());
		singleCreate.setHorizontalAlignment(JLabel.CENTER);
		add(singleCreate);
		getUIManager().getUIConfig("main_button4").applyBoundsToComponent(title);
		singleCreate.setFont(getUIManager().getUIConfig("main_button4").getFont());
		singleCreate.setHorizontalAlignment(JLabel.CENTER);
		add(singleCreate);
	}
	
}
