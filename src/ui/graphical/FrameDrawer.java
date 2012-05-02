package ui.graphical;

import model.*;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Frame rajzoló
 * @responsibility Kereteket rajzol ki.
 */
public class FrameDrawer {
	public static final int WIDTH = 300;
	public static final int HEIGHT = 200;
	public static final int HORIZ_MARGIN = 5;
	public static final int HORIZ_PADDING = 5;
	public static final int VERT_MARGIN = 5;
	public static final int VERT_PADDING = 5;
	
	private Color blueColor = new Color(204, 216, 216);
	
	/**
	 * Kirajzol egy keretet az átadott Graphics objektumra.
	 * @param p Kirajzolandó keret pozíciója
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(Area p, Graphics g) {
		// Outer rectangle
		g.setColor(Color.WHITE);
		g.fillRect(
			p.getX() * (Frame.FRAME_WIDTH*10 + 2 * FrameDrawer.VERT_PADDING + FrameDrawer.VERT_MARGIN),
			p.getY() * (Frame.FRAME_HEIGHT*10 + 2 * FrameDrawer.HORIZ_PADDING + FrameDrawer.HORIZ_MARGIN),
			Frame.FRAME_WIDTH*10 + 2 * FrameDrawer.VERT_PADDING,
			Frame.FRAME_HEIGHT*10 + 2 * FrameDrawer.HORIZ_PADDING
		);
		
		// Inner rectangle
		g.setColor(this.blueColor);
		g.fillRect(
			p.getX() * (Frame.FRAME_WIDTH*10 + 2 * FrameDrawer.VERT_PADDING + FrameDrawer.VERT_MARGIN) + FrameDrawer.VERT_PADDING,
			p.getY() * (Frame.FRAME_HEIGHT*10 + 2 * FrameDrawer.HORIZ_PADDING + FrameDrawer.HORIZ_MARGIN) + FrameDrawer.HORIZ_PADDING,
			Frame.FRAME_WIDTH*10,
			Frame.FRAME_HEIGHT*10
		);
	}
}
