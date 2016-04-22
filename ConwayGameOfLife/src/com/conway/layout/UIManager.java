package com.conway.layout;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;

public class UIManager {

	private HashMap<String, HashMap<String, UIConfig>> UIs = new HashMap<String, HashMap<String, UIConfig>>();
	private String currentLayout = "default";
	private int currentLayoutID = 0;

	public UIManager() {
		resetUI();
	}
	
	public void resetUI() {
		HashMap<String, UIConfig> layout_default = new HashMap<String, UIConfig>();
		layout_default.put("main_title", new UIConfig(null, new Font("Arial", Font.PLAIN, 72), new Rectangle(0, 0, getScreenWidth(), 100)));
		layout_default.put("main_button1", new UIConfig(null, null, new Rectangle(getRelScreenWidth(0.5d) - 100, getRelScreenHeight(0.2d), 200, 30)));
		layout_default.put("main_button2", new UIConfig(null, null, new Rectangle(getRelScreenWidth(0.5d) - 100, getRelScreenHeight(0.2d) + 40, 200, 30)));
		layout_default.put("main_button3", new UIConfig(null, null, new Rectangle(getRelScreenWidth(0.5d) - 100, getRelScreenHeight(0.2d) + 80, 200, 30)));
		layout_default.put("main_button4", new UIConfig(null, null, new Rectangle(getRelScreenWidth(0.5d) - 100, getRelScreenHeight(0.2d) + 120, 200, 30)));
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
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}
	
	public int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	private int getRelScreenWidth(double percent) {
		return (int) (getScreenWidth() * percent);
	}
	
	private int getRelScreenHeight(double percent) {
		return (int) (getScreenHeight() * percent);
	}

}
