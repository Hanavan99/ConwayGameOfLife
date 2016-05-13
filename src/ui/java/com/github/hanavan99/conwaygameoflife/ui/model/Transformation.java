package com.github.hanavan99.conwaygameoflife.ui.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A transformation of a layout property
 * 
 * @author Zach Deibert
 */
public class Transformation implements IProperty {
	private static final Logger log = LogManager.getLogger();
	private final List<IProperty> properties;
	private String name;
	private Object value;

	/**
	 * Gets a list of the property objects
	 * 
	 * @return The list
	 */
	public List<IProperty> getProperties() {
		return properties;
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

	private void apply(Object obj, java.awt.Dimension size, boolean retryWithCtor) throws ReflectiveOperationException {
		String getter = String.format("get%c%s", Character.toUpperCase(name.charAt(0)), name.substring(1));
		Object o;
		try {
			o = obj.getClass().getMethod(getter).invoke(obj);
		} catch ( ReflectiveOperationException ex ) {
			log.info(this);
			log.warn("Getter {}.{} does not exist", obj.getClass().getName(), getter);
			try {
				o = obj.getClass().newInstance();
			} catch ( ReflectiveOperationException e ) {
				o = null;
			}
		}
		List<IProperty> failed = new ArrayList<IProperty>();
		if ( o != null ) {
			for ( IProperty property : properties ) {
				try {
					property.apply(o, size);
				} catch ( ReflectiveOperationException ex ) {
					failed.add(property);
				}
			}
		}
		if ( o == null || !failed.isEmpty() ) {
			if ( retryWithCtor ) {
				String setter = String.format("set%c%s", Character.toUpperCase(name.charAt(0)), name.substring(1));
				Method set = null;
				for ( Method method : obj.getClass().getMethods() ) {
					if ( method.getName().equals(setter) && method.getParameterCount() == 1 ) {
						set = method;
						break;
					}
				}
				if ( set == null ) {
					for ( IProperty fail : failed ) {
						log.warn(
								"Unable to apply property {}.{} to object {} because a setter for {} could not be found",
								name, fail.getName(), obj, name);
					}
				}
				Constructor<?> best = null;
				int bestArgAmt = 0;
				for ( Constructor<?> ctor : set.getParameterTypes()[0].getConstructors() ) {
					if ( ctor.getParameterCount() > 0 ) {
						int argAmt = 0;
						for ( Parameter param : ctor.getParameters() ) {
							for ( IProperty property : failed ) {
								if ( property instanceof LiteralProperty
										&& param.getName().equals(property.getName()) ) {
									++argAmt;
								}
							}
						}
						if ( bestArgAmt < argAmt ) {
							best = ctor;
							bestArgAmt = argAmt;
						}
					}
				}
				if ( best == null ) {
					for ( IProperty fail : failed ) {
						log.warn(
								"Unable to apply property {}.{} to object {} because a constructor could not be found in {}",
								name, fail.getName(), obj, set.getParameterTypes()[0]);
					}
				} else {
					Object[] args = new Object[best.getParameterCount()];
					int i = 0;
					for ( Parameter param : best.getParameters() ) {
						args[i] = null;
						for ( IProperty property : failed ) {
							if ( property instanceof LiteralProperty && param.getName().equals(property.getName()) ) {
								try {
									args[i] = ((LiteralProperty) property).getValueFor(param.getType(),
											set.getParameterTypes()[0]);
									break;
								} catch ( ReflectiveOperationException ex ) {
								}
							}
						}
						if ( args[i] == null ) {
							String name = param.getName();
							getter = String.format("get%c%s", Character.toUpperCase(name.charAt(0)), name.substring(1));
							Class<?> type = param.getType();
							try {
								Method get = obj.getClass().getMethod(getter);
								args[i] = type.cast(get.invoke(obj));
							} catch ( ReflectiveOperationException ex ) {
								log.warn("Using default value for ctor parameter {} on class {}", name, type.getName());
							}
						}
						++i;
					}
					try {
						o = best.newInstance(args);
						obj.getClass().getMethod(setter, o.getClass()).invoke(obj, o);
						apply(obj, size, false);
					} catch ( ReflectiveOperationException ex ) {
						log.catching(ex);
						for ( IProperty fail : failed ) {
							log.warn(
									"Unable to apply property {}.{} to object {} because the setter or constructor failed",
									name, fail.getName(), obj);
						}
					}
				}
			} else {
				for ( IProperty fail : failed ) {
					log.warn("Unable to apply property {}.{} to object {} because the property is not setable", name,
							fail.getName(), obj);
				}
			}
		}
	}

	@Override
	public void apply(Object obj, java.awt.Dimension size) throws ReflectiveOperationException {
		apply(obj, size, true);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		if ( !(obj instanceof Transformation) ) {
			return false;
		}
		Transformation other = (Transformation) obj;
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		} else if ( !name.equals(other.name) ) {
			return false;
		}
		if ( properties == null ) {
			if ( other.properties != null ) {
				return false;
			}
		} else if ( !properties.equals(other.properties) ) {
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
		return "Transformation [properties=" + properties + ", name=" + name + ", value=" + value + "]";
	}

	/**
	 * Default constructor
	 */
	public Transformation() {
		properties = new ArrayList<IProperty>();
	}
}
