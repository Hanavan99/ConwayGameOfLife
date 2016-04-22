package com.conway.panels;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = -3297556724944722841L;
	
	/**
	 * Called whenever the PanelManager changes the visible panel to this instance
	 */
	public void panelShown() {
		
	}

}
