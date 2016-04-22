package com.conway.layout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * The <code>UIConfig</code> class stores information about the UI. This can
 * hold things such as color, font, and positioning.
 * 
 * @author Hanavan Kuhn
 *
 */
public class UIConfig {

	private final Color color;
	private final Font font;
	private final Rectangle bounds;
	
	/**
	 * Instantiates a new <code>UIConfig</code> class with the specified color, font, and bounds.
	 * @param color The color
	 * @param font The font
	 * @param bounds The bounds
	 */
	public UIConfig(Color color, Font font, Rectangle bounds) {
		this.color = color;
		this.font = font;
		this.bounds = bounds;
	}
	
	/**
	 * Gets the color of this <code>UIConfig</code> class.
	 * @return The color
	 */
	public Color getColor() {
		return color;
	}
	
	public Font getFont() {
		return font;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Applies this layout to the specified <code>JComponent</code>.
	 * @param c The <code>JComponent</code> to apply the layout to
	 */
	public void applyBoundsToComponent(JComponent c) {
		c.setBounds(bounds);
	}
	
	public void applyBoundsToComponent(JComponent c, boolean useX, boolean useY, boolean useWidth, boolean useHeight) {
		c.setBounds(useX ? bounds.x : null, useY ? bounds.y : null, useWidth ? bounds.width : null, useHeight ? bounds.height : null);
	}
}
