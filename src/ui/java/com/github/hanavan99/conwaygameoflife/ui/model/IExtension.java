package com.github.hanavan99.conwaygameoflife.ui.model;

import org.w3c.dom.Element;

/**
 * Interface for an extension class to the layout model
 * 
 * @author Zach Deibert
 */
public interface IExtension extends IProperty {
	/**
	 * Loads the extension using an xml element
	 * 
	 * @param element
	 *            The element
	 */
	void load(Element element);
}
