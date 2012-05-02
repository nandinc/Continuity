package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;

import model.Area;
import model.FrameItem;

/**
 * Door rajzoló
 * @responsibility Ajtókat rajzol ki.
 */
public class DoorDrawer implements ItemDrawer {
	/**
	 * Kirajzolja az átadott ajtót az átadott Graphics objektumra.
	 * @param fi Kirajzolandó Door
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		Area a = fi.getArea();
		
		g.setColor(Color.BLUE);
		g.fillRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
	}
}
