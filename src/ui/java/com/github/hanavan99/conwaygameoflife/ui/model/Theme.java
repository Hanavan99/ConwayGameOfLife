package com.github.hanavan99.conwaygameoflife.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An object representing a UI theme
 * 
 * @author Zach Deibert
 */
public class Theme {
	private final List<Panel> panels;

	/**
	 * Gets the list of panels in this theme
	 * 
	 * @return The list
	 */
	public List<Panel> getPanels() {
		return panels;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((panels == null) ? 0 : panels.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !(obj instanceof Theme) ) {
			return false;
		}
		Theme other = (Theme) obj;
		if ( panels == null ) {
			if ( other.panels != null ) {
				return false;
			}
		} else if ( !panels.equals(other.panels) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Theme [panels=" + panels + "]";
	}

	/**
	 * Default constructor
	 */
	public Theme() {
		panels = new ArrayList<Panel>();
	}
}
