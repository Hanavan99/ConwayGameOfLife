package com.github.hanavan99.conwaygameoflife.ui.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.github.hanavan99.conwaygameoflife.model.Game;

/**
 * The model submodule for the ui module
 * 
 * @author Zach Deibert
 */
public class UIModel {
	private static final Logger log = LogManager.getLogger();
	private final List<Extension> extensions;
	private final List<Theme> themes;
	private Theme currentTheme;

	private Extension parseExtension(Element element) throws ClassNotFoundException {
		Extension extension = new Extension();
		if ( element.hasAttribute("class") ) {
			extension.setExtensionClass(element.getAttribute("class"));
		}
		if ( element.hasAttribute("name") ) {
			extension.setName(element.getAttribute("name"));
		}
		return extension;
	}

	private List<Extension> parseExtensions(Element element) {
		List<Extension> extensions = new ArrayList<Extension>();
		NodeList list = element.getElementsByTagName("extension");
		for ( int i = 0; i < list.getLength(); ++i ) {
			Node node = list.item(i);
			if ( node instanceof Element ) {
				try {
					extensions.add(parseExtension((Element) node));
				} catch ( ClassNotFoundException ex ) {
					log.catching(ex);
				}
			}
		}
		return extensions;
	}

	private IProperty parseProperty(Attr attribute) {
		LiteralProperty literal = new LiteralProperty();
		literal.setName(attribute.getName());
		literal.setValue(attribute.getValue());
		return literal;
	}

	private IProperty parseProperty(Element element) {
		NodeList list = element.getChildNodes();
		NamedNodeMap attrs = element.getAttributes();
		boolean isLiteral = attrs.getLength() == 0;
		if ( isLiteral ) {
			for ( int i = 0; i < list.getLength(); ++i ) {
				if ( !(list.item(i) instanceof Text) ) {
					isLiteral = false;
					break;
				}
			}
		}
		String name = element.getTagName();
		Extension ext = null;
		for ( Extension extension : extensions ) {
			if ( extension.getName().equals(name) ) {
				ext = extension;
				break;
			}
		}
		IProperty property = null;
		if ( ext != null ) {
			property = ext.construct();
		}
		if ( property == null ) {
			if ( isLiteral ) {
				property = new LiteralProperty();
			} else {
				property = new Transformation();
				property.setName(name);
			}
			if ( property instanceof Transformation ) {
				for ( int i = 0; i < attrs.getLength(); ++i ) {
					Node node = attrs.item(i);
					if ( node instanceof Attr ) {
						((Transformation) property).getProperties().add(parseProperty((Attr) node));
					}
				}
				for ( int i = 0; i < list.getLength(); ++i ) {
					Node node = list.item(i);
					if ( node instanceof Element ) {
						((Transformation) property).getProperties().add(parseProperty((Element) node));
					}
				}
			}
		} else {
			((IExtension) property).load(element);
		}
		return property;
	}

	private Component parseComponent(Element element) {
		Component component = new Component();
		if ( element.hasAttribute("name") ) {
			component.setName(element.getAttribute("name"));
		}
		NodeList list = element.getChildNodes();
		for ( int i = 0; i < list.getLength(); ++i ) {
			Node node = list.item(i);
			if ( node instanceof Element ) {
				component.getProperties().add(parseProperty((Element) node));
			}
		}
		NamedNodeMap attrs = element.getAttributes();
		for ( int i = 0; i < attrs.getLength(); ++i ) {
			Node node = attrs.item(i);
			if ( node instanceof Attr && !node.getNodeName().equals("name") ) {
				component.getProperties().add(parseProperty((Attr) node));
			}
		}
		return component;
	}

	private Panel parsePanel(Element element) {
		Panel panel = new Panel();
		if ( element.hasAttribute("name") ) {
			panel.setName(element.getAttribute("name"));
		}
		NodeList list = element.getElementsByTagName("component");
		for ( int i = 0; i < list.getLength(); ++i ) {
			Node node = list.item(i);
			if ( node instanceof Element ) {
				panel.getComponents().add(parseComponent((Element) node));
			}
		}
		return panel;
	}

	private Theme parseTheme(Element element) {
		Theme theme = new Theme();
		NodeList list = element.getElementsByTagName("panel");
		for ( int i = 0; i < list.getLength(); ++i ) {
			Node node = list.item(i);
			if ( node instanceof Element ) {
				theme.getPanels().add(parsePanel((Element) node));
			}
		}
		return theme;
	}

	private void parseDocument(Element element) {
		NodeList list = element.getChildNodes();
		for ( int i = 0; i < list.getLength(); ++i ) {
			Node node = list.item(i);
			if ( node instanceof Element ) {
				switch ( node.getNodeName() ) {
				case "extensions":
					extensions.addAll(parseExtensions((Element) node));
					break;
				case "theme":
					themes.add(parseTheme((Element) node));
					break;
				}
			}
		}
	}

	/**
	 * Gets the list of installed themes
	 * 
	 * @return The list
	 */
	public List<Theme> getThemes() {
		return themes;
	}

	/**
	 * Gets the current theme, or <code>null</code> if no themes are loaded
	 * 
	 * @return The current theme
	 */
	public Theme getCurrentTheme() {
		return currentTheme;
	}

	/**
	 * Sets the current theme
	 * 
	 * @param currentTheme
	 *            The new current theme
	 */
	public void setCurrentTheme(Theme currentTheme) {
		if ( !themes.contains(currentTheme) ) {
			throw new IllegalArgumentException("Theme is not registered with UI model yet");
		}
		this.currentTheme = currentTheme;
	}

	/**
	 * Sets the current theme
	 * 
	 * @param index
	 *            The index of the new current theme
	 */
	public void setCurrentTheme(int index) {
		setCurrentTheme(themes.get(index));
	}

	/**
	 * Parses the layout.xml file
	 * 
	 * @throws IOException
	 *             if an i/o error occurred while reading the file
	 */
	public void parse() throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(getClass().getResourceAsStream("/layout.xml"));
			parseDocument(doc.getDocumentElement());
			if ( themes.size() > 0 ) {
				currentTheme = themes.get(0);
			} else {
				currentTheme = null;
			}
		} catch ( ParserConfigurationException | SAXException ex ) {
			throw new IOException(ex);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extensions == null) ? 0 : extensions.hashCode());
		result = prime * result + ((themes == null) ? 0 : themes.hashCode());
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
		if ( !(obj instanceof UIModel) ) {
			return false;
		}
		UIModel other = (UIModel) obj;
		if ( extensions == null ) {
			if ( other.extensions != null ) {
				return false;
			}
		} else if ( !extensions.equals(other.extensions) ) {
			return false;
		}
		if ( themes == null ) {
			if ( other.themes != null ) {
				return false;
			}
		} else if ( !themes.equals(other.themes) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UIModel [extensions=" + extensions + ", themes=" + themes + "]";
	}

	/**
	 * Default constructor
	 * 
	 * @param game
	 *            The game model
	 */
	public UIModel(Game game) {
		extensions = new ArrayList<Extension>();
		themes = new ArrayList<Theme>();
	}
}
