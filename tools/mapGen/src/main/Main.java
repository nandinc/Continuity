package main;

import javax.swing.JFrame;

public class Main {

	static JFrame mainFrame;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createAndShowGUI();
	}
	
	 private static void createAndShowGUI() {
	        mainFrame = new gui.Gui();
	 
	        //Display the window.
	        mainFrame.setVisible(true);
	 }

}
