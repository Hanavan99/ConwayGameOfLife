package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.function.Supplier;

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
	private final Supplier<Rectangle> bounds;
	
	/**
	 * Instantiates a new <code>UIConfig</code> class with the specified color, font, and bounds.
	 * @param color The color
	 * @param font The font
	 * @param bounds The bounds
	 */
	public UIConfig(Color color, Font font, Rectangle bounds) {
	    this(color, font, () -> bounds);
	    System.err.println("Wrong ctor");
	}
	
	public UIConfig(Color color, Font font, Supplier<Rectangle> bounds) {
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
		return bounds.get();
	}
	
	/**
	 * Applies this layout to the specified <code>Component</code>.
	 * @param c The <code>Component</code> to apply the layout to
	 */
	public void applyBoundsToComponent(Component c) {
		c.setBounds(getBounds());
	}
	
	public void applyBoundsToComponent(Component c, boolean useX, boolean useY, boolean useWidth, boolean useHeight) {
	    Rectangle bounds = getBounds();
		c.setBounds(useX ? bounds.x : null, useY ? bounds.y : null, useWidth ? bounds.width : null, useHeight ? bounds.height : null);
	}
}
