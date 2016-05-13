package com.github.hanavan99.conwaygameoflife.ui.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A model object representing an extension to the layout model
 * 
 * @author Zach Deibert
 */
public class Extension {
	private static final Logger log = LogManager.getLogger();
	private Class<?> cls;
	private String name;

	/**
	 * Gets the class the extension is in
	 * 
	 * @return The extension class
	 */
	public Class<?> getExtensionClass() {
		return cls;
	}

	/**
	 * Sets the class the extension is in
	 * 
	 * @param cls
	 *            The extension class
	 */
	public void setExtensionClass(Class<?> cls) {
		this.cls = cls;
	}

	/**
	 * Sets the class the extension is in
	 * 
	 * @param cls
	 *            The name of the extension class
	 * @throws ClassNotFoundException
	 *             if the class is not found
	 */
	public void setExtensionClass(String cls) throws ClassNotFoundException {
		setExtensionClass(Class.forName(cls));
	}

	/**
	 * Gets the name of the extension element
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the extension element
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Constructs a new extension from the class
	 * 
	 * @return The new extension
	 */
	public IExtension construct() {
		try {
			Object o = cls.newInstance();
			if ( o instanceof IExtension ) {
				return (IExtension) o;
			}
		} catch ( ReflectiveOperationException ex ) {
			log.catching(ex);
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cls == null) ? 0 : cls.hashCode());
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
		if ( !(obj instanceof Extension) ) {
			return false;
		}
		Extension other = (Extension) obj;
		if ( cls == null ) {
			if ( other.cls != null ) {
				return false;
			}
		} else if ( !cls.equals(other.cls) ) {
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
		return "Extension [cls=" + cls + ", name=" + name + "]";
	}
}
