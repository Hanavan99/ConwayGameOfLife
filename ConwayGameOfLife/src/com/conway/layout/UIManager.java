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
		HashMap<String, UIConfig> layout_default = new HashMap<String, UIConfig>();
		layout_default.put("title", new UIConfig(null, new Font("Arial", Font.PLAIN, 72), new Rectangle(0, 0, 1920, 200)));
		UIs.put("default", layout_default);
	}

	public UIConfig getUIConfig(String name) {
		return UIs.get(currentLayout).get(name);
	}

	public void changeLayout() {
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

}
