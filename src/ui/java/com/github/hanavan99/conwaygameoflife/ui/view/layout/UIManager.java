package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JComponent;

import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;

public class UIManager {
	private final Component comp;
	private final UIModel model;

	public UIManager(Component comp, UIModel model) {
		if ( !(comp instanceof JComponent || comp instanceof Container) ) {
			throw new IllegalArgumentException("Invalid component to manage");
		}
		this.comp = comp;
		this.model = model;
		resetUI();
	}

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

	public int getScreenWidth() {
		return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().getBounds().getWidth();
	}

	public int getScreenHeight() {
		return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().getBounds().getHeight();
	}

	public int getLayoutWidth() {
		return comp.getWidth();
	}

	public int getLayoutHeight() {
		return comp.getHeight();
	}
}
