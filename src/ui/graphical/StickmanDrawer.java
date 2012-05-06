package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Area;
import model.FrameItem;
import model.Stickman;

/**
 * Stickman rajzoló
 * @responsibility Stickmaneket rajzol ki.
 */
public class StickmanDrawer implements ItemDrawer {
	/**
	 * Stickman 1 grafikája
	 */
	private Image stickman1Image;
	
	/**
	 * Stickman 2 grafikája
	 */
	private Image stickman2Image;
	
	/**
	 * Inicializálás
	 */
	public StickmanDrawer() {
		try {
			this.stickman1Image = ImageIO.read(ClassLoader.class.getResourceAsStream("/resources/graphics/stickman1.png"));
			this.stickman2Image = ImageIO.read(ClassLoader.class.getResourceAsStream("/resources/graphics/stickman2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kirajzolja az átadott stickmant az átadott Graphics objektumra.
	 * @param fi Kirajzolandó Stickman
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g) {
		Area a = fi.getArea();
		Stickman s = (Stickman) fi;
		
		//g.setColor(Color.GREEN);
		//g.fillRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
		g.drawImage(s.getStickmanId() == 1 ? this.stickman1Image : this.stickman2Image, a.getX(), a.getY(), null);
	}

}
