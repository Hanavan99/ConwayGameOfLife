package com.conway.layout;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.HashMap;

public class UIManager {

	private HashMap<String, HashMap<String, UIConfig>> UIs = new HashMap<String, HashMap<String, UIConfig>>();
	private String currentLayout = "default";
	private int currentLayoutID = 0;
	private final Component comp;

	public UIManager(Component comp) {
	    this.comp = comp;
		resetUI();
	}
	
	public void resetUI() {
		HashMap<String, UIConfig> layout_default = new HashMap<String, UIConfig>();
		layout_default.put("main_title", new UIConfig(null, new Font("Arial", Font.PLAIN, 72), new Rectangle(0, 0, getLayoutWidth(), 100)));
		layout_default.put("main_button1", new UIConfig(null, null, new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d), 200, 30)));
		layout_default.put("main_button2", new UIConfig(null, null, new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 40, 200, 30)));
		layout_default.put("main_button3", new UIConfig(null, null, new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 80, 200, 30)));
		layout_default.put("main_button4", new UIConfig(null, null, new Rectangle(getRelLayoutWidth(0.5d) - 100, getRelLayoutHeight(0.2d) + 120, 200, 30)));
		UIs.put("default", layout_default);
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
		return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getWidth();
	}
	
	public int getScreenHeight() {
		return (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight();
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
