package com.github.hanavan99.conwaygameoflife.ui.view.layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.HashMap;

import javax.swing.JComponent;

import com.github.hanavan99.conwaygameoflife.ui.model.UIModel;

public class UIManager {

	private HashMap<String, HashMap<String, UIConfig>> UIs = new HashMap<String, HashMap<String, UIConfig>>();
	private String currentLayout = "default";
	private int currentLayoutID = 0;
	private final Component comp;
	private final UIModel model;

	public UIManager(Component comp, UIModel model) {
		if (!(comp instanceof JComponent || comp instanceof Container)) {
			throw new IllegalArgumentException("Invalid component to manage");
		}
		this.comp = comp;
		this.model = model;
		resetUI();
	}

	public void resetUI() {
		HashMap<String, UIConfig> layout_default = new HashMap<String, UIConfig>();
		layout_default.put("main_title", new UIConfig(null, new Font("Arial", Font.PLAIN, 72),
				() -> new Rectangle(0, 0, getLayoutWidth(), 100)));
		layout_default.put("main_button1", new UIConfig(null, null,
				() -> new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d), 200, 30)));
		layout_default.put("main_button2", new UIConfig(null, null,
				() -> new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 40, 200, 30)));
		layout_default.put("main_button3", new UIConfig(null, null,
				() -> new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 80, 200, 30)));
		layout_default.put("main_button4", new UIConfig(null, null,
				() -> new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 120, 200, 30)));
		layout_default.put("main_gif", new UIConfig(null, null,
				() -> new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 160, 200, 200)));
		layout_default.put("navigation_panel", new UIConfig(new Color(0, 125, 0), null,
				() -> new Rectangle(getRelLayoutWidth(0.8d) - 100, getRelLayoutHeight(0.8d) + 120, 200, 30)));
		layout_default.put("navigation_mainmenu", new UIConfig(null, null,
				() -> new Rectangle(getRelLayoutWidth(0.5d) - 50, 0, 100, 30)));
		UIs.put("default", layout_default);
		if (comp instanceof JComponent) {
			((JComponent) comp).setLayout(new UILayout(this, model));
		} else if (comp instanceof Container) {
			Runnable relayout = () -> {
				UILayout layout = new UILayout(this, model);
				for (Component c : ((Container) comp).getComponents()) {
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

	public UIConfig getUIConfig(String name) {
		return UIs.get(currentLayout).get(name);
	}

	public void changeLayout() {
		resetUI();
		currentLayoutID++;
		if (UIs.keySet().size() <= currentLayoutID) {
			currentLayoutID = 0;
		}
		currentLayout = (String) UIs.keySet().toArray()[currentLayoutID];
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

	private int getRelLayoutWidth(double percent) {
		return (int) (getLayoutWidth() * percent);
	}

	private int getRelLayoutHeight(double percent) {
		return (int) (getLayoutHeight() * percent);
	}

}
