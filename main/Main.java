package main;

import java.awt.EventQueue;

import vista.Gui;

public class Main {
	public static void main(String[] args) {
		
		Gui g = new Gui();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					g.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
