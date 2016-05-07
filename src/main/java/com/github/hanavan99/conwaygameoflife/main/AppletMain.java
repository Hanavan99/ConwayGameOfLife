package com.github.hanavan99.conwaygameoflife.main;

import java.applet.Applet;
import java.awt.BorderLayout;

import com.github.hanavan99.conwaygameoflife.ui.view.MainPanel;
import com.github.hanavan99.conwaygameoflife.ui.view.PanelManager;

public final class AppletMain extends Applet {
	private static final long serialVersionUID = 8187756037896569937L;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		PanelManager.setParentFrame(this);
		PanelManager.addPanel("main", new MainPanel());
	}

	@Override
	public void destroy() {
		removeAll();
	}
}
