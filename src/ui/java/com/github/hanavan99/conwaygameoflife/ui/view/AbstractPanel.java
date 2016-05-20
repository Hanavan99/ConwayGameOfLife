package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.JPanel;

import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;
import com.github.hanavan99.conwaygameoflife.ui.view.layout.UIManager;

public abstract class AbstractPanel extends JPanel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = -3297556724944722841L;
	private static UIModel model;
	private static UIManager manager;
	
	/**
	 * Called after the <code>PanelManager</code> sets this panel's visibility to <code>true</code>.
	 */
	public void panelShown() {
		
	}

	/**
	 * Sets the model for the manager on this panel
	 * 
	 * @param model
	 *            The model
	 */
	public static void setModel(UIModel model) {
		AbstractPanel.model = model;
	}

	public UIManager getUIManager() {
		if ( manager == null ) {
			if ( model == null ) {
				throw new IllegalStateException(
						"The abstract panel does not yet know the ui model and can therefore not construct a manager");
			}
			manager = new UIManager(this, model);
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
