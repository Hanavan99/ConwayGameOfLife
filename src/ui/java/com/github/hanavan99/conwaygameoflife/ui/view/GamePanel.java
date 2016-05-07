package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.Color;

public class GamePanel extends AbstractPanel{

	private static final long serialVersionUID = 8016777731264107678L;

	public GamePanel(boolean multiplayer) {
		setBackground(new Color(125, 125, 125));
		add(new NavigationPanel(), "navigation_panel");
		if(multiplayer)
		{
			
		}
		else
		{
			
		}
	}

}
