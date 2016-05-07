package com.github.hanavan99.conwaygameoflife.ui.view;

public class CreateSinglePlayerGamePanel extends AbstractPanel{

	private static final long serialVersionUID = 8428112282665295567L;

	public CreateSinglePlayerGamePanel() {
		PanelManager.addPanel("gamePanel", new GamePanel(false));
		PanelManager.showPanel("gamePanel");
		add(new NavigationPanel(), "navigation_panel");
	}

}
