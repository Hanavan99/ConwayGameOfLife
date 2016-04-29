package com.conway.main;

import javax.swing.JFrame;
import com.conway.layout.UILookAndFeel;
import com.conway.net.GamePacket;
import com.conway.panels.MainPanel;
import com.conway.panels.PanelManager;
import com.conway.util.Vector2D;

public class Main {
	public static JFrame gamewindow;
	
	public static void main(String[] args) {
	    UILookAndFeel.init();
		gamewindow = new JFrame("Conway's Game Of Life");
		gamewindow.setLayout(null);
		gamewindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gamewindow.setUndecorated(true);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setVisible(true);
		PanelManager.setParentFrame(gamewindow);
		PanelManager.addPanel("main", new MainPanel());
		
		//testing area
		Vector2D[] v2d = {new Vector2D(3, 1)};
		GamePacket p = new GamePacket(v2d);
		byte[] data = p.serialize();
		GamePacket p2 = new GamePacket(data);
		System.out.println(p2.getUpdates()[0].getX());
	}
}
