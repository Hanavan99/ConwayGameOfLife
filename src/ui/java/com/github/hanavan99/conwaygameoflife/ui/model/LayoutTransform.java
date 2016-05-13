package com.github.hanavan99.conwaygameoflife.ui.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The transformation on a bounding box in the layout
 * 
 * @author Zach Deibert
 */
public class LayoutTransform implements IExtension {
	private Point position;
	private Point margin;
	private Dimension size;
	private String name;
	private Object value;

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
	public void apply(Object obj, java.awt.Dimension size) {
		if ( obj instanceof java.awt.Component ) {
			java.awt.Component c = (java.awt.Component) obj;
			c.setBounds((int) (position.getX(size) + margin.getX(size)),
					(int) (position.getY(size) + margin.getY(size)), (int) this.size.getWidth(size),
					(int) this.size.getHeight(size));
		}
	}

	private void parsePoint(Point point, Element element) {
		if ( element.hasAttribute("x") ) {
			point.setX(element.getAttribute("x"));
		}
		if ( element.hasAttribute("y") ) {
			point.setY(element.getAttribute("y"));
		}
	}

	private void parseDimension(Dimension dimension, Element element) {
		if ( element.hasAttribute("width") ) {
			dimension.setWidth(element.getAttribute("width"));
		}
		if ( element.hasAttribute("height") ) {
			dimension.setHeight(element.getAttribute("height"));
		}
	}

	@Override
	public void load(Element element) {
		position = new Point();
		margin = new Point();
		size = new Dimension();
		NodeList list = element.getChildNodes();
		for ( int i = 0; i < list.getLength(); ++i ) {
			Node node = list.item(i);
			if ( node instanceof Element ) {
				switch ( node.getNodeName() ) {
				case "position":
					parsePoint(position, (Element) node);
					break;
				case "margin":
					parsePoint(margin, (Element) node);
					break;
				case "size":
					parseDimension(size, (Element) node);
					break;
				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((margin == null) ? 0 : margin.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		if ( !(obj instanceof LayoutTransform) ) {
			return false;
		}
		LayoutTransform other = (LayoutTransform) obj;
		if ( position == null ) {
			if ( other.position != null ) {
				return false;
			}
		} else if ( !position.equals(other.position) ) {
			return false;
		}
		if ( margin == null ) {
			if ( other.margin != null ) {
				return false;
			}
		} else if ( !margin.equals(other.margin) ) {
			return false;
		}
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		} else if ( !name.equals(other.name) ) {
			return false;
		}
		if ( size == null ) {
			if ( other.size != null ) {
				return false;
			}
		} else if ( !size.equals(other.size) ) {
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
		return "LayoutTransform [position=" + position + ", margin=" + margin + ", size=" + size + ", name=" + name
				+ ", value=" + value + "]";
	}
}
