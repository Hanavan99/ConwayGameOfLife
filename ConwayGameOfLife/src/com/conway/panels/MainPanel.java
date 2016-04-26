package com.conway.panels;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
		setLayout(null);
		JLabel title = new JLabel("Conway's Game Of Life");
		getUIManager().getUIConfig("main_title").applyBoundsToComponent(title);
		title.setFont(getUIManager().getUIConfig("main_title").getFont());
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title);
		JButton multiCreate = new JButton("Create Multiplayer Game");
		JButton multiJoin = new JButton("Join Multiplayer Game");
		JButton singleCreate = new JButton("Singleplayer Game");
		JButton exit = new JButton("Exit");
		getUIManager().getUIConfig("main_button1").applyBoundsToComponent(multiCreate);
		multiCreate.setFont(getUIManager().getUIConfig("main_button1").getFont());
		add(multiCreate);
		getUIManager().getUIConfig("main_button2").applyBoundsToComponent(multiJoin);
		multiJoin.setFont(getUIManager().getUIConfig("main_button2").getFont());
		add(multiJoin);
		getUIManager().getUIConfig("main_button3").applyBoundsToComponent(singleCreate);
		singleCreate.setFont(getUIManager().getUIConfig("main_button3").getFont());
		add(singleCreate);
		getUIManager().getUIConfig("main_button4").applyBoundsToComponent(exit);
		exit.setFont(getUIManager().getUIConfig("main_button4").getFont());
		add(exit);
		exit.addActionListener((ActionEvent e) -> {
			if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?") == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		});
	}
	
}
