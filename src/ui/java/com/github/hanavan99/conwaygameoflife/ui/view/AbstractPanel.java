package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import javax.swing.JPanel;

import com.github.hanavan99.conwaygameoflife.ui.view.layout.UIManager;

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
	
	public AbstractPanel() {
	    addHierarchyBoundsListener(new HierarchyBoundsListener() {
            @Override
            public void ancestorResized(HierarchyEvent e) {
                invalidate();
            }
            
            @Override
            public void ancestorMoved(HierarchyEvent e) {
                invalidate();
            }
        });
	}
}
