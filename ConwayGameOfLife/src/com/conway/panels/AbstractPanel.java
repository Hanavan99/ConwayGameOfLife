package com.conway.panels;

import javax.swing.JPanel;

import com.conway.layout.UIManager;

public abstract class AbstractPanel extends JPanel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = -3297556724944722841L;
	private UIManager manager;
	
	/**
	 * Called after the <code>PanelManager</code> sets this panel's visibility to <code>true</code>.
	 */
	public void panelShown() {
		
	}
	
	public UIManager getUIManager() {
	    if ( manager == null ) {
	        manager = new UIManager(this);
	    }
		return manager;
	}

}
