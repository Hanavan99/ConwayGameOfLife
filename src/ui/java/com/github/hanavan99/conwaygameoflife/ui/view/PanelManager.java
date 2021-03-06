package com.github.hanavan99.conwaygameoflife.ui.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.HashMap;

public class PanelManager {

	private static HashMap<String, AbstractPanel> panels = new HashMap<String, AbstractPanel>();
	private static Container parentframe;

	/**
	 * Adds a new panel to this <code>PanelManager</code>.
	 * 
	 * @param name The string name
	 * @param panel The instance of an <code>AbstractPanel</code>
	 */
	public static void addPanel(String name, AbstractPanel panel) {
		panels.put(name, panel);
		parentframe.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Gets a panel from this <code>PanelManager</code>.
	 * 
	 * @param name The string name
	 * @return The <code>AbstractPanel</code>, but if the panel is not found,
	 *         returns <code>null</code>.
	 */
	public static AbstractPanel getPanel(String name) {
		return panels.get(name);
	}

	/**
	 * Sets this <code>PanelManager</code>'s parent frame so that future panel
	 * additions get added directly to the frame.
	 * 
	 * @param frame The frame to set as the parent frame
	 */
	public static void setParentFrame(Container frame) {
		parentframe = frame;
	}

	/**
	 * Shows the panel with the specified name, or hides all frames if name is not found
	 * @param name The name of the panel
	 */
	public static void showPanel(String name) {
		for (String s : panels.keySet()) {
			panels.get(s).setVisible(false);
		}
		panels.get(name).setVisible(true);
		panels.get(name).panelShown();
	}

}
