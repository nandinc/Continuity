package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;

import model.Area;
import model.FrameItem;

/**
 * Platform rajzoló
 * @responsibility Platformokat rajzol ki.
 */
public class PlatformDrawer implements ItemDrawer {
	/**
	 * Kirajzolja az átadott platformot az átadott Graphics objektumon.
	 * @param fi Kirajzolandó Platform
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		Area a = fi.getArea();
		
		g.setColor(Color.BLACK);
		g.fillRect(a.getX()*10, a.getY()*10, a.getWidth()*10, a.getHeight()*10);
	}

}
