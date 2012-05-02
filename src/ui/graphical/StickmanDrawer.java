package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;

import model.Area;
import model.FrameItem;

/**
 * Stickman rajzoló
 * @responsibility Stickmaneket rajzol ki.
 */
public class StickmanDrawer implements ItemDrawer {
	/**
	 * Kirajzolja az átadott stickmant az átadott Graphics objektumra.
	 * @param fi Kirajzolandó Stickman
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		Area a = fi.getArea();
		
		g.setColor(Color.GREEN);
		g.fillRect(a.getX()*10, a.getY()*10, a.getWidth()*10, a.getHeight()*10);
	}

}
