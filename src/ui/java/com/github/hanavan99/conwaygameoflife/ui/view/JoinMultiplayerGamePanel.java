package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

public class JoinMultiplayerGamePanel extends AbstractPanel {

	private static final long serialVersionUID = 4293106669482179946L;

	private JTextField textField = new JTextField();
	private JButton joinButton = new JButton("Join");

	public JoinMultiplayerGamePanel() {
		this.setBackground(new Color(125, 125, 125));
		textField.setColumns(10);
		add(textField);
		add(joinButton);
		joinButton.addActionListener((ActionEvent e) -> {
			//add some networking stuff
			PanelManager.addPanel("gamePanel", new GamePanel(true));
			PanelManager.showPanel("gamePanel");
		});
	}

}
