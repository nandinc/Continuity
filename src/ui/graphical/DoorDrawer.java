package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Area;
import model.FrameItem;

/**
 * Door rajzoló
 * @responsibility Ajtókat rajzol ki.
 */
public class DoorDrawer implements ItemDrawer {
	/**
	 * Ajtó grafika
	 */
	private Image doorImage;
	
	/**
	 * Inicializálás
	 */
	public DoorDrawer() {
		try {
			this.doorImage = ImageIO.read(ClassLoader.class.getResourceAsStream("/resources/graphics/door.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kirajzolja az átadott ajtót az átadott Graphics objektumra.
	 * @param fi Kirajzolandó Door
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		Area a = fi.getArea();
		
		//g.setColor(Color.BLUE);
		//g.fillRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
		g.drawImage(this.doorImage, a.getX(), a.getY(), null);
	}
}
