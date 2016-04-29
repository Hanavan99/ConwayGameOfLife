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
		getUIManager().resetUI();
		JLabel title = new JLabel("Conway's Game Of Life");
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title, "main_title");
		JButton multiCreate = new JButton("Create Multiplayer Game");
		JButton multiJoin = new JButton("Join Multiplayer Game");
		JButton singleCreate = new JButton("Singleplayer Game");
		JButton exit = new JButton("Exit");
		add(multiCreate, "main_button1");
		add(multiJoin, "main_button2");
		add(singleCreate, "main_button3");
		add(exit, "main_button4");
		exit.addActionListener((ActionEvent e) -> {
			if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?") == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		});
		repaint();
	}
	
}
