package com.github.hanavan99.conwaygameoflife.ui.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A property assigned from a literal value
 * 
 * @author Zach Deibert
 */
public class LiteralProperty implements IProperty {
	private static final Logger log = LogManager.getLogger();
	private String name;
	private Object value;

	/**
	 * Gets the parsed value for a parameter of a specified type
	 * 
	 * @param cls
	 *            The type of parameter
	 * @param constants
	 *            The class containing constants to use, or null
	 * @return The casted object
	 * @throws ReflectiveOperationException
	 *             if the value could not be casted
	 */
	Object getValueFor(Class<?> cls, Class<?> constants) throws ReflectiveOperationException {
		if ( constants != null ) {
			try {
				Field field = constants.getField(value.toString());
				return field.get(null);
			} catch ( ReflectiveOperationException ex ) {
			}
		}
		try {
			switch ( cls.getName() ) {
			case "byte":
				return Byte.parseByte(value.toString());
			case "short":
				return Short.parseShort(value.toString());
			case "int":
				return Integer.parseInt(value.toString());
			case "long":
				return Long.parseLong(value.toString());
			case "float":
				return Float.parseFloat(value.toString());
			case "double":
				return Double.parseDouble(value.toString());
			case "boolean":
				return Boolean.parseBoolean(value.toString());
			default:
				return cls.cast(value);
			}
		} catch ( NumberFormatException ex ) {
			throw new ClassCastException("Unable to parse input");
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public void apply(Object obj, java.awt.Dimension size) throws ReflectiveOperationException {
		if ( name == null ) {
			log.warn("Null property found in LiteralProperty for {}", obj);
			return;
		}
		Class<?> cls = obj.getClass();
		String name = String.format("set%c%s", Character.toUpperCase(this.name.charAt(0)), this.name.substring(1));
		for ( Method method : cls.getMethods() ) {
			if ( method.getName().equals(name) && method.getParameterCount() == 1 ) {
				Class<?> arg = method.getParameterTypes()[0];
				try {
					method.invoke(obj, getValueFor(arg, obj.getClass()));
				} catch ( ReflectiveOperationException | NumberFormatException ex ) {
				}
			}
		}
		throw new NoSuchFieldException("Unable to find setter for property");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if ( !(obj instanceof LiteralProperty) ) {
			return false;
		}
		LiteralProperty other = (LiteralProperty) obj;
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		} else if ( !name.equals(other.name) ) {
			return false;
		}
		if ( value == null ) {
			if ( other.value != null ) {
				return false;
			}
		} else if ( !value.equals(other.value) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LiteralProperty [name=" + name + ", value=" + value + "]";
	}
}
