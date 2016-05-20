package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JComponent;

import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;

/**
 * The manager for UI layout components
 * 
 * @author Zach Deibert
 */
public class UIManager {
	private final Component comp;
	private final UIModel model;

	/**
	 * Default constructor
	 * 
	 * @param comp
	 *            The component to manager the layout for
	 * @param model
	 *            The model to get data from
	 */
	public UIManager(Component comp, UIModel model) {
		if ( !(comp instanceof JComponent || comp instanceof Container) ) {
			throw new IllegalArgumentException("Invalid component to manage");
		}
		this.comp = comp;
		this.model = model;
		resetUI();
	}

	/**
	 * Resets the UI for a component and relays out the container
	 */
	public void resetUI() {
		if ( comp instanceof JComponent ) {
			((JComponent) comp).setLayout(new UILayout(this, model));
		} else if ( comp instanceof Container ) {
			Runnable relayout = () -> {
				UILayout layout = new UILayout(this, model);
				for ( Component c : ((Container) comp).getComponents() ) {
					layout.addLayoutComponent(c, c.getName());
				}
				layout.layoutContainer((Container) comp);
			};
			comp.addHierarchyBoundsListener(new HierarchyBoundsListener() {
				@Override
				public void ancestorMoved(HierarchyEvent e) {
					relayout.run();
				}

				@Override
				public void ancestorResized(HierarchyEvent e) {
					relayout.run();
				}
			});
			comp.addHierarchyListener(new HierarchyListener() {
				@Override
				public void hierarchyChanged(HierarchyEvent e) {
					relayout.run();
				}
			});
		}
		comp.invalidate();
	}

	/**
	 * Gets the width of the screen. This should not be used for centering
	 * purposes because the window may not take up the full screen (in an
	 * applet) or there may be multiple screens attached the the device this is
	 * running on. For centering purposes, use
	 * {@link UIManager#getLayoutWidth()}
	 * 
	 * @return The width (in pixels) of the screen
	 */
	public int getScreenWidth() {
		return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().getBounds().getWidth();
	}

	/**
	 * Gets the height of the screen. This should not be used for centering
	 * purposes because the window may not take up the full screen (in an
	 * applet) or there may be multiple screens attached the the device this is
	 * running on. For centering purposes, use
	 * {@link UIManager#getLayoutHeight()}
	 * 
	 * @return The height (in pixels) of the screen
	 */
	public int getScreenHeight() {
		return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().getBounds().getHeight();
	}

	/**
	 * Gets the width of the layout available for components to be in
	 * 
	 * @return The width (in pixels) of the layout
	 */
	public int getLayoutWidth() {
		return comp.getWidth();
	}

	/**
	 * Gets the height of the layout available for components to be in
	 * 
	 * @return The height (in pixels) of the layout
	 */
	public int getLayoutHeight() {
		return comp.getHeight();
	}
}
