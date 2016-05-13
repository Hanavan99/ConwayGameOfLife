package com.github.hanavan99.conwaygameoflife.ui.model;

import java.awt.Dimension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A model object for a component's layout
 * 
 * @author Zach Deibert
 */
public class Component extends Transformation {
	private static final Logger log = LogManager.getLogger();

	@Override
	public void apply(Object obj, Dimension size) throws ReflectiveOperationException {
		for ( IProperty property : getProperties() ) {
			property.apply(obj, size);
		}
	}

	/**
	 * Applies the properties from this component onto an AWT component
	 * 
	 * @param component
	 *            The AWT component
	 */
	public void apply(java.awt.Component component) {
		try {
			apply(component, component.getParent().getSize());
		} catch ( ReflectiveOperationException ex ) {
			log.catching(ex);
		}
	}

	@Override
	public String toString() {
		return "Component [properties=" + getProperties() + ", name=" + getName() + ", value=" + getValue() + "]";
	}
}
