package ui.graphical;

import java.awt.*;
import model.FrameItem;

/**
 * Interfész az egyes keretben lévő elemek rajzolásához.
 * @responsibility FrameItemeteket rajzol ki.
 */
public interface ItemDrawer {
	/**
	 * Kirajzolja az átadott FrameItemet az átadott Graphics objektumra.
	 * @param fi Kirajzolandó FrameItem
	 * @param g Objektum, amire rajzolni lehet
	 */
	public void draw(FrameItem fi, Graphics g);
}
