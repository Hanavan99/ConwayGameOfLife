package com.conway.main;

import java.applet.Applet;
import java.awt.BorderLayout;

import com.conway.panels.MainPanel;
import com.conway.panels.PanelManager;

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
