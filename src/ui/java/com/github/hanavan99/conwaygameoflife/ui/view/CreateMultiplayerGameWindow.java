package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateMultiplayerGameWindow extends JFrame {

	private static final long serialVersionUID = -1185509656011361538L;

	private JPanel panel = new JPanel();
	private JLabel[] labels = { new JLabel("defaultText"), new JLabel("defaultText"), new JLabel("defaultText") };
	private JTextField[] textFields = { new JTextField(), new JTextField(), new JTextField() };
	private JButton okButton = new JButton("OK");

	public CreateMultiplayerGameWindow() {
		this.setAlwaysOnTop(true);
		this.setTitle("Create Multiplayer Game");
		this.setVisible(true);
		this.setSize(new Dimension(250, 200));
		this.add(panel);
		for (int i = 0; i < labels.length; i++) {
			textFields[i].setColumns(10);
			panel.add(labels[i]);
			panel.add(textFields[i]);
		}
		panel.add(okButton);
		okButton.addActionListener((ActionEvent e) -> {
			PanelManager.addPanel("multiplayerPanel", new JoinMultiplayerGamePanel());
			PanelManager.showPanel("multiplayerPanel");
			setVisible(false);
		});
	}

}
