package ui.graphical;

import java.awt.Graphics;

import javax.swing.JPanel;

import model.*;

/**
 * Grafikus interfészt nyújt a programhoz.
 * @responsibility Kirajzolja a pálya aktuális állapotát, a benne lévő keretekkel, elemekkel stb. együtt, valamint megjeleníti a vezérlőket (pl. következő pályára lépés).
 */
public class FrontView {
	/**
	 * A játék modellje
	 */
	private Game game;
	
	/**
	 * Az aktuális pálya megjelenítésére szolgáló terület.
	 */
	private JPanel canvas;
	
	/**
	 * A különböző típusú FrameItemekhez tartozó rajzoló osztályok tárolója.
	 */
	private java.util.Map<? extends FrameItem, ItemDrawer> drawers;
	
	/**
	 * Inicializálás
	 * @param game
	 */
	FrontView(Game game) {
		this.game = game;
		
		this.initWindow();
		
		// Subscribe to invalidate events
	}
	
	/**
	 * Újrarajzolja az aktuális pályát.
	 */
	private void repaint() {
		this.canvas.invalidate();
	}
	
	/**
	 * Gondoskodik a keretek és keret elemek canvaszra történő kirajzolásáról.
	 * @param g Objektum, amire rajzolni lehet
	 */
	private void draw(Graphics g) {
		// Reset background
		
		// Draw frames
		
		// Draw items
	}
	
	/**
	 * Inicializálja a grafikus interfészt,
	 * többek közt megjeleníti a játékhoz tartozó ablakot.
	 */
	private void initWindow() {
		
	}
}
