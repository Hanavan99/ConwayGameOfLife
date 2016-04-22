package com.conway.panels;

import java.util.HashMap;

import javax.swing.JFrame;

public class PanelManager {

	private static HashMap<String, AbstractPanel> panels = new HashMap<String, AbstractPanel>();
	private static JFrame parentframe;

	/**
	 * Adds a new panel to this <code>PanelManager</code>.
	 * 
	 * @param name The string name
	 * @param panel The instance of an <code>AbstractPanel</code>
	 */
	public static void addPanel(String name, AbstractPanel panel) {
		panels.put(name, panel);
	}

	/**
	 * Gets a panel from this <code>PanelManager</code>.
	 * @param name The string name
	 * @return The <code>AbstractPanel</code>, but if the panel is not found, returns <code>null</code>.
	 */
	public static AbstractPanel getPanel(String name) {
		return panels.get(name);
	}

	public static void setParentFrame(JFrame frame) {
		parentframe = frame;
	}

	public static void showPanel(String name) {
		for (String s : panels.keySet()) {
			panels.get(s).setVisible(false);
		}
		panels.get(name).setVisible(true);
	}

}
