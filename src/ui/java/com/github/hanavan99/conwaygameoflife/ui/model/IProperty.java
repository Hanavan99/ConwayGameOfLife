package com.github.hanavan99.conwaygameoflife.ui.model;

/**
 * Interface for an assignable property in a layout
 * 
 * @author Zach Deibert
 */
public interface IProperty {
	/**
	 * Gets the name of the property
	 * 
	 * @return The name
	 */
	public String getName();

	/**
	 * Sets the name of the property
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name);

	/**
	 * Gets the value of the property
	 * 
	 * @return The value
	 */
	public Object getValue();

	/**
	 * Sets the value of the property
	 * 
	 * @param value
	 *            The value
	 */
	public void setValue(Object value);

	/**
	 * Applies this property to an object
	 * 
	 * @param obj
	 *            The object to apply it on
	 * @param size
	 * @throws ReflectiveOperationException
	 *             if an error occurs while performing reflection
	 */
	public void apply(Object obj, java.awt.Dimension size) throws ReflectiveOperationException;
}
