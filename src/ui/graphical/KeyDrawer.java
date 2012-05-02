package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;

import model.Area;
import model.FrameItem;

/**
 * Key rajzoló
 * @responsibility Kulcsokat rajzol ki.
 */
public class KeyDrawer implements ItemDrawer {
	/**
	 * Kirajzolja az átadott kulcsot az átadott Graphics objektumra.
	 * @param fi Kirajzolandó Key
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		// TODO Auto-generated method stub
		Area a = fi.getArea();
		
		g.setColor(Color.RED);
		g.fillRect(a.getX()*10, a.getY()*10, a.getWidth()*10, a.getHeight()*10);
	}

}
