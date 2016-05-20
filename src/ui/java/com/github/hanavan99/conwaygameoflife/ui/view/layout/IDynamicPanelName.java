package com.github.hanavan99.conwaygameoflife.ui.view.layout;

/**
 * Interface for a panel that has a name that changes based on the state of the
 * object.
 * 
 * @author Zach Deibert
 */
public interface IDynamicPanelName {
	/**
	 * Gets the name of the panel for the current state of the object.
	 * 
	 * @return The panel name
	 */
	String getPanelName();
}
