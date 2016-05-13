package com.github.hanavan99.conwaygameoflife.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A model object for a panel's layout
 * 
 * @author Zach Deibert
 */
public class Panel {
	private final List<Component> components;
	private String name;

	/**
	 * Gets the list of components in the panel
	 * 
	 * @return The list
	 */
	public List<Component> getComponents() {
		return components;
	}

	/**
	 * Gets the name of the component
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the component
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if ( !(obj instanceof Panel) ) {
			return false;
		}
		Panel other = (Panel) obj;
		if ( components == null ) {
			if ( other.components != null ) {
				return false;
			}
		} else if ( !components.equals(other.components) ) {
			return false;
		}
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		} else if ( !name.equals(other.name) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Panel [components=" + components + ", name=" + name + "]";
	}

	/**
	 * Default constructor
	 */
	public Panel() {
		components = new ArrayList<Component>();
	}
}
