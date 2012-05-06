package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Area;
import model.FrameItem;
import model.Key;

/**
 * Key rajzoló
 * @responsibility Kulcsokat rajzol ki.
 */
public class KeyDrawer implements ItemDrawer {
	/**
	 * Kulcs grafika
	 */
	private Image keyImage;
	
	/**
	 * Megszerzett kulcs grafika
	 */
	private Image collectedKeyImage;
	
	
	/**
	 * Inicializálás
	 */
	public KeyDrawer() {
		try {
			this.keyImage = ImageIO.read(new File("src/resources/graphics/key.png"));
			this.collectedKeyImage = ImageIO.read(new File("src/resources/graphics/key_collected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kirajzolja az átadott kulcsot az átadott Graphics objektumra.
	 * @param fi Kirajzolandó Key
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		// TODO Auto-generated method stub
		Area a = fi.getArea();
		Key k = (Key) fi;
		
		//g.setColor(Color.RED);
		//g.fillRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
		g.drawImage(k.isCollected() ? this.collectedKeyImage : this.keyImage, a.getX(), a.getY(), null);
	}

}
